package com.farmarket.farmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.Order;
import com.farmarket.farmarket.DataType.OrderDetail;
import com.farmarket.farmarket.Misc.GeneralCalculations;
import com.farmarket.farmarket.Models.OrderDetailModel;
import com.farmarket.farmarket.Models.OrderModel;
import com.farmarket.farmarket.Models.UserModel;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CompleteRePaymentActivity extends AppCompatActivity {
    ImageView logoImage;
    LinearLayout tokenLayout;
    TextView phoneNumber,token;
    String networkString,phoneString,tokenString;
    Button completePayment;
    Realm realm;
    TextView subtotalCost,deliveryCost,totalCost;
    ArrayList<OrderDetail> orderDetails;
    Order order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_re_payment);
        Intent intent = getIntent();
        try
        {
            order = (Order) intent.getExtras().get("order");
            networkString = intent.getExtras().get("network").toString();
            System.out.print(order.getAccountNetwork());
        }
        catch (Exception e)
        {
            intent = new Intent(CompleteRePaymentActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        realm = Realm.getDefaultInstance();
        logoImage = (ImageView)findViewById(R.id.networkLogo);
        tokenLayout = (LinearLayout)findViewById(R.id.tokenLayout);
        phoneNumber = (TextView)findViewById(R.id.phoneNumberET);
        token = (TextView)findViewById(R.id.vodafoneTokenET);
        completePayment= (Button)findViewById(R.id.completePayment);
        orderDetails = new ArrayList<>();
        subtotalCost = (TextView)findViewById(R.id.subTotalTV);
        deliveryCost= (TextView)findViewById(R.id.deliveryTV);
        totalCost = (TextView)findViewById(R.id.totalCostTV);
        setCartDetails();

        if(networkString != null)
        {
            switch (networkString)
            {
                case "MTN":
                    logoImage.setImageDrawable(getResources().getDrawable(R.drawable.mtn_momo));
                    tokenLayout.setVisibility(View.GONE);
                    break;
                case "Vodafone":
                    logoImage.setImageDrawable(getResources().getDrawable(R.drawable.voda));
                    break;
                case "Tigo":
                    logoImage.setImageDrawable(getResources().getDrawable(R.drawable.tigo));
                    tokenLayout.setVisibility(View.GONE);
                    break;
                case "Airtel":
                    logoImage.setImageDrawable(getResources().getDrawable(R.drawable.airtel));
                    tokenLayout.setVisibility(View.GONE);
                    break;
                default:
                    logoImage.setImageDrawable(getResources().getDrawable(R.drawable.mtn_momo));
                    tokenLayout.setVisibility(View.GONE);
                    break;
            }
        }
        completePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneString = phoneNumber.getText().toString();
                if(phoneString != null && phoneString.length()>4)
                {
                    phoneString = phoneNumber.getText().toString();
                    tokenString = "";
                    if(networkString.equals("Vodafone") && token.getText().toString().length() >4)
                    {
                        tokenString = token.getText().toString();
                    }
                    else if(networkString.equals("Vodafone") && token.getText().toString().length() <=4)
                    {
                        Toast.makeText(getApplicationContext(),"Kindly provide your generated token",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(validateUsing_libphonenumber("+233",phoneString) ==false)
                    {
                        Toast.makeText(getApplicationContext(),"Kindly provide a valid phone number",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        completePayment();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Kindly provide a valid phone number",Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }
        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            phoneString = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            return true;
        } else {
            //Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }


    void setCartDetails()
    {
        if(order != null)
        {
            double subTotal = 0;
            double total = 0;
            ArrayList<OrderDetail> orderDetails = order.getDetails();
            for (int i= 0; i<orderDetails.size();i++
                    ) {

                OrderDetail orderDetail = orderDetails.get(i);
                if(orderDetail.getWeight() >0.00)
                {
                    subTotal += GeneralCalculations.getCost(orderDetail.getPrice_per_kg(),orderDetail.getWeight());

                }
            }

            double delivery = 14 + GeneralCalculations.getChargeOnTotal(subTotal ,14 );
            total = subTotal +delivery;
            totalCost.setText("GhC "+total);
            subtotalCost.setText("GhC "+subTotal);
            deliveryCost.setText("GhC "+delivery);

        }
    }


    void completePayment()
    {


        final ProgressDialog pd  = ProgressDialog.show(CompleteRePaymentActivity.this,"Completing payment request ..."," Please Wait  ...", true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<UserModel> login = endpoints.completePayment(order.getUser_id(),order.getInvoices().getInvoice_id(),phoneString,networkString);
        login.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                UserModel user = response.body();
                pd.hide();
                try
                {
                    if(user.getResponseCode() ==200)
                    {
                        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
                        realm.beginTransaction();
                        cartsTable.setCart_status("Closed");
                        realm.copyToRealmOrUpdate(cartsTable);
                        realm.commitTransaction();
                        Toast.makeText(getApplicationContext(),"Payment request sent, Kindly wait for a prompt to complete payment",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CompleteRePaymentActivity.this,MyOrdersActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Sorry an error was encountered kindly contact support",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                pd.hide();
                t.printStackTrace();
            }
        });


    }



}

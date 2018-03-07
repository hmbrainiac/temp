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
import com.farmarket.farmarket.Misc.GeneralCalculations;
import com.farmarket.farmarket.Misc.JsonHelper;
import com.farmarket.farmarket.Models.OrderDetailModel;
import com.farmarket.farmarket.Models.OrderModel;
import com.farmarket.farmarket.Models.UserModel;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CompletePaymentActivity extends AppCompatActivity {
    ImageView logoImage;
    LinearLayout tokenLayout;
    TextView phoneNumber,token;
    String networkString,phoneString,tokenString;
    Button completePayment;
    Realm realm;
    TextView subtotalCost,deliveryCost,totalCost;
    ArrayList<OrderDetailModel> orderDetailModels;
    OrderModel orderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_payment);
        Intent intent = getIntent();
        realm = Realm.getDefaultInstance();
        networkString = intent.getExtras().get("network").toString();
        logoImage = (ImageView)findViewById(R.id.networkLogo);
        tokenLayout = (LinearLayout)findViewById(R.id.tokenLayout);
        phoneNumber = (TextView)findViewById(R.id.phoneNumberET);
        token = (TextView)findViewById(R.id.vodafoneTokenET);
        completePayment= (Button)findViewById(R.id.completePayment);
        orderDetailModels = new ArrayList<>();
        subtotalCost = (TextView)findViewById(R.id.subTotalTV);
        deliveryCost= (TextView)findViewById(R.id.deliveryTV);
        totalCost = (TextView)findViewById(R.id.totalCostTV);


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
                    if(validateUsing_libphonenumber("+233",phoneNumber.getText().toString()) ==false)
                    {
                        Toast.makeText(getApplicationContext(),"Kindly provide a valid phone number",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        System.out.println("Current phone number "+phoneString);
                        completePayment();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Kindly provide a valid phone number",Toast.LENGTH_LONG).show();
                }
            }
        });
        setCartDetails();

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
        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
        if(cartsTable != null)
        {
            orderModel = new OrderModel();
            orderModel.setDeliveryTown(cartsTable.getDeliveryTown());
            orderModel.setDelivery_gh_post_code(cartsTable.getDelivery_gh_post_code());
            orderModel.setUser_id(realm.where(UserTable.class).findFirst().getServer_id());
            orderModel.setDelivryRegion(cartsTable.getDelivryRegion());
            orderModel.setResidentialAddress(cartsTable.getResidentialAddress());
            orderModel.setAccountNumber(phoneString);
            orderModel.setAccountNetwork(networkString);
            orderModel.setAccountToken(tokenString);
            orderModel.setEmail(realm.where(UserTable.class).findFirst().getEmail());
            orderModel.setPhone(realm.where(UserTable.class).findFirst().getPhone());
            double subTotal = 0;
            double total = 0;
            RealmResults<CartDetailsTable> cartDetailsTableRealmResults = realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll();
            if(cartDetailsTableRealmResults != null)
            {
                for (int i= 0; i<cartDetailsTableRealmResults.size();i++) {
                    CartDetailsTable cartDetailsTable = cartDetailsTableRealmResults.get(i);
                    OrderDetailModel orderDetailModel = new OrderDetailModel();
                    orderDetailModel.setCost_per_kg(cartDetailsTable.getCost_per_kg());
                    orderDetailModel.setPrice_per_kg(cartDetailsTable.getPrice_per_kg());
                    orderDetailModel.setProduce_id(cartDetailsTable.getProduce_id());
                    orderDetailModel.setWeight(cartDetailsTable.getWeight());
                    orderDetailModel.setRemarks(cartDetailsTable.getRemarks());
                    orderDetailModel.setOrder_id(cartDetailsTable.getCart_id());
                    orderDetailModels.add(orderDetailModel);
                    if(cartDetailsTable.getWeight() >0.00)
                    {
                        subTotal += GeneralCalculations.getCost(cartDetailsTable.getPrice_per_kg(),cartDetailsTable.getWeight());

                    }
                }

            }
            total = subTotal +14;
            totalCost.setText("GhC "+total);
            subtotalCost.setText("GhC "+subTotal);
            deliveryCost.setText("GhC 14");

        }
    }


    void completePayment()
    {


        final ProgressDialog pd  = ProgressDialog.show(CompletePaymentActivity.this,"Completing payment request ..."," Please Wait  ...", true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        JSONArray jsArray = new JSONArray();
        for (int i=0;i<orderDetailModels.size();i++)
        {
            try {
                jsArray.put(i,orderDetailModels.get(i).toJSon());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(json);
        Date m = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(m);
        cal.add(Calendar.DATE, 7); // 10 is the days you want to add or subtract
        m = cal.getTime();
        orderModel.setExpected_delivery(m.toString());
        orderModel.setAccountNumber(phoneString);
        orderModel.setAccountToken(tokenString);
        System.out.println(orderModel.toJSon()+" Data threw");
        Call<OrderModel> login = endpoints.completeOrder(orderModel.toJSon(),jsArray);
        login.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Response<OrderModel> response, Retrofit retrofit) {
                OrderModel user = response.body();
                pd.hide();
                try
                {
                    if(!user.getUuid().equalsIgnoreCase(null))
                    {
                        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
                        realm.beginTransaction();
                        cartsTable.setCart_status("Closed");
                        realm.copyToRealmOrUpdate(cartsTable);
                        realm.commitTransaction();
                        Intent intent = new Intent(CompletePaymentActivity.this,ThamkYouActivity.class);
                        intent.putExtra("order",user.getOrder_id());
                        intent.putExtra("uuid",user.getUuid());
                        intent.putExtra("expected",user.getExpected_delivery());
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

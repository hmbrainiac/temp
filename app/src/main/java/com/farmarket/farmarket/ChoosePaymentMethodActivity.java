package com.farmarket.farmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.farmarket.farmarket.DataType.CartDetail;
import com.farmarket.farmarket.DataType.CartTotal;
import com.farmarket.farmarket.Misc.GeneralCalculations;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;

import io.realm.Realm;
import io.realm.RealmResults;

public class ChoosePaymentMethodActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton mtn,voda,tigo,airtel;
    Button goToPayment;
    Realm realm;
    TextView subtotalCost,deliveryCost,totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choos_payment_methos);
        realm = Realm.getDefaultInstance();
        radioGroup =(RadioGroup)findViewById(R.id.myRadioGroup);
        mtn = (RadioButton)findViewById(R.id.mtn);
        voda = (RadioButton)findViewById(R.id.vodafone);
        tigo = (RadioButton)findViewById(R.id.tigo);
        airtel = (RadioButton)findViewById(R.id.airtel);
        goToPayment = (Button)findViewById(R.id.toCheckout);
        subtotalCost = (TextView)findViewById(R.id.subTotalTV);
        deliveryCost= (TextView)findViewById(R.id.deliveryTV);
        totalCost = (TextView)findViewById(R.id.totalCostTV);
        mtn.setSelected(true);
        goToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                 String selected = "";
                if(selectedId == mtn.getId())
                {
                    selected = "MTN";
                }else
                if(selectedId == voda.getId())
                {
                    selected = "Vodafone";
                }
                else
                if(selectedId == tigo.getId())
                {
                    selected = "Tigo";
                }else
                if(selectedId == airtel.getId())
                {
                    selected = "Airtel";
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Kindly select a payment method",Toast.LENGTH_LONG).show();
                }
                if(selected !="")
                {
                    Intent intent = new Intent(ChoosePaymentMethodActivity.this,CompletePaymentActivity.class);
                    intent.putExtra("network",selected);
                    startActivity(intent);
                    finish();
                    return;
                }

            }
        });
        setCartDetails();
    }



    void setCartDetails()
    {
        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
        if(cartsTable != null)
        {
            double subTotal = 0;
            double total = 0;
            RealmResults<CartDetailsTable> cartDetailsTableRealmResults = realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll();
            if(cartDetailsTableRealmResults != null)
            {
                for (int i= 0; i<cartDetailsTableRealmResults.size();i++
                        ) {

                    CartDetailsTable cartDetailsTable = cartDetailsTableRealmResults.get(i);
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


}

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

import com.farmarket.farmarket.DataType.Order;
import com.farmarket.farmarket.DataType.OrderDetail;
import com.farmarket.farmarket.Misc.GeneralCalculations;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class PayForOrderActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton mtn,voda,tigo,airtel;
    Button goToPayment;
    Realm realm;
    TextView subtotalCost,deliveryCost,totalCost;
    Order order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_for_order);
        Intent intent = getIntent();
        try
        {
            order = (Order) intent.getExtras().get("order");
            System.out.print(order.getAccountNetwork());
        }
        catch (Exception e)
        {
            intent = new Intent(PayForOrderActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
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
                    Intent intent = new Intent(PayForOrderActivity.this,CompleteRePaymentActivity.class);
                    intent.putExtra("network",selected);
                    intent.putExtra("order",order);
                    startActivity(intent);
                }

            }
        });
        setCartDetails();

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

}

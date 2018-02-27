package com.farmarket.farmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.ProductCart;
import com.farmarket.farmarket.DataType.ProductEmpty;
import com.farmarket.farmarket.Misc.GeneralCalculations;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;

import io.realm.Realm;

public class MySingleOrderActivity extends AppCompatActivity {
    ProductCart productCart;
    ImageView plusImage,minusImage,imageView;
    TextView singlePrice,currentWeight,totalPrice,name,description,productType,percentageOff;
    CartDetailsTable currentCartDetail;
    Button button;
    static double weight,cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_single_order);
        Intent intent = getIntent();
        productCart = (ProductCart) intent.getSerializableExtra("product");
        weight = productCart.getInCart();
        cost = (GeneralCalculations.getCost(Double.parseDouble(productCart.getPrice_per_kg()),weight));
        plusImage=(ImageView)findViewById(R.id.idPlus);
        minusImage=(ImageView)findViewById(R.id.idMinus);
        name = (TextView)findViewById(R.id.productName);
        description = (TextView)findViewById(R.id.productDescription);
        singlePrice = (TextView)findViewById(R.id.pricePerKilo);
        currentWeight = (TextView)findViewById(R.id.totalKilo);
        totalPrice = (TextView)findViewById(R.id.totalCost);
        button = (Button)findViewById(R.id.addCartBtn);
        imageView = (ImageView)findViewById(R.id.mainImage);
        productType = (TextView)findViewById(R.id.percentOff);
        percentageOff = (TextView)findViewById(R.id.likes);

        percentageOff.setVisibility(View.GONE);
        productType.setText(productCart.getProduce_type());

        name.setText(productCart.getName());
        description.setText(productCart.getDescription());
        singlePrice.setText("GhC "+productCart.getPrice_per_kg()+"/ Kg X");
        currentWeight.setText(weight+"");
        totalPrice.setText("= Cost : GhC "+cost+"");

        Glide.with(MySingleOrderActivity.this).load(ApiLocation.getImageLocation()+productCart.getFile_name())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        minusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current quantity
                //increase by 0.2
                weight -= 0.2;
                if(weight < 0.00)
                    weight = 0.00;
                weight = Math.round(weight*100.0)/100.0;
                cost = (GeneralCalculations.getCost(Double.parseDouble(productCart.getPrice_per_kg()),weight));
                Realm realm = Realm.getDefaultInstance();
                currentCartDetail = realm.where(CartDetailsTable.class).equalTo("produce_id",productCart.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                currentWeight.setText(weight+"");
                totalPrice.setText("= GhC "+cost+"");

                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",productCart.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                realm.beginTransaction();
                    currentCartDetail.setWeight(weight);
                    cartDetailsTable.setWeight(weight);
                realm.copyToRealmOrUpdate(cartDetailsTable);
                realm.copyToRealmOrUpdate(currentCartDetail);
                realm.commitTransaction();


            }
        });

        plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm =Realm.getDefaultInstance();
                weight +=0.2;
                weight = Math.round(weight*100.0)/100.0;
                cost = (GeneralCalculations.getCost(Double.parseDouble(productCart.getPrice_per_kg()),weight));

                if(weight == 0.00)
                {
                    minusImage.setEnabled(true);
                    //currentCartDetail
                    realm.beginTransaction();
                        currentCartDetail.setWeight(weight);
                    realm.copyToRealmOrUpdate(currentCartDetail);
                    realm.commitTransaction();

                }
                currentWeight.setText(weight+"");
                totalPrice.setText("= Cost : GhC "+cost+"");


                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",productCart.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                realm.beginTransaction();
                cartDetailsTable.setWeight(weight);
                realm.copyToRealmOrUpdate(cartDetailsTable);
                realm.commitTransaction();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send to cart
                Intent intent1 = new Intent(MySingleOrderActivity.this,CartActivity.class);
                startActivity(intent1);
                finish();
                return;
            }
        });
    }
}

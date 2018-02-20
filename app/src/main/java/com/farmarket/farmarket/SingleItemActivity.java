package com.farmarket.farmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

public class SingleItemActivity extends AppCompatActivity {
    ProductEmpty productEmpty;
    ImageView imageView;
    TextView pointFive,one,five,ten,name,description,price;
    Button button;
    static double weight,cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        Intent intent = getIntent();
        productEmpty = (ProductEmpty) intent.getSerializableExtra("product");
        weight = 1;
        cost = Double.parseDouble(productEmpty.getPrice_per_kg());

        imageView = (ImageView)findViewById(R.id.mainImage);
        pointFive = (TextView)findViewById(R.id.id05);
        one = (TextView)findViewById(R.id.id1);
        five = (TextView)findViewById(R.id.id5);
        ten = (TextView)findViewById(R.id.id10);
        pointFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClick(pointFive,"0.5 Kg",0.5);
            }
        });


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClick(one,"1 Kg",1);

            }
        });


        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClick(five,"5 Kg",5);
            }
        });


        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClick(ten,"10 Kg",10);
            }
        });



        name = (TextView)findViewById(R.id.productName);
        description = (TextView)findViewById(R.id.productDescription);
        price = (TextView)findViewById(R.id.price);

        button = (Button)findViewById(R.id.addCartBtn);

        name.setText(productEmpty.getName());
        description.setText(productEmpty.getDescription());
        price.setText(productEmpty.getPrice_per_kg());

        Glide.with(SingleItemActivity.this).load(ApiLocation.getImageLocation()+productEmpty.getFile_name())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        doClick(one,"1 Kg",1);

    }

    void addToCart()
    {

        //check if there is an opened cart
        Realm realm = Realm.getDefaultInstance();
        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
        if(cartsTable == null)
        {
            //if not create one
            cartsTable = new CartsTable();
            realm.beginTransaction();
            cartsTable.setCart_status("Pending");
            cartsTable.setDelivery_gh_post_code("");
            cartsTable.setDelivery_id(0);
            cartsTable.setDelivery_lat("");
            cartsTable.setDelivery_lon("");
            cartsTable.setEmail("");
            cartsTable.setExpected_delivery("");
            cartsTable.setName("");
            cartsTable.setPhone("");
            cartsTable.setStatus("Pending");
            cartsTable.setUser_id(0);
            cartsTable.setId((realm.where(CartsTable.class).findAll().size()+1));
            realm.copyToRealmOrUpdate(cartsTable);
           // realm.close();
        }

        //check of the there is this product in the cart
        CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).equalTo("produce_id",productEmpty.getProduce_id()).findFirst();
        if(cartDetailsTable == null)
        {
            //create one
            cartDetailsTable = new CartDetailsTable();
            cartDetailsTable.setCart_id(cartDetailsTable.getId());
            cartDetailsTable.setCost_per_kg(Double.parseDouble(productEmpty.getPrice_per_kg()));
            cartDetailsTable.setWeight(weight);
            cartDetailsTable.setCreated_at("");
            cartDetailsTable.setId((realm.where(CartDetailsTable.class).findAll().size()+1));
            cartDetailsTable.setPrice_per_kg(Double.parseDouble(productEmpty.getPrice_per_kg()));
            cartDetailsTable.setProduce_id(productEmpty.getProduce_id());
            cartDetailsTable.setRemarks("");
            cartDetailsTable.setUnique_code("");
            cartDetailsTable.setUpdated_at("");
            realm.copyToRealmOrUpdate(cartDetailsTable);
           // realm.close();
        }
        else
        {
            cartDetailsTable.setWeight(weight);
            cartDetailsTable.setCost_per_kg(Double.parseDouble(productEmpty.getPrice_per_kg()));
            cartDetailsTable.setPrice_per_kg(Double.parseDouble(productEmpty.getPrice_per_kg()));
            realm.copyToRealmOrUpdate(cartDetailsTable);
          //  realm.close();
        }
        Intent intent = new Intent(SingleItemActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    void doClick(TextView textView,String string, double weight)
    {
        this.weight = weight;
        cost = GeneralCalculations.getCost(Double.parseDouble(productEmpty.getPrice_per_kg()),weight);
        doHighlight(textView,string);
        price.setText("Cost : GhC "+cost);

    }

    protected void doHighlight(TextView tv, String string){
        // Specify the text/word to highlight inside TextView
        pointFive.setText("0.5 Kg");
        one.setText("1 Kg");
        five.setText("5 Kg");
        ten.setText("10 Kg");

        // Construct the formatted text
        String replacedWith = "<font color='blue'>" + string + "</font>";

        // Update the TextView text
        tv.setText(Html.fromHtml(replacedWith));
    }
}

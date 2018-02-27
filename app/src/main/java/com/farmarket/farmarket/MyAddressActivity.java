package com.farmarket.farmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmarket.farmarket.RealmTables.CartsTable;

import io.realm.Realm;

public class MyAddressActivity extends AppCompatActivity {
    Spinner regionSpinner;
    EditText postGPS,residentionalAddress,cityTown;
    Button btnCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
         regionSpinner=(Spinner)findViewById(R.id.Regionspinner);
        final String[] countries=getResources().getStringArray(R.array.array_regions);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);
        regionSpinner.setAdapter(adapter);
        postGPS = (EditText)findViewById(R.id.postGPS);
        residentionalAddress = (EditText)findViewById(R.id.physicalAddress);
        cityTown = (EditText)findViewById(R.id.city);
        btnCheckOut = (Button)findViewById(R.id.toPayment);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String region,gps,address,city;
                region = countries[regionSpinner.getSelectedItemPosition()];
                gps = postGPS.getText().toString();
                address = residentionalAddress.getText().toString();
                city = cityTown.getText().toString();
                if(isValid(region) && isValid(gps) && isValid(address) && isValid(city))
                {
                    Realm realm = Realm.getDefaultInstance();
                    CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
                    if(cartsTable != null)
                    {
                        realm.beginTransaction();
                        cartsTable.setResidentialAddress(address);
                        cartsTable.setDelivryRegion(region);
                        cartsTable.setDelivery_gh_post_code(gps);
                        cartsTable.setDeliveryTown(city);
                        realm.copyToRealmOrUpdate(cartsTable);
                        realm.commitTransaction();
                        Intent intent = new Intent(MyAddressActivity.this,ChoosePaymentMethodActivity.class);
                        intent.putExtra("region",region);
                        intent.putExtra("gps",gps);
                        intent.putExtra("address",address);
                        intent.putExtra("city",city);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MyAddressActivity.this,"Sorry there was an error processing cart information please try again",Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    Toast.makeText(MyAddressActivity.this,"Kindly provide all requested information",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    boolean isValid(String string)
    {
        if(string.length() > 4)
            return  true;
        return false;
    }
}

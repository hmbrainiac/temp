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
import com.farmarket.farmarket.RealmTables.MyAddressTable;

import io.realm.Realm;

public class SetupAddressActivity extends AppCompatActivity {
    Realm realm;
    Spinner regionSpinner;
    EditText postGPS,residentionalAddress,cityTown;
    Button btnCheckOut;
    MyAddressTable myAddressTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_address);
        regionSpinner=(Spinner)findViewById(R.id.Regionspinner);
        final String[] countries=getResources().getStringArray(R.array.array_regions);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);
        regionSpinner.setAdapter(adapter);
        postGPS = (EditText)findViewById(R.id.postGPS);
        residentionalAddress = (EditText)findViewById(R.id.physicalAddress);
        cityTown = (EditText)findViewById(R.id.city);
        btnCheckOut = (Button)findViewById(R.id.toPayment);
        realm = Realm.getDefaultInstance();
         myAddressTable = realm.where(MyAddressTable.class).findFirst();
        if(myAddressTable != null)
        {
            postGPS.setText(myAddressTable.getGhana_post_gps());
            residentionalAddress.setText(myAddressTable.getResidentialAddress());
            cityTown.setText(myAddressTable.getDeliveryTown());
        }
        else
        {
            myAddressTable = new MyAddressTable();
            myAddressTable.setId(1);
        }
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
                    realm.beginTransaction();

                    myAddressTable.setResidentialAddress(address);
                    myAddressTable.setDelivryRegion(region);
                    myAddressTable.setGhana_post_gps(gps);
                    myAddressTable.setDeliveryTown(city);
                    realm.copyToRealmOrUpdate(myAddressTable);
                    realm.commitTransaction();
                    Toast.makeText(SetupAddressActivity.this,"Your delivery address has been updated",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(SetupAddressActivity.this,"Kindly provide all requested information",Toast.LENGTH_LONG).show();
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

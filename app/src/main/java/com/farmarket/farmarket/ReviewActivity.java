package com.farmarket.farmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.Order;
import com.farmarket.farmarket.Models.UserModel;
import com.farmarket.farmarket.RealmTables.CartsTable;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ReviewActivity extends AppCompatActivity {
    String orderId,Uuid;
    RatingBar delivery,packaging,experience;
    EditText extraService;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        try{
            orderId = (String)intent.getExtras().get("order");
            Uuid = (String)intent.getExtras().get("uuid");
            System.out.println(Uuid);
        }
        catch (Exception e)
        {
            Intent intent1 = new Intent(ReviewActivity.this,MainActivity.class);
            Toast.makeText(getApplicationContext(),"Sorry review is currently not available",Toast.LENGTH_LONG).show();
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent1);
            finish();
            return;
        }

        submitButton = (Button)findViewById(R.id.submitReview);
        extraService = (EditText)findViewById(R.id.extraSuggestion);
        delivery = (RatingBar)findViewById(R.id.courierRatingBar);
        packaging = (RatingBar)findViewById(R.id.packagingRatingBar);
        experience = (RatingBar)findViewById(R.id.customerRatingBar);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateService();

            }
        });

    }

    void rateService()
    {



        final ProgressDialog pd  = ProgressDialog.show(ReviewActivity.this,"Submitting your review ..."," Please Wait  ...", true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<UserModel> login = endpoints.reviewOrder(Uuid,extraService.getText().toString(),delivery.getRating(),packaging.getRating(),experience.getRating());
        login.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                UserModel user = response.body();
                pd.hide();
                try
                {
                    if(user.getResponseCode() ==200)
                    {
                        Toast.makeText(getApplicationContext(),"Thank you for the feedback;",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ReviewActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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

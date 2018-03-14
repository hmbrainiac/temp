package com.farmarket.farmarket;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.Category;
import com.farmarket.farmarket.DataType.ProductCart;
import com.farmarket.farmarket.DataType.ProductEmpty;
import com.farmarket.farmarket.Models.CategoryModel;
import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.UserTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SplashActivity extends AppCompatActivity {
    ArrayList<Category> categories;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        categories = new ArrayList<>();
        pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setIndeterminate(true);
        loadCategories();
//        new BackgroundTask().execute();
    }

    private class BackgroundTask extends AsyncTask {
        Intent intent;
        private static final int SPLASH_TIME = 4080;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // intent = new Intent(SplashActivity.this, LoginActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */
            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//          Pass your loaded data here using Intent
//          intent.putExtra("data_key", "");
         //   startActivity(intent);
          //  finish();
        }
    }


    void loadCategories()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<List<CategoryModel>> data = endpoints.listCategories();
        data.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Response<List<CategoryModel>> response, Retrofit retrofit) {
                if(response.isSuccess() && response.code() == 200)
                {
                    categories.clear();

                    List<CategoryModel> generalModels = response.body();
                    for(int i = 0; i<generalModels.size();i++)
                    {
                        CategoryModel produceModel = generalModels.get(i);

                        Category category = new Category();
                        category.setCategory_id(produceModel.getCategory_id());
                        category.setName(produceModel.getName());
                        categories.add(category);
                    }

                    Realm realm = Realm.getDefaultInstance();

                    UserTable  userTable = realm.where(UserTable.class).findFirst();
                    Intent intent;
                    intent = new Intent(SplashActivity.this, MainSelectionActivity.class);
                    if(userTable != null && userTable.getId() == 1)
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("categories",categories);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(),"Please make sure you have a working internet connection",Toast.LENGTH_LONG).show();
                loadCategories();
            }
        });

    }


}

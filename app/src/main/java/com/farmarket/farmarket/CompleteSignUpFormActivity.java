package com.farmarket.farmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.Models.UserModel;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.google.firebase.auth.PhoneAuthProvider;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CompleteSignUpFormActivity extends AppCompatActivity {
    static String phone,email,user_name,pass,confirm_password;
    Button completeBtn;
    TextView signIn;
    EditText userName,password,confirmPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up_form);

        final Intent intent = getIntent();
        phone = (String)intent.getExtras().get("phone");
        email = (String)intent.getExtras().get("email");
        progressDialog = new ProgressDialog(CompleteSignUpFormActivity.this);
        userName = (EditText) findViewById(R.id.userNameET);
        password = (EditText) findViewById(R.id.passwordET);
        confirmPassword = (EditText) findViewById(R.id.phoneNumberET);
        completeBtn = (Button)findViewById(R.id.signInBtn);
        signIn = (TextView)findViewById(R.id.signUpTV);

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = userName.getText().toString();
                pass = password.getText().toString();
                confirm_password = confirmPassword.getText().toString();
                if(isPasswordValid(pass) && confirm_password.equals(pass))
                {
                    registration();
                }
                else
                {
                    Toast.makeText(CompleteSignUpFormActivity.this,"Make sure your password is more than 6 characters and confirm your password",Toast.LENGTH_LONG).show();
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CompleteSignUpFormActivity.this,SignInActivity.class);
                startActivity(intent1);
                finish();
                return;
            }
        });

    }


    void registration()
    {


            final ProgressDialog pd  = ProgressDialog.show(CompleteSignUpFormActivity.this,"Completing registration request ..."," Please Wait  ...", true);
            final Intent intent = new Intent(CompleteSignUpFormActivity.this, MainActivity.class);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiLocation.getApiLocation1())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);

            Call<UserModel> login = endpoints.register(email,"","",user_name,phone,pass);
            login.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                    UserModel user = response.body();
                    pd.hide();

                    try
                    {
                        Realm realm = Realm.getDefaultInstance();
                        UserTable userTable = new UserTable();

                        userTable.setCreated_at(user.getCreated_at());
                        userTable.setEmail(user.getEmail());
                        userTable.setFirstname(user.getFirstname());
                        userTable.setForgot_code(user.getForgot_code());
                        userTable.setId(1);
                        userTable.setLastname(user.getLastname());
                        userTable.setPhone(user.getPhone());
                        userTable.setResponseCode(user.getResponseCode());
                        userTable.setServer_id(user.getUser_id());
                        userTable.setUnique_code(user.getUnique_code());
                        userTable.setUpdated_at(user.getUpdated_at());
                        userTable.setUser_id(user.getUser_id());
                        userTable.setUser_region(user.getUser_region());
                        userTable.setUser_type(user.getUser_type());
                        userTable.setUsername(user.getUsername());
                        userTable.setUuid(user.getUuid());
                        userTable.setVerification_code(user.getVerification_code());

                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(userTable);
                        realm.commitTransaction();

                        Toast.makeText(CompleteSignUpFormActivity.this,user.getPhone(),Toast.LENGTH_LONG).show();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(CompleteSignUpFormActivity.this,"Sorry we couldnt complete your registration. Please try again",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    pd.hide();
                    t.printStackTrace();
                    Toast.makeText(CompleteSignUpFormActivity.this,"Sorry An Error Occurred Please Try Again Later",Toast.LENGTH_LONG).show();
                }
            });

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

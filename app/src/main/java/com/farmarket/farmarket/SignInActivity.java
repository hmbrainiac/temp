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
import com.farmarket.farmarket.Fonts.TitalliumWebText;
import com.farmarket.farmarket.Models.UserModel;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SignInActivity extends AppCompatActivity {
    EditText usernameET,passwordET;
    static String username,password;
    TitalliumWebText goToSignUp,forgotPassword;
    Button signInbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        goToSignUp = (TitalliumWebText)findViewById(R.id.signUpTV);
        usernameET = (EditText)findViewById(R.id.emailET);
        passwordET = (EditText)findViewById(R.id.passwordET);

        signInbtn = (Button)findViewById(R.id.signInBtn);

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);

                startActivity(intent);
                finish();
                return;
            }
        });

        forgotPassword = (TitalliumWebText)findViewById(R.id.forgotPasswordTV);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);

                startActivity(intent);
                finish();
                return;

            }
        });
        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordET.getText().toString();
                username = usernameET.getText().toString();
                if(isPasswordValid(password) && isUserNameValid(username))
                {
                    loginUser();
                }
                else
                {
                    Toast.makeText(SignInActivity.this,"Kindly provide a valid login credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void loginUser()
    {


        final ProgressDialog pd  = ProgressDialog.show(SignInActivity.this,"Completing login request ..."," Please Wait  ...", true);
        final Intent intent = new Intent(SignInActivity.this, MainActivity.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);

        Call<UserModel> login = endpoints.login(username,password);
        login.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                UserModel user = response.body();
                pd.hide();

                try
                {
                    if(user.getResponseCode() ==200)
                    {
                        Toast.makeText(SignInActivity.this,user.getPhone(),Toast.LENGTH_LONG).show();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignInActivity.this,"Sorry login failed please try again",Toast.LENGTH_LONG).show();

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(SignInActivity.this,"Sorry we couldnt complete your login. Please try again",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                pd.hide();
                t.printStackTrace();
                Toast.makeText(SignInActivity.this,"Sorry An Error Occurred Please Try Again Later",Toast.LENGTH_LONG).show();
            }
        });


    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isUserNameValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

}

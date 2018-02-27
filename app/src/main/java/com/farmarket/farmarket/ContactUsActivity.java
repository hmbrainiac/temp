package com.farmarket.farmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.Models.UserModel;
import com.farmarket.farmarket.RealmTables.UserTable;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ContactUsActivity extends AppCompatActivity {

    Realm realm;
    UserTable userTable;
    EditText message,Email;
    Button button;
    TextInputLayout messageLayout,emailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        realm = Realm.getDefaultInstance();
        userTable = realm.where(UserTable.class).findFirst();
        message = (EditText)findViewById(R.id.message);
        Email = (EditText)findViewById(R.id.userEmail);


        button = (Button)findViewById(R.id.toPayment);
        emailLayout = (TextInputLayout)findViewById(R.id.input_layout_name);
        messageLayout = (TextInputLayout)findViewById(R.id.input_layout_name1);

        Email.addTextChangedListener(new MyTextWatcher(Email));
        message.addTextChangedListener(new MyTextWatcher(message));
        Email.setText(userTable.getEmail());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateName() && validateEmail())
                {
                    loginUser();
                }
            }
        });


    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.message:
                    validateName();
                    break;
                case R.id.userEmail:
                    validateEmail();
                    break;

            }
        }
    }

    private boolean validateName() {
        if (message.getText().toString().trim().isEmpty()) {
            messageLayout.setError("Kindly provide a message to send");
            requestFocus(message);
            return false;
        } else {
            messageLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = Email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailLayout.setError("Kindly provide a valid email address");
            requestFocus(Email);
            return false;
        } else {
            emailLayout.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    void loginUser()
    {


        final ProgressDialog pd  = ProgressDialog.show(ContactUsActivity.this,"Sending your request ..."," Please Wait  ...", true);
        final Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);

        Call<UserModel> login = endpoints.contactUs(Email.getText().toString().trim(),message.getText().toString().trim());
        login.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                UserModel user = response.body();
                pd.hide();
                if(response.isSuccess() && response.code() == 200) {
                    Toast.makeText(ContactUsActivity.this,"Your message has been sent. We will be in touch shortly",Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(ContactUsActivity.this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                    return;

                }
                else
                {
                    Toast.makeText(ContactUsActivity.this,"Sorry we couldnt complete your request. Please try again "+response.message(),Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Throwable t) {
                pd.hide();
                t.printStackTrace();
                Toast.makeText(ContactUsActivity.this,"Sorry An Error Occurred Please Try Again Later",Toast.LENGTH_LONG).show();
            }
        });


    }

}

package com.farmarket.farmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmarket.farmarket.Fonts.TitalliumWebText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;


public class SignUpActivity extends AppCompatActivity {

    EditText phoneNumber,firstName,lastName,email;
    CountryCodePicker countryCodePicker;
    TitalliumWebText signInTV;
    Button signUpBtn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseAuth mAuth;
    static String fullPhone;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        countryCodePicker = (CountryCodePicker)findViewById(R.id.ccp);
        email = (EditText)findViewById(R.id.emailET);
        phoneNumber = (EditText)findViewById(R.id.phoneNumberET);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        signInTV = (TitalliumWebText)findViewById(R.id.signUpTV);
        signUpBtn = (Button)findViewById(R.id.signUpBtn);
        //countryCodePicker.detectLocaleCountry(true);
        //countryCodePicker.setCountryForNameCode("Ghana");
        //countryCodePicker.setAutoDetectedCountry(true);
        //countryCodePicker.setCountryPreference("GH");
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            public static final String TAG ="" ;

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                progressDialog.dismiss();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                progressDialog.dismiss();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(),"Sorry an error was encountered please try again later",Toast.LENGTH_LONG).show();
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(getApplicationContext(),"Kindly contact support with error code 212",Toast.LENGTH_LONG).show();
                }

                // Show a message and update the UI
                // ...
               // Toast.makeText(getApplicationContext(),"Kindly contact support with error code 213",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                progressDialog.dismiss();

                // Save verification ID and resending token so we can use them later
                //mVerificationId = verificationId;
                //mResendToken = token;
                //TODO send user to enter verification code
                // ...
               
               

                final String finalPhone = internationalFormat;

                Intent intent = new Intent(SignUpActivity.this,ConfirmCodeActivity.class);
                intent.putExtra("verificationId",verificationId);
                intent.putExtra("token",token);
                intent.putExtra("phone",finalPhone);
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("email",email.getText().toString().trim());
                intent.putExtra("firstname",firstName.getText().toString().trim());
                intent.putExtra("lastname",lastName.getText().toString().trim());
                startActivity(intent);
                finish();
                return;
            }
        };
        fullPhone = "";


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEmailValid(email.getText().toString()) && isNameValid(firstName.getText().toString().trim()) && isNameValid(lastName.getText().toString().trim()))
                {

                    progressDialog.setTitle("Verifying your contact details ..");
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    String phone = phoneNumber.getText().toString();

                    final String phoneNumber = phone;

                    if(validateUsing_libphonenumber(countryCodePicker.getSelectedCountryCodeWithPlus().toString(),phoneNumber))
                    {
                            fullPhone = internationalFormat;
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                fullPhone,        // Phone number to verify
                                60,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                SignUpActivity.this,               // Activity (for callback binding)
                                mCallbacks);        // OnVerificationStateChangedCallbacks

                    }
                    else
                    {
                        progressDialog.dismiss();
                    }

                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"Kindly provide a valid email address",Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public static final String TAG = "";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            //updateUI(STATE_SIGNIN_SUCCESS, user);
                            Intent intent = new Intent(SignUpActivity.this,CompleteSignUpFormActivity.class);
                            intent.putExtra("phone",fullPhone);
                            intent.putExtra("email",email.getText().toString().trim());
                            intent.putExtra("firstname",firstName.getText().toString().trim());
                            intent.putExtra("lastname",lastName.getText().toString().trim());
                            startActivity(intent);
                            finish();

                            startActivity(intent);
                            finish();
                            return;
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                                Toast.makeText(getApplicationContext(),"Sorry verification failed",Toast.LENGTH_LONG).show();
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            //updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                            Toast.makeText(getApplicationContext(),"Sorry registration failed kindly try again later",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isNameValid(String email) {
        //TODO: Replace this with your own logic
        return email.length()>1;
    }

    static String internationalFormat;


    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
             internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            return true;
        } else {
            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }



}

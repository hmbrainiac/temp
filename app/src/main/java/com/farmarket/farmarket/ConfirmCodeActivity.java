package com.farmarket.farmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class ConfirmCodeActivity extends AppCompatActivity {

    EditText verificationCode1,verificationCode2,verificationCode3,verificationCode4,verificationCode5,verificationCode6;
    TextView changeNumber,resendActivationCode, loginTv;
    FirebaseAuth mAuth;

    Button activateLogin;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    static PhoneAuthProvider.ForceResendingToken mToken;
    static String phoneNumber,verificationId,phone,email,firstname,lastname,type;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        progressDialog = new ProgressDialog(ConfirmCodeActivity.this);

        mAuth = FirebaseAuth.getInstance();
        verificationCode1 = (EditText)findViewById(R.id.verificationCodeET1);
        verificationCode2 = (EditText)findViewById(R.id.verificationCodeET2);
        verificationCode3 = (EditText)findViewById(R.id.verificationCodeET3);
        verificationCode4 = (EditText)findViewById(R.id.verificationCodeET4);
        verificationCode5 = (EditText)findViewById(R.id.verificationCodeET5);
        verificationCode6 = (EditText)findViewById(R.id.verificationCodeET6);
        changeNumber = (TextView)findViewById(R.id.changePhoneNumber);
        resendActivationCode = (TextView)findViewById(R.id.resendActivationCode);
        loginTv = (TextView)findViewById(R.id.signUpTV);
        activateLogin = (Button)findViewById(R.id.activateBtn);

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmCodeActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        verificationCode1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(verificationCode1.getText().toString().length()==1)     //size as per your requirement
                {
                    verificationCode2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        verificationCode2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(verificationCode2.getText().toString().length()==1)     //size as per your requirement
                {
                    verificationCode3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        verificationCode3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(verificationCode3.getText().toString().length()==1)     //size as per your requirement
                {
                    verificationCode4.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        verificationCode4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(verificationCode4.getText().toString().length()==1)     //size as per your requirement
                {
                    verificationCode5.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        verificationCode5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(verificationCode5.getText().toString().length()==1)     //size as per your requirement
                {
                    verificationCode6.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        verificationCode6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(verificationCode6.getText().toString().length()==1)     //size as per your requirement
                {
                    // verificationCode6.requestFocus();
                    String finalString = getString(verificationCode1)+getString(verificationCode2)+getString(verificationCode3)+getString(verificationCode4)+getString(verificationCode5)+getString(verificationCode6);
                    verifyPhoneNumberWithCode(verificationId,finalString);

                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        Intent intent = getIntent();
        mToken = (PhoneAuthProvider.ForceResendingToken)intent.getExtras().get("token");
        phoneNumber = (String)intent.getExtras().get("phone");
        phone = (String)intent.getExtras().get("phone");
        verificationId = (String)intent.getExtras().get("verificationId");
        email = (String)intent.getExtras().get("email");
        firstname = (String)intent.getExtras().get("firstname");
        lastname = (String)intent.getExtras().get("lastname");
        type = (String)intent.getExtras().get("type");


        activateLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalString = getString(verificationCode1)+getString(verificationCode2)+getString(verificationCode3)+getString(verificationCode4)+getString(verificationCode5)+getString(verificationCode6);

                verifyPhoneNumberWithCode(verificationId,finalString);
            }
        });


        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ConfirmCodeActivity.this,SignUpActivity.class);
                if(type.equalsIgnoreCase("Forgot"))
                {
                    intent1 = new Intent(ConfirmCodeActivity.this,ForgotPasswordActivity.class);
                }
                startActivity(intent1);
                finish();
                return;
            }
        });

        resendActivationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(phoneNumber,mToken);
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

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

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
                Toast.makeText(getApplicationContext(),"Kindly contact support with error code 213",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                //mVerificationId = verificationId;
                //mResendToken = token;
                //TODO send user to enter verification code
                // ...
                mToken= token;
                ConfirmCodeActivity.verificationId = verificationId;
                Toast.makeText(getApplicationContext(),"Kindly check your text messages for the activation code",Toast.LENGTH_LONG).show();
            }
        };


    }



    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        progressDialog.setMessage("Please wait ... ");
        progressDialog.setTitle("Verifying Code");
        progressDialog.show();
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }


    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
// [END resend_verification]


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public static final String TAG = "";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            //updateUI(STATE_SIGNIN_SUCCESS, user);
                            //Toast.makeText(getApplicationContext(),"User id "+user.getUid(),Toast.LENGTH_LONG).show();
                            if(type.equalsIgnoreCase("SignUp"))
                            {
                                Intent intent = new Intent(ConfirmCodeActivity.this,CompleteSignUpFormActivity.class);
                                intent.putExtra("phone",phone);
                                intent.putExtra("email",email);
                                intent.putExtra("firstname",firstname);
                                intent.putExtra("lastname",lastname);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            else
                            {
                                Intent intent = new Intent(ConfirmCodeActivity.this,SetPasswordActivity.class);
                                intent.putExtra("phone",phone);
                                startActivity(intent);
                                finish();
                                return;
                            }
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
                            Toast.makeText(getApplicationContext(),"Sorry verification failed kindly try again later",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    String getString(EditText editText)
    {
        return editText.getText().toString();
    }
}

package com.farmarket.farmarket.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.CompleteSignUpFormActivity;
import com.farmarket.farmarket.MainActivity;
import com.farmarket.farmarket.Models.UserModel;
import com.farmarket.farmarket.R;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.farmarket.farmarket.SignUpActivity;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }
    EditText firstname,lastname,email,phone;
    Button update;
    CountryCodePicker countryCodePicker;
    String fullPhone;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        firstname = (EditText)v.findViewById(R.id.firstName);
        lastname = (EditText)v.findViewById(R.id.lastName);
        email = (EditText)v.findViewById(R.id.emailET);
        countryCodePicker = (CountryCodePicker)v.findViewById(R.id.ccp);
        phone = (EditText)v.findViewById(R.id.phoneNumberET);
        update = (Button)v.findViewById(R.id.signUpBtn);
        Realm realm = Realm.getDefaultInstance();
        final UserTable userTable = realm.where(UserTable.class).findFirst();

        firstname.setText(userTable.getFirstname());

        lastname.setText(userTable.getLastname());
        email.setText(userTable.getEmail());
        phone.setText(userTable.getPhone());
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getText().toString().trim().equalsIgnoreCase(userTable.getPhone()))
                    phone.setText("");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });



        return v;
    }


    public void updateAccount()
    {

        if(isEmailValid(email.getText().toString()) && isNameValid(firstname.getText().toString().trim()) && isNameValid(lastname.getText().toString().trim()))
        {


            String mPhone = phone.getText().toString().trim();

            final String phoneNumber = mPhone;

            if(validateUsing_libphonenumber(countryCodePicker.getSelectedCountryCodeWithPlus().toString(),phoneNumber))
            {

                final ProgressDialog pd  = ProgressDialog.show(getContext(),"Updating your account details ..."," Please Wait  ...", true);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiLocation.getApiLocation1())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final Realm realm = Realm.getDefaultInstance();
                final UserTable userTable = realm.where(UserTable.class).findFirst();

                ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);

                Call<UserModel> login = endpoints.update(userTable.getUser_id(),email.getText().toString().trim(),firstname.getText().toString().trim(),lastname.getText().toString().trim(),fullPhone);
                login.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                        UserModel user = response.body();
                        pd.hide();

                        try
                        {
                            Realm realm = Realm.getDefaultInstance();
                            UserTable userTable = realm.where(UserTable.class).findFirst();
                            realm.beginTransaction();
                            userTable.setCreated_at(user.getCreated_at());
                            userTable.setEmail(user.getEmail());
                            userTable.setFirstname(user.getFirstname());
                            userTable.setForgot_code(user.getForgot_code());
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
                            realm.copyToRealmOrUpdate(userTable);
                            realm.commitTransaction();
                            Toast.makeText(getContext(),"Update has been completed, Thank you",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(),MainActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                            return;

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getContext(),"Sorry we couldn't complete your account update. Please try again",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        pd.hide();
                        t.printStackTrace();
                        Toast.makeText(getContext(),"Sorry An Error Eas Encountered Please Try Again Later",Toast.LENGTH_LONG).show();
                    }
                });


            }
            else
            {
               // Toast.makeText(getContext(),"Kindly provide a valid email address",Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Toast.makeText(getContext(),"Kindly provide a valid email address",Toast.LENGTH_LONG).show();
        }


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
            fullPhone = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            return true;
        } else {
            Toast.makeText(getContext(), "Phone Number is Invalid ", Toast.LENGTH_LONG).show();
            return false;
        }
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

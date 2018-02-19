package com.farmarket.farmarket.Api;

import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.Models.UserModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by isaac on 2/17/18.
 */

public interface ApiEndpoints {
    @FormUrlEncoded
    @POST("login")
    Call<UserModel> login(@Field("username") String userName, @Field("password") String password);


    @FormUrlEncoded
    @POST("register")
    Call<UserModel> register(@Field("email") String email, @Field("firstname") String firstname, @Field("lastname") String lastname, @Field("username") String username, @Field("phone") String phone, @Field("password") String password);


    @GET("products")
    Call<List<ProduceModel>> listProducts();



}

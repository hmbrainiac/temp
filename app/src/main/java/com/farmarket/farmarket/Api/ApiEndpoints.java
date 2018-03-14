package com.farmarket.farmarket.Api;

import com.farmarket.farmarket.Models.CategoryModel;
import com.farmarket.farmarket.Models.OrderDetailModel;
import com.farmarket.farmarket.Models.OrderModel;
import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.Models.UserModel;
import com.mobsandgeeks.saripaar.annotation.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by isaac on 2/17/18.
 */

public interface ApiEndpoints {
    @FormUrlEncoded
    @POST("login")
    Call<UserModel> login(@Field("username") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST("contact-us")
    Call<UserModel> contactUs(@Field("email") String userName, @Field("message") String password);

    @FormUrlEncoded
    @POST("register")
    Call<UserModel> register(@Field("email") String email, @Field("firstname") String firstname, @Field("lastname") String lastname, @Field("username") String username, @Field("phone") String phone, @Field("password") String password);


    @FormUrlEncoded
    @POST("update-user")
    Call<UserModel> update(@Field("user_id") int user_id,@Field("email") String email, @Field("firstname") String firstname, @Field("lastname") String lastname,  @Field("phone") String phone);


    @FormUrlEncoded
    @POST("change-password")
    Call<UserModel> updatePassword(@Field("user_id") int user_id,@Field("oldPassword") String oldPassword, @Field("password") String password);


    @FormUrlEncoded
    @POST("pay-invoice")
    Call<UserModel> completePayment(@Field("user_id") int user_id,@Field("invoice_id") int invoice_id, @Field("phone") String phone, @Field("network") String network);

    @FormUrlEncoded
    @POST("set-password")
    Call<UserModel> setPassword(@Field("phone") String phone,@Field("new_password") String new_password);



    @GET("products")
    Call<List<ProduceModel>> listProducts();


    @GET("categories")
    Call<List<CategoryModel>> listCategories();


    @GET("orders/{id}")
    Call<List<OrderModel>> listMyOrders(@Path(value = "id", encoded = true) int id);


    @GET("products/{id}")
    Call<List<ProduceModel>> listCategoryProducts(@Path(value = "id", encoded = true) int id);


    @FormUrlEncoded
    @POST("complete-order")
    Call<OrderModel> completeOrder(@Field("order") JSONObject order, @Field("order_details") JSONArray orderDetail);



    @FormUrlEncoded
    @POST("review-order")
    Call<UserModel> reviewOrder(@Field("order_id") String orderId, @Field("extra") String extra, @Field("delivery") Float delivery, @Field("packaging") Float packaging, @Field("experience") Float experience);


}

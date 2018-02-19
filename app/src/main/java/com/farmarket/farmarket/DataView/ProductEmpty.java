package com.farmarket.farmarket.DataView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by isaac on 2/18/18.
         */

public class ProductEmpty extends RecyclerViewEmptySupport.ViewHolder{
    TextView price,discount,nameProduct,measurement,addToCartTV;
    ImageView addToCart,mainImage;

    public ProductEmpty(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView addToCartTV, ImageView addToCart) {
        super(itemView);
        this.price = price;
        this.discount = discount;
        this.nameProduct = nameProduct;
        this.measurement = measurement;
        this.addToCartTV = addToCartTV;
        this.addToCart = addToCart;
    }

    public ProductEmpty(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView addToCartTV, ImageView addToCart, ImageView mainImage) {
        super(itemView);
        this.price = price;
        this.discount = discount;
        this.nameProduct = nameProduct;
        this.measurement = measurement;
        this.addToCartTV = addToCartTV;
        this.addToCart = addToCart;
        this.mainImage = mainImage;
    }

    public ImageView getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageView mainImage) {
        this.mainImage = mainImage;
    }

    public ProductEmpty(View itemView) {
        super(itemView);
        this.price = (TextView)itemView.findViewById(R.id.priceTopTV);
        this.discount = (TextView)itemView.findViewById(R.id.offTopTV);
        this.nameProduct = (TextView)itemView.findViewById(R.id.nameProduct);
        this.measurement = (TextView)itemView.findViewById(R.id.measurementType);
        this.addToCartTV = (TextView)itemView.findViewById(R.id.addToCartTV);
        this.addToCart = (ImageView) itemView.findViewById(R.id.addToCart);
        this.mainImage = (ImageView)itemView.findViewById(R.id.imageSrc);

    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public TextView getDiscount() {
        return discount;
    }

    public void setDiscount(TextView discount) {
        this.discount = discount;
    }

    public TextView getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(TextView nameProduct) {
        this.nameProduct = nameProduct;
    }

    public TextView getMeasurement() {
        return measurement;
    }

    public void setMeasurement(TextView measurement) {
        this.measurement = measurement;
    }

    public TextView getAddToCartTV() {
        return addToCartTV;
    }

    public void setAddToCartTV(TextView addToCartTV) {
        this.addToCartTV = addToCartTV;
    }

    public ImageView getAddToCart() {
        return addToCart;
    }

    public void setAddToCart(ImageView addToCart) {
        this.addToCart = addToCart;
    }
}

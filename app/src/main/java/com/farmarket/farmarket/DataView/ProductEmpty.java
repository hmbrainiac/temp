package com.farmarket.farmarket.DataView;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by isaac on 2/18/18.
         */

public class ProductEmpty extends RecyclerViewEmptySupport.ViewHolder{
    TextView price,discount,nameProduct,measurement,addToCartTV;
    ImageView addToCart,mainImage;
    RelativeLayout dividerView;
    CardView cardView;

    public ProductEmpty(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView addToCartTV, ImageView addToCart) {
        super(itemView);
        this.price = (TextView)itemView.findViewById(R.id.priceTopTV);
        this.discount = (TextView)itemView.findViewById(R.id.offTopTV);
        this.nameProduct = (TextView)itemView.findViewById(R.id.nameProduct);
        this.measurement = (TextView)itemView.findViewById(R.id.measurementType);
        this.addToCartTV = (TextView)itemView.findViewById(R.id.addToCartTV);
        this.addToCart = (ImageView) itemView.findViewById(R.id.addToCart);
        this.mainImage = (ImageView)itemView.findViewById(R.id.imageSrc);
        this.dividerView = (RelativeLayout)itemView.findViewById(R.id.dividerView);
    }

    public ProductEmpty(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView addToCartTV, ImageView addToCart, ImageView mainImage, RelativeLayout dividerView) {
        super(itemView);
        this.price = (TextView)itemView.findViewById(R.id.priceTopTV);
        this.discount = (TextView)itemView.findViewById(R.id.offTopTV);
        this.nameProduct = (TextView)itemView.findViewById(R.id.nameProduct);
        this.measurement = (TextView)itemView.findViewById(R.id.measurementType);
        this.addToCartTV = (TextView)itemView.findViewById(R.id.addToCartTV);
        this.addToCart = (ImageView) itemView.findViewById(R.id.addToCart);
        this.mainImage = (ImageView)itemView.findViewById(R.id.imageSrc);
        this.dividerView = (RelativeLayout)itemView.findViewById(R.id.dividerView);
    }

    public ProductEmpty(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView addToCartTV, ImageView addToCart, ImageView mainImage, RelativeLayout dividerView, CardView cardView) {
        super(itemView);
        this.price = (TextView)itemView.findViewById(R.id.priceTopTV);
        this.discount = (TextView)itemView.findViewById(R.id.offTopTV);
        this.nameProduct = (TextView)itemView.findViewById(R.id.nameProduct);
        this.measurement = (TextView)itemView.findViewById(R.id.measurementType);
        this.addToCartTV = (TextView)itemView.findViewById(R.id.addToCartTV);
        this.addToCart = (ImageView) itemView.findViewById(R.id.addToCart);
        this.mainImage = (ImageView)itemView.findViewById(R.id.imageSrc);
        this.dividerView = (RelativeLayout)itemView.findViewById(R.id.dividerView);
        this.cardView = (CardView) itemView.findViewById(R.id.card_view);
    }

    public RelativeLayout getDividerView() {
        return dividerView;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public void setDividerView(RelativeLayout dividerView) {
        this.dividerView = dividerView;
    }

    public ProductEmpty(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView addToCartTV, ImageView addToCart, ImageView mainImage) {
        super(itemView);
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
        this.addToCart = (ImageView) itemView.findViewById(R.id.removeFromCart);
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

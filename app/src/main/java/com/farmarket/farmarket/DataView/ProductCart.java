package com.farmarket.farmarket.DataView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by isaac on 2/18/18.
 */

public class ProductCart extends RecyclerViewEmptySupport.ViewHolder{
    TextView price,discount,nameProduct,measurement,quantityInCart;
    ImageView reduceCart,increaseCart,mainImage;

    public ProductCart(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView quantityInCart, ImageView reduceCart, ImageView increaseCart) {
        super(itemView);
        this.price = price;
        this.discount = discount;
        this.nameProduct = nameProduct;
        this.measurement = measurement;
        this.quantityInCart = quantityInCart;
        this.reduceCart = reduceCart;
        this.increaseCart = increaseCart;
    }

    public ProductCart(View itemView, TextView price, TextView discount, TextView nameProduct, TextView measurement, TextView quantityInCart, ImageView reduceCart, ImageView increaseCart, ImageView mainImage) {
        super(itemView);
        this.price = price;
        this.discount = discount;
        this.nameProduct = nameProduct;
        this.measurement = measurement;
        this.quantityInCart = quantityInCart;
        this.reduceCart = reduceCart;
        this.increaseCart = increaseCart;
        this.mainImage = mainImage;
    }

    public ImageView getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageView mainImage) {
        this.mainImage = mainImage;
    }

    public ProductCart(View itemView) {
        super(itemView);
        this.price = (TextView) itemView.findViewById(R.id.priceTopTV);
        this.discount =(TextView) itemView.findViewById(R.id.offTopTV);
        this.nameProduct = (TextView)itemView.findViewById(R.id.nameProduct);
        this.measurement = (TextView)itemView.findViewById(R.id.measurementType);
        this.quantityInCart = (TextView)itemView.findViewById(R.id.quantityInCart);

        this.reduceCart = (ImageView)itemView.findViewById(R.id.removeFromCart);
        this.increaseCart = (ImageView)itemView.findViewById(R.id.addToCart);
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

    public TextView getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(TextView quantityInCart) {
        this.quantityInCart = quantityInCart;
    }

    public ImageView getReduceCart() {
        return reduceCart;
    }

    public void setReduceCart(ImageView reduceCart) {
        this.reduceCart = reduceCart;
    }

    public ImageView getIncreaseCart() {
        return increaseCart;
    }

    public void setIncreaseCart(ImageView increaseCart) {
        this.increaseCart = increaseCart;
    }
}

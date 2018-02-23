package com.farmarket.farmarket.DataView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by isaac on 2/22/18.
 */

public class CartDetail extends RecyclerViewEmptySupport.ViewHolder{
    TextView productType,productName,productWeight,productPrice,removeFromCart;
    EditText currentWeight;
    ImageView mainImage,plusImage,minusImage;

    public CartDetail(View itemView) {
        super(itemView);
        this.productType = (TextView)itemView.findViewById(R.id.offTopTV);
        this.productName = (TextView)itemView.findViewById(R.id.nameProduct);
        this.productWeight = (TextView)itemView.findViewById(R.id.measurementType);
        this.productPrice = (TextView)itemView.findViewById(R.id.totalCost);
        this.removeFromCart = (TextView)itemView.findViewById(R.id.closeTV);
        this.currentWeight = (EditText) itemView.findViewById(R.id.currentWeight);
        this.mainImage = (ImageView) itemView.findViewById(R.id.imageSrc);
        this.plusImage = (ImageView)itemView.findViewById(R.id.plusImage);
        this.minusImage = (ImageView)itemView.findViewById(R.id.minusImage);

    }

    public CartDetail(View itemView, TextView productType, TextView productName, TextView productWeight, TextView productPrice, TextView removeFromCart, EditText currentWeight, ImageView mainImage, ImageView plusImage, ImageView minusImage) {
        super(itemView);
        this.productType = (TextView)itemView.findViewById(R.id.offTopTV);
        this.productName = (TextView)itemView.findViewById(R.id.nameProduct);
        this.productWeight = (TextView)itemView.findViewById(R.id.measurementType);
        this.productPrice = (TextView)itemView.findViewById(R.id.totalCost);
        this.removeFromCart = (TextView)itemView.findViewById(R.id.closeTV);
        this.currentWeight = (EditText) itemView.findViewById(R.id.currentWeight);
        this.mainImage = (ImageView) itemView.findViewById(R.id.imageSrc);
        this.plusImage = (ImageView)itemView.findViewById(R.id.plusImage);
        this.minusImage = (ImageView)itemView.findViewById(R.id.minusImage);
    }

    public TextView getProductType() {
        return productType;

    }

    public void setProductType(TextView productType) {
        this.productType = productType;
    }

    public TextView getProductName() {
        return productName;
    }

    public void setProductName(TextView productName) {
        this.productName = productName;
    }

    public TextView getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(TextView productWeight) {
        this.productWeight = productWeight;
    }

    public TextView getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(TextView productPrice) {
        this.productPrice = productPrice;
    }

    public TextView getRemoveFromCart() {
        return removeFromCart;
    }

    public void setRemoveFromCart(TextView removeFromCart) {
        this.removeFromCart = removeFromCart;
    }

    public EditText getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(EditText currentWeight) {
        this.currentWeight = currentWeight;
    }

    public ImageView getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageView mainImage) {
        this.mainImage = mainImage;
    }

    public ImageView getPlusImage() {
        return plusImage;
    }

    public void setPlusImage(ImageView plusImage) {
        this.plusImage = plusImage;
    }

    public ImageView getMinusImage() {
        return minusImage;
    }

    public void setMinusImage(ImageView minusImage) {
        this.minusImage = minusImage;
    }
}

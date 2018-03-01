package com.farmarket.farmarket.DataView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by admin on 27/02/2018.
 */

public class OrderDetail extends RecyclerViewEmptySupport.ViewHolder{
    TextView productType,productName,productWeight,productPrice,removeFromCart;
    EditText currentWeight;
    ImageView mainImage;

    public OrderDetail(View itemView) {
        super(itemView);
        this.productType = (TextView)itemView.findViewById(R.id.offTopTV);
        this.productName = (TextView)itemView.findViewById(R.id.nameProduct);
        this.productWeight = (TextView)itemView.findViewById(R.id.measurementType);
        this.productPrice = (TextView)itemView.findViewById(R.id.totalCost);
        this.removeFromCart = (TextView)itemView.findViewById(R.id.closeTV);
        this.currentWeight = (EditText) itemView.findViewById(R.id.currentWeight);
        this.mainImage = (ImageView) itemView.findViewById(R.id.imageSrc);
    }

    public OrderDetail(View itemView, TextView productType, TextView productName, TextView productWeight, TextView productPrice, TextView removeFromCart, EditText currentWeight, ImageView mainImage) {
        super(itemView);
        this.productType = (TextView)itemView.findViewById(R.id.offTopTV);
        this.productName = (TextView)itemView.findViewById(R.id.nameProduct);
        this.productWeight = (TextView)itemView.findViewById(R.id.measurementType);
        this.productPrice = (TextView)itemView.findViewById(R.id.totalCost);
        this.removeFromCart = (TextView)itemView.findViewById(R.id.closeTV);
        this.currentWeight = (EditText) itemView.findViewById(R.id.currentWeight);
        this.mainImage = (ImageView) itemView.findViewById(R.id.imageSrc);
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
}

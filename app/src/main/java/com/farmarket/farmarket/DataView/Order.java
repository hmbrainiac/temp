package com.farmarket.farmarket.DataView;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by admin on 27/02/2018.
 */

public class Order extends RecyclerViewEmptySupport.ViewHolder{
    TextView orderStatus,orderCreationDate,orderCode,sumWeightOrder,orderTotal,paymentStatus;
    TextView completePayment,viewDetails;

    CardView cardView;

    public Order(View itemView, TextView orderStatus, TextView orderCreationDate, TextView orderCode, TextView sumWeightOrder, TextView orderTotal, TextView paymentStatus, TextView completePayment, TextView viewDetails, CardView cardView) {
        super(itemView);
        this.orderCreationDate = (TextView) itemView.findViewById(R.id.closeTV);
        this.orderCode = (TextView) itemView.findViewById(R.id.nameProduct);
        this.sumWeightOrder = (TextView) itemView.findViewById(R.id.measurementType);
        this.orderStatus =(TextView) itemView.findViewById(R.id.offTopTV);
        this.cardView =(CardView) itemView.findViewById(R.id.card_view);
        this.orderTotal =(TextView) itemView.findViewById(R.id.totalCost);
        this.paymentStatus =(TextView) itemView.findViewById(R.id.offBottomTV);
        this.completePayment =(TextView) itemView.findViewById(R.id.completePayment);
        this.viewDetails =(TextView) itemView.findViewById(R.id.viewDetails);
    }

    public Order(View itemView) {
        super(itemView);
        this.orderCreationDate = (TextView) itemView.findViewById(R.id.closeTV);
        this.orderCode = (TextView) itemView.findViewById(R.id.nameProduct);
        this.sumWeightOrder = (TextView) itemView.findViewById(R.id.measurementType);
        this.orderStatus =(TextView) itemView.findViewById(R.id.offTopTV);
        this.cardView =(CardView) itemView.findViewById(R.id.card_view);
        this.orderTotal =(TextView) itemView.findViewById(R.id.totalCost);
        this.paymentStatus =(TextView) itemView.findViewById(R.id.offBottomTV);
        this.completePayment =(TextView) itemView.findViewById(R.id.completePayment);
        this.viewDetails =(TextView) itemView.findViewById(R.id.viewDetails);
    }

    public TextView getCompletePayment() {
        return completePayment;
    }

    public void setCompletePayment(Button completePayment) {
        this.completePayment = completePayment;
    }

    public TextView getViewDetails() {
        return viewDetails;
    }

    public void setViewDetails(Button viewDetails) {
        this.viewDetails = viewDetails;
    }

    public TextView getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(TextView paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public TextView getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(TextView orderStatus) {
        this.orderStatus = orderStatus;
    }

    public TextView getOrderCreationDate() {
        return orderCreationDate;
    }

    public void setOrderCreationDate(TextView orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    public TextView getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(TextView orderCode) {
        this.orderCode = orderCode;
    }

    public TextView getSumWeightOrder() {
        return sumWeightOrder;
    }

    public void setSumWeightOrder(TextView sumWeightOrder) {
        this.sumWeightOrder = sumWeightOrder;
    }

    public TextView getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(TextView orderTotal) {
        this.orderTotal = orderTotal;
    }
}

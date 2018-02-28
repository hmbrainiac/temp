package com.farmarket.farmarket.DataView;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by admin on 27/02/2018.
 */

public class Order extends RecyclerViewEmptySupport.ViewHolder{
    TextView orderStatus,orderCreationDate,orderCode,sumWeightOrder,orderTotal;

    CardView cardView;

    public Order(View itemView, TextView orderStatus, TextView orderCreationDate, TextView orderCode, TextView sumWeightOrder, TextView orderTotal, CardView cardView) {
        super(itemView);
        this.orderCreationDate = (TextView) itemView.findViewById(R.id.closeTV);
        this.orderCode = (TextView) itemView.findViewById(R.id.nameProduct);
        this.sumWeightOrder = (TextView) itemView.findViewById(R.id.measurementType);
        this.orderStatus =(TextView) itemView.findViewById(R.id.offTopTV);
        this.cardView =(CardView) itemView.findViewById(R.id.card_view);
        this.orderTotal =(TextView) itemView.findViewById(R.id.totalCost);
    }

    public Order(View itemView) {
        super(itemView);
        this.orderCreationDate = (TextView) itemView.findViewById(R.id.closeTV);
        this.orderCode = (TextView) itemView.findViewById(R.id.nameProduct);
        this.sumWeightOrder = (TextView) itemView.findViewById(R.id.measurementType);
        this.orderStatus =(TextView) itemView.findViewById(R.id.offTopTV);
        this.cardView =(CardView) itemView.findViewById(R.id.card_view);
        this.orderTotal =(TextView) itemView.findViewById(R.id.totalCost);
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

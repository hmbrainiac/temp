package com.farmarket.farmarket.DataView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by isaac on 2/22/18.
 */

public class CartTotal extends  RecyclerViewEmptySupport.ViewHolder {
    TextView subTotal,total;
    Button toCheckOutBtn;
    public CartTotal(View itemView) {
        super(itemView);
        subTotal = (TextView)itemView.findViewById(R.id.subTotalTV);
        total = (TextView)itemView.findViewById(R.id.totalCostTV);
        toCheckOutBtn = (Button)itemView.findViewById(R.id.toCheckout);

    }

    public CartTotal(View itemView, TextView subTotal, TextView total) {
        super(itemView);
        subTotal = (TextView)itemView.findViewById(R.id.subTotalTV);
        total = (TextView)itemView.findViewById(R.id.totalCostTV);
        toCheckOutBtn = (Button)itemView.findViewById(R.id.toCheckout);
    }

    public Button getToCheckOutBtn() {
        return toCheckOutBtn;
    }

    public void setToCheckOutBtn(Button toCheckOutBtn) {
        this.toCheckOutBtn = toCheckOutBtn;
    }

    public TextView getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(TextView subTotal) {
        this.subTotal = subTotal;
    }

    public TextView getTotal() {
        return total;
    }

    public void setTotal(TextView total) {
        this.total = total;
    }
}

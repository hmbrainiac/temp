package com.farmarket.farmarket.DataView;

import android.view.View;
import android.widget.TextView;

import com.farmarket.farmarket.Misc.RecyclerViewEmptySupport;
import com.farmarket.farmarket.R;

/**
 * Created by isaac on 3/17/18.
 */

public class WalletTransaction extends RecyclerViewEmptySupport.ViewHolder{
    TextView amountAdded,closingBalance,typeName,rightName;

    public WalletTransaction(View itemView, TextView amountAdded, TextView closingBalance) {
        super(itemView);

        this.amountAdded = itemView.findViewById(R.id.amountAdded);
        this.closingBalance = itemView.findViewById(R.id.closingAmount);
    }

    public WalletTransaction(View itemView, TextView amountAdded, TextView closingBalance, TextView typeName, TextView rightName) {
        super(itemView);
        this.typeName = itemView.findViewById(R.id.typeName);
        this.rightName = itemView.findViewById(R.id.offTopTV);

        this.amountAdded = itemView.findViewById(R.id.amountAdded);
        this.closingBalance = itemView.findViewById(R.id.closingAmount);

    }

    public WalletTransaction(View itemView) {
        super(itemView);
        this.amountAdded = itemView.findViewById(R.id.amountAdded);
        this.closingBalance = itemView.findViewById(R.id.closingAmount);
        this.typeName = itemView.findViewById(R.id.typeName);
        this.rightName = itemView.findViewById(R.id.offTopTV);
    }

    public TextView getTypeName() {
        return typeName;
    }

    public void setTypeName(TextView typeName) {
        this.typeName = typeName;
    }

    public TextView getRightName() {
        return rightName;
    }

    public void setRightName(TextView rightName) {
        this.rightName = rightName;
    }

    public TextView getAmountAdded() {
        return amountAdded;
    }

    public void setAmountAdded(TextView amountAdded) {
        this.amountAdded = amountAdded;
    }

    public TextView getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(TextView closingBalance) {
        this.closingBalance = closingBalance;
    }
}

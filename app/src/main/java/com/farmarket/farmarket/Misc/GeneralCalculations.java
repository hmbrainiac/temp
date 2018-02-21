package com.farmarket.farmarket.Misc;

/**
 * Created by admin on 20/02/2018.
 */

public class GeneralCalculations {

    public static double getCost(double unitPrice,double weight)
    {
        return Math.round((weight*unitPrice)*100.0)/100.0;
    }

}

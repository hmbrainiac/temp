package com.farmarket.farmarket.Misc;

/**
 * Created by admin on 20/02/2018.
 */

public class GeneralCalculations {

    public static double getCost(double unitPrice,double weight)
    {
        return Math.round((weight*unitPrice)*100.0)/100.0;
    }

    public static double getDisplay(double incremental,double number)
    {
        return Math.round((incremental*number)*100.0)/100.0;
    }

    public static String getDisplayWithMeasurement(double incremental,double number,String measurement)
    {
        return Math.round((incremental*number)*100.0)/100.0+" "+measurement;
    }

    public static double getChargeOnTotal(double total, double delivery)
    {
        double number = total +delivery;

        return Math.round((number* 0.029)*100.0)/100.0;
    }
}

package com.farmarket.farmarket.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by isaac on 1/30/18.
 */

public class TTCorals  extends android.support.v7.widget.AppCompatTextView {
    public TTCorals(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"TT_Corals_Thin-DEMO.otf"));

    }
}
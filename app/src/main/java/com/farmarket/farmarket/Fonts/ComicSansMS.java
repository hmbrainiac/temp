package com.farmarket.farmarket.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by admin on 14/03/2018.
 */

public class ComicSansMS extends android.support.v7.widget.AppCompatTextView {
    public ComicSansMS(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"comic_sans_ms.ttf"));

    }
}
package com.sixd.greek.house.chefs.cuastomviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Praveen on 24-Jul-17.
 */

public class TextViewRegular extends TextView {
    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf"));
    }
}

package com.sixd.greek.house.chefs.cuastomviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Praveen on 24-Jul-17.
 */

public class TextViewSemiBold extends TextView {
    public TextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/opensans_semibold.ttf"));
        this.setLineSpacing(0,0.8f);
    }
}

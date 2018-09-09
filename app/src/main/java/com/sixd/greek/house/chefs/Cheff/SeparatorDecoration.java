package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.CheffAdapter.ForumCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.ForumTopicsAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemAdapter;
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllCategoryTopics;
import dao_db.AllFormCategory;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class SeparatorDecoration extends RecyclerView.ItemDecoration {

    private final Paint mPaint;

    /**
     * Create a decoration that draws a line in the given color and width between the items in the view.
     *
     * @param context  a context to access the resources.
     * @param color    the color of the separator to draw.
     * @param heightDp the height of the separator in dp.
     */
    public SeparatorDecoration(@NonNull Context context, @ColorInt int color,
                               @FloatRange(from = 0, fromInclusive = false) float heightDp) {
        mPaint = new Paint();
        mPaint.setColor(color);
        final float thickness = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                heightDp, context.getResources().getDisplayMetrics());
        mPaint.setStrokeWidth(thickness);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

        // we want to retrieve the position in the list
        final int position = params.getViewAdapterPosition();

        // and add a separator to any view but the last one
        if (position < state.getItemCount()) {
            outRect.set(0, 0, 0, (int) mPaint.getStrokeWidth()); // left, top, right, bottom
        } else {
            outRect.setEmpty(); // 0, 0, 0, 0
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // we set the stroke width before, so as to correctly draw the line we have to offset by width / 2
        final int offset = (int) (mPaint.getStrokeWidth() / 2);

        // this will iterate over every visible view
        for (int i = 0; i < parent.getChildCount(); i++) {
            // get the view
            final View view = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

            // get the position
            final int position = params.getViewAdapterPosition();

            // and finally draw the separator
            if (position < state.getItemCount()) {
                c.drawLine(view.getLeft(), view.getBottom() + offset, view.getRight(), view.getBottom() + offset, mPaint);
            }
        }
    }
}
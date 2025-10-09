package com.infovsn.homework;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public final class ActionBarUtil {
    private ActionBarUtil() {}

    public static void centerTitle(AppCompatActivity activity, @StringRes int titleRes) {
        ActionBar ab = activity.getSupportActionBar();
        if (ab == null) return;
        TextView tv = new TextView(activity);
        tv.setText(titleRes);
        tv.setTextColor(ContextCompat.getColor(activity, R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tv.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        tv.setSingleLine(true);
        tv.setGravity(Gravity.CENTER);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        ab.setCustomView(tv, lp);
    }
}


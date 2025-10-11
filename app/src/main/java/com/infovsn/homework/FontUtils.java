package com.infovsn.homework;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public final class FontUtils {
    private static Typeface cached;

    private FontUtils() {}

    private static Typeface getTypeface(Context ctx) {
        if (cached != null) return cached;
        try {
            cached = Typeface.createFromAsset(ctx.getAssets(), "fonts/RobotoMono-Regular.ttf");
        } catch (Exception e) {
            cached = Typeface.MONOSPACE;
        }
        return cached;
    }

    // Public accessor so views can explicitly opt into Roboto Mono where needed
    public static Typeface getRobotoMono(Context ctx) {
        return getTypeface(ctx);
    }

    public static void applyToActivity(Activity activity) {
        View root = activity.findViewById(android.R.id.content);
        if (root == null) return;
        Typeface tf = getTypeface(activity);
        applyRecursive(root, tf);
    }

    private static void applyRecursive(View v, Typeface tf) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                applyRecursive(vg.getChildAt(i), tf);
            }
            return;
        }
        if (v instanceof TextView && !(v instanceof Button)) {
            TextView tv = (TextView) v;
            tv.setTypeface(tf);
            // Min SDK is 21 per build.gradle, so letterSpacing API is available
            tv.setLetterSpacing(0f);
        }
    }
}

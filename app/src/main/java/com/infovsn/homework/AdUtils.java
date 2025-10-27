package com.infovsn.homework;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Helper to load anchored adaptive banner ads into an existing AdView.
 *
 * Usage:
 *   AdView adView = findViewById(R.id.adView);
 *   AdUtils.loadAdaptiveBanner(this, adView);
 */
public final class AdUtils {
    private AdUtils() {}

    public static void loadAdaptiveBanner(final Activity activity, final AdView adView) {
        if (activity == null || adView == null) return;

        // Post to ensure adView has been laid out and has a width; if 0, fallback to screen width
        adView.post(new Runnable() {
            @Override public void run() {
                try {
                    AdSize adSize = getAdSize(activity, adView);
                    if (adSize != null) {
                        // Set adaptive size computed for current orientation and available width
                        adView.setAdSize(adSize);
                    }
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                } catch (Throwable t) {
                    // Fail-safe: try loading without resetting size
                    try {
                        AdRequest adRequest = new AdRequest.Builder().build();
                        adView.loadAd(adRequest);
                    } catch (Throwable ignore) { }
                }
            }
        });
    }

    /**
     * Computes the best width in dp for the adaptive banner:
     * - On API 30+, uses WindowMetrics and subtracts system bar insets so the ad fits the content area.
     * - Otherwise, falls back to DisplayMetrics or the view's measured width if available.
     */
    private static AdSize getAdSize(Activity activity, View anchor) {
        int adWidthPixels = 0;

        // Prefer the anchor width if measured (handles cases where the ad is inside padded containers)
        int measuredWidth = anchor.getWidth();
        if (measuredWidth > 0) {
            adWidthPixels = measuredWidth;
        } else {
            // Fallback: compute from window size
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
                    int width = windowMetrics.getBounds().width();
                    // Subtract system bars (status, nav) to get the content width
                    WindowInsets insets = windowMetrics.getWindowInsets();
                    int insetLeft = insets.getInsets(WindowInsets.Type.systemBars()).left;
                    int insetRight = insets.getInsets(WindowInsets.Type.systemBars()).right;
                    adWidthPixels = Math.max(0, width - insetLeft - insetRight);
                } else {
                    DisplayMetrics outMetrics = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                    adWidthPixels = outMetrics.widthPixels;
                }
            } catch (Throwable ignored) {
                // As a last resort, fall back to DisplayMetrics
                DisplayMetrics outMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                adWidthPixels = outMetrics.widthPixels;
            }
        }

        // Convert pixels to density-independent pixels for the AdSize API
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int adWidth = (int) (adWidthPixels / density);
        if (adWidth <= 0) adWidth = 320; // conservative fallback

        // Return anchored adaptive size for the current orientation
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }
}

package com.infovsn.homework;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

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

    private static AdSize getAdSize(Activity activity, View anchor) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int adWidthPixels = anchor.getWidth();
        if (adWidthPixels <= 0) {
            adWidthPixels = outMetrics.widthPixels; // fallback to full screen width
        }
        float density = outMetrics.density;
        int adWidth = (int) (adWidthPixels / density);
        if (adWidth <= 0) adWidth = 320; // conservative fallback
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }
}


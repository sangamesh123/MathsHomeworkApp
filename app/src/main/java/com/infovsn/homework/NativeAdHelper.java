package com.infovsn.homework;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.AdLoader;

public final class NativeAdHelper implements DefaultLifecycleObserver {
    private static final int MIN_BANNER_DP = 50; // typical phone banner height

    private final Activity activity;
    private NativeAd currentNative;
    private String lastLayoutName;

    private NativeAdHelper(Activity activity) {
        this.activity = activity;
        if (activity instanceof LifecycleOwner) {
            ((LifecycleOwner) activity).getLifecycle().addObserver(this);
        }
    }

    public static void loadDynamicNativeOrBanner(@NonNull Activity activity,
                                                 @NonNull MaxHeightFrameLayout adContainer,
                                                 int remainingHeightPx) {
        NativeAdHelper helper = new NativeAdHelper(activity);
        helper.loadInto(adContainer, remainingHeightPx);
    }

    private void loadInto(@NonNull MaxHeightFrameLayout adContainer, int remainingHeightPx) {
        if (remainingHeightPx <= 0) {
            adContainer.setVisibility(View.GONE);
            return;
        }
        adContainer.setVisibility(View.VISIBLE);
        adContainer.setMaxHeightPx(remainingHeightPx);

        int remainingDp = pxToDp(remainingHeightPx);

        String layoutName;
        if (remainingDp >= 420) {
            layoutName = "native_ad_large";
        } else if (remainingDp >= 280) {
            layoutName = "native_ad_medium";
        } else if (remainingDp >= 140) {
            layoutName = "native_ad_small";
        } else {
            // Not enough space for a native ad. Try banner only if enough room for banner height.
            if (remainingDp < MIN_BANNER_DP) {
                adContainer.setVisibility(View.GONE);
                return;
            }
            loadBannerFallback(adContainer);
            return;
        }
        lastLayoutName = layoutName;

        int layoutRes = activity.getResources().getIdentifier(layoutName, "layout", activity.getPackageName());
        if (layoutRes == 0) {
            // If for any reason layout isn't found, fallback to banner if enough space
            if (remainingDp < MIN_BANNER_DP) {
                adContainer.setVisibility(View.GONE);
            } else {
                loadBannerFallback(adContainer);
            }
            return;
        }

        final View adView = LayoutInflater.from(activity).inflate(layoutRes, adContainer, false);

        AdLoader adLoader = new AdLoader.Builder(activity, activity.getString(R.string.native_ad_unit_id))
                .forNativeAd(nativeAd -> {
                    if (activity.isFinishing() || (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed())) {
                        nativeAd.destroy();
                        return;
                    }
                    destroyCurrent();
                    currentNative = nativeAd;
                    bindNative(adView, nativeAd);
                    adContainer.removeAllViews();
                    FrameLayout.LayoutParams lp;
                    if ("native_ad_large".equals(lastLayoutName)) {
                        // Expand to available height so MediaView (weight=1) can fill the remaining space
                        lp = new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                remainingHeightPx
                        );
                    } else {
                        lp = new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                    }
                    adContainer.addView(adView, lp);
                })
                .withAdListener(new AdListener() {
                    @Override public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        if (pxToDp(adContainer.getHeight()) < MIN_BANNER_DP) {
                            adContainer.setVisibility(View.GONE);
                        } else {
                            loadBannerFallback(adContainer);
                        }
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build())
                        .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void loadBannerFallback(@NonNull MaxHeightFrameLayout adContainer) {
        adContainer.removeAllViews();
        AdView banner = new AdView(activity);
        banner.setAdUnitId(activity.getString(R.string.banner_ad_unit_id));
        AdSize adSize = AdUtilsBannerSize.getAdSize(activity, adContainer);
        banner.setAdSize(adSize);
        adContainer.addView(banner, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        banner.loadAd(new AdRequest.Builder().build());
    }

    private void bindNative(@NonNull View adView, @NonNull NativeAd nativeAd) {
        int idNativeAdView = id("native_ad_view");
        int idMedia = id("ad_media");
        int idHeadline = id("ad_headline");
        int idBody = id("ad_body");
        int idCta = id("ad_call_to_action");
        int idIcon = id("ad_app_icon");
        int idAdv = id("ad_advertiser");

        com.google.android.gms.ads.nativead.NativeAdView nav = adView.findViewById(idNativeAdView);
        MediaView mediaView = adView.findViewById(idMedia);
        if (nav == null) return;
        if (mediaView != null) nav.setMediaView(mediaView);
        if (idHeadline != 0) nav.setHeadlineView(adView.findViewById(idHeadline));
        if (idBody != 0) nav.setBodyView(adView.findViewById(idBody));
        if (idCta != 0) nav.setCallToActionView(adView.findViewById(idCta));
        if (idIcon != 0) nav.setIconView(adView.findViewById(idIcon));
        if (idAdv != 0) nav.setAdvertiserView(adView.findViewById(idAdv));

        // Populate assets
        if (nav.getHeadlineView() instanceof TextView) {
            ((TextView) nav.getHeadlineView()).setText(nativeAd.getHeadline());
        }
        if (nav.getBodyView() instanceof TextView) {
            if (nativeAd.getBody() == null) {
                nav.getBodyView().setVisibility(View.GONE);
            } else {
                ((TextView) nav.getBodyView()).setText(nativeAd.getBody());
                nav.getBodyView().setVisibility(View.VISIBLE);
            }
        }
        if (nav.getCallToActionView() instanceof TextView) {
            if (nativeAd.getCallToAction() == null) {
                nav.getCallToActionView().setVisibility(View.GONE);
            } else {
                ((TextView) nav.getCallToActionView()).setText(nativeAd.getCallToAction());
                nav.getCallToActionView().setVisibility(View.VISIBLE);
            }
        }
        if (nav.getIconView() instanceof ImageView) {
            if (nativeAd.getIcon() == null) {
                nav.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) nav.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                nav.getIconView().setVisibility(View.VISIBLE);
            }
        }
        if (nav.getAdvertiserView() instanceof TextView) {
            if (nativeAd.getAdvertiser() == null) {
                nav.getAdvertiserView().setVisibility(View.GONE);
            } else {
                ((TextView) nav.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                nav.getAdvertiserView().setVisibility(View.VISIBLE);
            }
        }

        nav.setNativeAd(nativeAd);
    }

    private void destroyCurrent() {
        try {
            if (currentNative != null) {
                currentNative.destroy();
                currentNative = null;
            }
        } catch (Throwable ignore) {}
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        destroyCurrent();
        DefaultLifecycleObserver.super.onDestroy(owner);
    }

    private int pxToDp(int px) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return Math.round(px / dm.density);
    }

    private int id(String name) {
        return activity.getResources().getIdentifier(name, "id", activity.getPackageName());
    }

    /**
     * Local helper to compute adaptive banner size based on a view width.
     */
    private static final class AdUtilsBannerSize {
        static AdSize getAdSize(Activity activity, View anchor) {
            int adWidthPixels = anchor.getWidth();
            if (adWidthPixels <= 0) {
                DisplayMetrics outMetrics = activity.getResources().getDisplayMetrics();
                adWidthPixels = outMetrics.widthPixels;
            }
            int adWidth = (int) (adWidthPixels / activity.getResources().getDisplayMetrics().density);
            if (adWidth <= 0) adWidth = 320;
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
        }
    }
}

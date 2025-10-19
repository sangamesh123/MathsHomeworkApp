package com.infovsn.homework;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
    private boolean lastWasLarge;

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

    /**
     * Convenience: attach dynamic native-or-banner loading to a layout.
     * Measures remaining space below the given content view inside the activity root and loads an ad into the container.
     */
    public static void attachToContainerOnLayout(@NonNull final Activity activity,
                                                 final int contentViewId,
                                                 final int adContainerId) {
        final View root = activity.findViewById(android.R.id.content);
        final View content = activity.findViewById(contentViewId);
        final MaxHeightFrameLayout adContainer = activity.findViewById(adContainerId);
        if (root == null || content == null || adContainer == null) return;
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int rootH = root.getHeight();
                int contentH = content.getHeight();
                int remaining = Math.max(0, rootH - contentH);
                if (remaining <= 0) {
                    adContainer.setVisibility(View.GONE);
                } else {
                    NativeAdHelper.loadDynamicNativeOrBanner(activity, adContainer, remaining);
                }
            }
        });
    }

    private void loadInto(@NonNull MaxHeightFrameLayout adContainer, int remainingHeightPx) {
        if (remainingHeightPx <= 0) {
            adContainer.setVisibility(View.GONE);
            return;
        }
        adContainer.setVisibility(View.VISIBLE);
        adContainer.setMaxHeightPx(remainingHeightPx);

        int remainingDp = pxToDp(remainingHeightPx);

        int layoutRes;
        if (remainingDp >= 420) {
            layoutRes = R.layout.native_ad_large; lastWasLarge = true;
        } else if (remainingDp >= 280) {
            layoutRes = R.layout.native_ad_medium; lastWasLarge = false;
        } else if (remainingDp >= 140) {
            layoutRes = R.layout.native_ad_small; lastWasLarge = false;
        } else {
            // Not enough space for a native ad. Try banner only if enough room for banner height.
            if (remainingDp < MIN_BANNER_DP) {
                adContainer.setVisibility(View.GONE);
                return;
            }
            loadBannerFallback(adContainer);
            return;
        }

        final View adView = LayoutInflater.from(activity).inflate(layoutRes, adContainer, false);

        AdLoader adLoader = new AdLoader.Builder(activity, activity.getString(R.string.native_ad_unit_id))
                .forNativeAd(nativeAd -> {
                    // Activity lifecycle safety
                    if (activity.isFinishing() || activity.isDestroyed()) {
                        nativeAd.destroy();
                        return;
                    }
                    destroyCurrent();
                    currentNative = nativeAd;
                    bindNative(adView, nativeAd);
                    adContainer.removeAllViews();
                    FrameLayout.LayoutParams lp;
                    if (lastWasLarge) {
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
        com.google.android.gms.ads.nativead.NativeAdView nav = adView.findViewById(R.id.native_ad_view);
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        if (nav == null) return;
        if (mediaView != null) nav.setMediaView(mediaView);
        nav.setHeadlineView(adView.findViewById(R.id.ad_headline));
        nav.setBodyView(adView.findViewById(R.id.ad_body));
        nav.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        nav.setIconView(adView.findViewById(R.id.ad_app_icon));
        nav.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

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

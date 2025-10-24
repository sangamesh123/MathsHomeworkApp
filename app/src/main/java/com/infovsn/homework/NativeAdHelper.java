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
    // Conservative thresholds to reduce overflow risk; final guard is measurement
    private static final int MIN_NATIVE_LARGE_DP = 400;
    private static final int MIN_NATIVE_MEDIUM_DP = 300;
    private static final int MIN_NATIVE_SMALL_DP = 160;

    private final Activity activity;
    private NativeAd currentNative;
    private AdView currentBanner;

    private NativeAdHelper(Activity activity) {
        this.activity = activity;
        if (activity instanceof LifecycleOwner) {
            ((LifecycleOwner) activity).getLifecycle().addObserver(this);
        }
    }

    public static void loadAdaptiveBySpace(@NonNull Activity activity,
                                           @NonNull MaxHeightFrameLayout adContainer,
                                           @NonNull View adWrapperCard,
                                           int remainingHeightPx) {
        NativeAdHelper helper = new NativeAdHelper(activity);
        helper.loadBySpaceInternal(adContainer, adWrapperCard, remainingHeightPx);
    }

    public static void loadDynamicNativeOrBanner(@NonNull Activity activity,
                                                 @NonNull MaxHeightFrameLayout adContainer,
                                                 int remainingHeightPx) {
        NativeAdHelper helper = new NativeAdHelper(activity);
        helper.loadInto(adContainer, remainingHeightPx);
    }

    public static void loadAdaptiveNativeInFlow(@NonNull Activity activity,
                                                @NonNull MaxHeightFrameLayout adContainer) {
        NativeAdHelper helper = new NativeAdHelper(activity);
        helper.loadAdaptiveInFlowInternal(adContainer);
    }

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
            layoutRes = R.layout.native_ad_large;
        } else if (remainingDp >= 280) {
            layoutRes = R.layout.native_ad_medium;
        } else if (remainingDp >= 140) {
            layoutRes = R.layout.native_ad_small;
        } else {
            if (remainingDp < MIN_BANNER_DP) {
                adContainer.setVisibility(View.GONE);
                return;
            }
            loadBannerFallback(adContainer);
            return;
        }

        loadNativeWithLayouts(adContainer, new int[]{ layoutRes }, 0);
    }

    private void loadAdaptiveInFlowInternal(@NonNull MaxHeightFrameLayout adContainer) {
        // Clear max cap to allow natural wrap_content sizing inside Card
        adContainer.setMaxHeightPx(0);
        adContainer.setVisibility(View.VISIBLE);
        // Prefer Large on tall screens, Medium on moderate, Small on compact
        int screenDpH = pxToDp(activity.getResources().getDisplayMetrics().heightPixels);
        int[] order;
        if (screenDpH >= 700) {
            order = new int[]{ R.layout.native_ad_large, R.layout.native_ad_medium, R.layout.native_ad_small };
        } else if (screenDpH >= 560) {
            order = new int[]{ R.layout.native_ad_medium, R.layout.native_ad_large, R.layout.native_ad_small };
        } else {
            order = new int[]{ R.layout.native_ad_small, R.layout.native_ad_medium };
        }
        loadNativeWithLayouts(adContainer, order, 0);
    }

    private void loadNativeWithLayouts(@NonNull MaxHeightFrameLayout adContainer, @NonNull int[] layouts, int index) {
        if (index >= layouts.length) {
            loadBannerFallback(adContainer);
            return;
        }

        final int layoutRes = layouts[index];
        final View adView = LayoutInflater.from(activity).inflate(layoutRes, adContainer, false);

        AdLoader adLoader = new AdLoader.Builder(activity, activity.getString(R.string.native_ad_unit_id))
                .forNativeAd(nativeAd -> {
                    if (activity.isFinishing() || activity.isDestroyed()) {
                        nativeAd.destroy();
                        return;
                    }
                    destroyCurrent();
                    currentNative = nativeAd;
                    bindNative(adView, nativeAd);
                    adContainer.removeAllViews();
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    adContainer.addView(adView, lp);
                })
                .withAdListener(new AdListener() {
                    @Override public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        loadNativeWithLayouts(adContainer, layouts, index + 1);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build())
                        .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void loadBySpaceInternal(@NonNull MaxHeightFrameLayout adContainer,
                                     @NonNull View adWrapperCard,
                                     int remainingHeightPx) {
        if (remainingHeightPx <= 0) {
            adWrapperCard.setVisibility(View.GONE);
            return;
        }
        adWrapperCard.setVisibility(View.VISIBLE);
        adContainer.setVisibility(View.VISIBLE);
        // Allow natural wrap; rely on pre-measurement to avoid overflow
        adContainer.setMaxHeightPx(0);

        int remainingDp = pxToDp(remainingHeightPx);
        int[] candidates;
        if (remainingDp >= MIN_NATIVE_LARGE_DP) {
            candidates = new int[]{ R.layout.native_ad_large, R.layout.native_ad_medium, R.layout.native_ad_small };
        } else if (remainingDp >= MIN_NATIVE_MEDIUM_DP) {
            candidates = new int[]{ R.layout.native_ad_medium, R.layout.native_ad_small };
        } else if (remainingDp >= MIN_NATIVE_SMALL_DP) {
            candidates = new int[]{ R.layout.native_ad_small };
        } else {
            if (remainingDp >= MIN_BANNER_DP) {
                loadBannerWithWrapper(adContainer, adWrapperCard);
            } else {
                adWrapperCard.setVisibility(View.GONE);
            }
            return;
        }
        loadNativeWithLayoutsAndWrapper(adContainer, adWrapperCard, remainingHeightPx, candidates, 0);
    }

    private void loadNativeWithLayoutsAndWrapper(@NonNull MaxHeightFrameLayout adContainer,
                                                 @NonNull View adWrapperCard,
                                                 int remainingHeightPx,
                                                 @NonNull int[] layouts,
                                                 int index) {
        if (index >= layouts.length) {
            int remainingDp = pxToDp(remainingHeightPx);
            if (remainingDp < MIN_BANNER_DP) {
                adWrapperCard.setVisibility(View.GONE);
            } else {
                loadBannerWithWrapper(adContainer, adWrapperCard);
            }
            return;
        }

        final int layoutRes = layouts[index];
        final View adView = LayoutInflater.from(activity).inflate(layoutRes, adContainer, false);

        AdLoader adLoader = new AdLoader.Builder(activity, activity.getString(R.string.native_ad_unit_id))
                .forNativeAd(nativeAd -> {
                    if (activity.isFinishing() || activity.isDestroyed()) {
                        nativeAd.destroy();
                        return;
                    }
                    // Bind assets before measuring (view not yet attached)
                    bindNative(adView, nativeAd);

                    // Measure prospective height and compare with remaining viewport
                    int widthPx = adContainer.getWidth();
                    if (widthPx <= 0) widthPx = activity.getResources().getDisplayMetrics().widthPixels;
                    int wSpec = View.MeasureSpec.makeMeasureSpec(widthPx, View.MeasureSpec.EXACTLY);
                    int hSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    adView.measure(wSpec, hSpec);
                    int needed = adView.getMeasuredHeight();

                    if (needed > remainingHeightPx) {
                        // Too tall: destroy and try next size
                        try { nativeAd.destroy(); } catch (Throwable ignore) {}
                        loadNativeWithLayoutsAndWrapper(adContainer, adWrapperCard, remainingHeightPx, layouts, index + 1);
                        return;
                    }

                    // Safe to attach
                    destroyCurrent();
                    currentNative = nativeAd;
                    adContainer.removeAllViews();
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    adContainer.addView(adView, lp);
                })
                .withAdListener(new AdListener() {
                    @Override public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        loadNativeWithLayoutsAndWrapper(adContainer, adWrapperCard, remainingHeightPx, layouts, index + 1);
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
        if (currentBanner != null) {
            try { currentBanner.destroy(); } catch (Throwable ignore) {}
            currentBanner = null;
        }
        AdView banner = new AdView(activity);
        banner.setAdUnitId(activity.getString(R.string.banner_ad_unit_id));
        AdSize adSize = AdUtilsBannerSize.getAdSize(activity, adContainer);
        banner.setAdSize(adSize);
        banner.setAdListener(new AdListener() {
            @Override public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                adContainer.setVisibility(View.GONE);
            }
        });
        adContainer.addView(banner, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        currentBanner = banner;
        banner.loadAd(new AdRequest.Builder().build());
    }

    private void loadBannerWithWrapper(@NonNull MaxHeightFrameLayout adContainer,
                                       @NonNull View adWrapperCard) {
        adContainer.removeAllViews();
        if (currentBanner != null) { try { currentBanner.destroy(); } catch (Throwable ignore) {} currentBanner = null; }
        // Ensure banner can wrap content height
        ViewGroup.LayoutParams lpCap = adContainer.getLayoutParams();
        if (lpCap != null) { lpCap.height = ViewGroup.LayoutParams.WRAP_CONTENT; adContainer.setLayoutParams(lpCap); }
        AdView banner = new AdView(activity);
        banner.setAdUnitId(activity.getString(R.string.banner_ad_unit_id));
        AdSize adSize = AdUtilsBannerSize.getAdSize(activity, adContainer);
        banner.setAdSize(adSize);
        banner.setAdListener(new AdListener() {
            @Override public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                adWrapperCard.setVisibility(View.GONE);
            }
        });
        adContainer.addView(banner, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        adWrapperCard.setVisibility(View.VISIBLE);
        currentBanner = banner;
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
        try {
            if (currentBanner != null) {
                currentBanner.destroy();
                currentBanner = null;
            }
        } catch (Throwable ignore) {}
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        try { if (currentBanner != null) currentBanner.pause(); } catch (Throwable ignore) {}
        DefaultLifecycleObserver.super.onPause(owner);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        try { if (currentBanner != null) currentBanner.resume(); } catch (Throwable ignore) {}
        DefaultLifecycleObserver.super.onResume(owner);
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

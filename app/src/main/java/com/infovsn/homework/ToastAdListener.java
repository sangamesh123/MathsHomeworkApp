package com.infovsn.homework;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;

/**
 * Created by Sangamesh on 26-07-2017.
 */

public class ToastAdListener extends AdListener {
    private Context mContext;
    private String mErrorReason;

    public ToastAdListener(Context context)
    {
        this.mContext=context;
    }

    @Override
    public void onAdLoaded() {
        Toast.makeText(mContext,"onAdLoaded()",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdOpened() {
        Toast.makeText(mContext,"onAdOpened()",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClosed() {
        Toast.makeText(mContext,"onAdClosed()",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClicked() {
        Toast.makeText(mContext,"onAdClicked()",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdImpression() {
        Toast.makeText(mContext,"onAdImpression()",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdFailedToLoad(LoadAdError adError) {
        mErrorReason = adError.getMessage();
        Toast.makeText(mContext,"onAdFailedToLoad(): " + mErrorReason,Toast.LENGTH_SHORT).show();
    }

    public String getErrorReason()
    {
        return mErrorReason==null ? "" : mErrorReason;
    }
}

package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class EvenNumbers extends AppCompatActivity {
    private AdView mAdView;
    TextView mText;
    String meven="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_numbers);
        FontUtils.applyToActivity(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //ADDS BY GOOGLE
        mAdView=(AdView)findViewById(R.id.adView);
//        mAdView.setAdListener(new ToastAdListener(EvenNumbers.this));
        AdRequest adRequest =new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mText= (TextView) findViewById(R.id.even);
        for (int i = 0; i <= 1000; i++) {
            if (((i % 2) == 0)) {
                meven = meven + i + "  ";
            }
        }

        mText.setText(meven);
        mText.append("\n\n");


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

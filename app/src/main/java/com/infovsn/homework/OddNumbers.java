package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class OddNumbers extends AppCompatActivity {
    private AdView mAdView;
    TextView mText;
    String modd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odd_numbers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ADDS BY GOOGLE
        mAdView=(AdView)findViewById(R.id.adView);
//        mAdView.setAdListener(new ToastAdListener(OddNumbers.this));
        AdRequest adRequest =new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mText= (TextView) findViewById(R.id.odd);
        for (int i = 0; i <= 1000; i++) {
            if (((i % 2) == 1)) {
                modd = modd + i + "  ";
            }
        }

        mText.setText(modd);
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
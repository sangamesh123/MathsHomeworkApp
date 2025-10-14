package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class OddNumbersResult extends AppCompatActivity {
    private AdView mAdView;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odd_numbers_result);
        FontUtils.applyToActivity(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Ads by Google
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        if (mAdView != null) {
            mAdView.loadAd(adRequest);
        }

        mText = findViewById(R.id.oddResult);

        int startVal = getIntent().getIntExtra("start", 1);
        int endVal = getIntent().getIntExtra("end", 500);
        if (startVal > endVal) {
            int tmp = startVal; startVal = endVal; endVal = tmp;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Odd numbers from ").append(startVal).append(" to ").append(endVal).append(":\n\n");
        int first = (startVal % 2 != 0) ? startVal : (startVal + 1);
        for (int i = first; i <= endVal; i += 2) {
            sb.append(i).append("  ");
        }
        mText.setText(sb.toString());
        mText.append("\n\n");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


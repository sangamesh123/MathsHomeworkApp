package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class EvenNumbersResult extends AppCompatActivity {
    private AdView mAdView;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_numbers_result);
        FontUtils.applyToActivity(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Ads by Google
        mAdView = findViewById(R.id.adView);
        // Adaptive banner
        if (mAdView != null) {
            AdUtils.loadAdaptiveBanner(EvenNumbersResult.this, mAdView);
        }

        mText = findViewById(R.id.evenResult);

        int startVal = getIntent().getIntExtra("start", 1);
        int endVal = getIntent().getIntExtra("end", 500);
        if (startVal > endVal) {
            int tmp = startVal; startVal = endVal; endVal = tmp;
        }

        // Header (green) + list (black)
        String header = "Even numbers from " + startVal + " to " + endVal + ":";
        SpannableString headerSpan = new SpannableString(header);
        headerSpan.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, headerSpan.length(), 0);
        mText.setText(headerSpan);
        mText.append("\n\n");

        StringBuilder list = new StringBuilder();
        int first = (startVal % 2 == 0) ? startVal : (startVal + 1);
        for (int i = first; i <= endVal; i += 2) {
            list.append(i).append("  ");
        }
        mText.append(list.toString());
        mText.append("\n");
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

package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class PrimeNumbersResult extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_numbers_result);
        FontUtils.applyToActivity(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Ads by Google
        mAdView = findViewById(R.id.adView);
        // Adaptive banner
        if (mAdView != null) {
            AdUtils.loadAdaptiveBanner(PrimeNumbersResult.this, mAdView);
        }

        TextView resultView = findViewById(R.id.primeResult);
        int n = getIntent().getIntExtra("number", 5);

        String message;
        if (isPrime(n)) {
            message = getString(R.string.prime_is, n);
        } else {
            message = getString(R.string.prime_not, n);
        }
        SpannableString span = new SpannableString(message);
        span.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, span.length(), 0);
        resultView.setText(span);
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; (long)i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
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

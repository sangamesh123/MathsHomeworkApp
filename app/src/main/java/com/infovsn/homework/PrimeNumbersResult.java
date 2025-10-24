package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import java.math.BigInteger;

public class PrimeNumbersResult extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_numbers_result);
        FontUtils.applyToActivity(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView resultView = findViewById(R.id.primeResult);
        long n = getIntent().getLongExtra("number", 5L);

        String message;
        if (isPrime(n)) {
            message = getString(R.string.prime_is, n);
        } else {
            message = getString(R.string.prime_not, n);
        }
        SpannableString span = new SpannableString(message);
        span.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, span.length(), 0);
        resultView.setText(span);

        // Attach a dynamic native-or-banner ad loader anchored at the bottom, measuring remaining below the prime text
        // Using the inner TextView avoids the fillViewport behavior of the ScrollView that can make container height == viewport
        NativeAdHelper.attachToContainerOnLayout(this, R.id.primeResult, R.id.ad_container);
    }

    private boolean isPrime(long n) {
        if (n < 2) return false;
        // Leverage BigInteger's well-tested probable prime check with high certainty
        return BigInteger.valueOf(n).isProbablePrime(20);
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

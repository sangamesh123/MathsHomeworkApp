package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

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

        // After the layout is done, compute remaining space below content and load ad accordingly
        final View root = findViewById(android.R.id.content);
        final View content = findViewById(R.id.content_container);
        final MaxHeightFrameLayout adContainer = findViewById(R.id.ad_container);
        if (adContainer != null && root != null && content != null) {
            root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override public void onGlobalLayout() {
                    // Remove to avoid repeated calls
                    root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int rootH = root.getHeight();
                    int contentH = content.getHeight();
                    int remaining = Math.max(0, rootH - contentH);
                    // Load a native ad that fits remaining space, or banner if too small; hide if none
                    if (remaining <= 0) {
                        adContainer.setVisibility(View.GONE);
                    } else {
                        NativeAdHelper.loadDynamicNativeOrBanner(PrimeNumbersResult.this, adContainer, remaining);
                    }
                }
            });
        }
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

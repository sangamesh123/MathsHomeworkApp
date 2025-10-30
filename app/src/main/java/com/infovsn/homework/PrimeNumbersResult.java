package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import java.math.BigInteger;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

public class PrimeNumbersResult extends BaseActivity {
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

        // Measure remaining viewport space and load the largest ad that fits in-flow
        final ScrollView scroll = findViewById(R.id.scroll);
        final View contentCard = findViewById(R.id.content_card);
        final View adCard = findViewById(R.id.ad_card);
        final MaxHeightFrameLayout adContainer = findViewById(R.id.ad_container);
        if (scroll != null && contentCard != null && adCard != null && adContainer != null) {
            scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override public void onGlobalLayout() {
                    scroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int viewportH = scroll.getHeight();
                    int contentH = contentCard.getHeight();

                    int contentBottomMargin = 0;
                    int adTopBottomMargin = 0;
                    ViewGroup.LayoutParams cLp = contentCard.getLayoutParams();
                    if (cLp instanceof ViewGroup.MarginLayoutParams) {
                        contentBottomMargin = ((ViewGroup.MarginLayoutParams) cLp).bottomMargin;
                    }
                    ViewGroup.LayoutParams aLp = adCard.getLayoutParams();
                    if (aLp instanceof ViewGroup.MarginLayoutParams) {
                        adTopBottomMargin = ((ViewGroup.MarginLayoutParams) aLp).topMargin + ((ViewGroup.MarginLayoutParams) aLp).bottomMargin;
                    }

                    int remaining = viewportH - (contentH + contentBottomMargin) - adTopBottomMargin;
                    if (remaining <= 0) {
                        adCard.setVisibility(View.GONE);
                    } else {
                        NativeAdHelper.loadAdaptiveBySpace(PrimeNumbersResult.this, adContainer, adCard, remaining);
                    }
                }
            });
        }
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

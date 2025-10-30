package com.infovsn.homework;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import com.google.android.material.appbar.MaterialToolbar;
import android.view.MenuItem;

public class TableDisplayActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        // Setup MaterialToolbar for title and Up navigation
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.m1);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        FontUtils.applyToActivity(this);
        TextView at = findViewById(R.id.txtScr);
        at.setMovementMethod(new ScrollingMovementMethod());
        at.setTypeface(FontUtils.getRobotoMono(this));
        at.setLetterSpacing(0f);

        String tableNumberStr = getIntent().getStringExtra("tableNumber");
        if (tableNumberStr == null || tableNumberStr.isEmpty()) {
            at.setText(getString(R.string.msg_no_number_provided));
        } else {
            try {
                long myNum = Long.parseLong(tableNumberStr);
                if (myNum > 99999) {
                    at.setTextSize(26);
                }
                StringBuilder table = new StringBuilder();
                for (int i = 1; i <= 10; i++) {
                    if (i == 10) {
                        table.append("\n").append(myNum).append("  x 10  =  ").append(myNum * i);
                    } else if (i == 1) {
                        table.append(myNum).append("  x  ").append(i).append("  =  ").append(myNum * i);
                    } else {
                        table.append("\n").append(myNum).append("  x  ").append(i).append("  =  ").append(myNum * i);
                    }
                }
                at.setText(table.toString());
            } catch (NumberFormatException nfe) {
                at.setText(getString(R.string.msg_invalid_number));
            }
        }

        // After layout, measure visible viewport remainder and load the largest ad that fits
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
                        NativeAdHelper.loadAdaptiveBySpace(TableDisplayActivity.this, adContainer, adCard, remaining);
                    }
                }
            });
        }
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

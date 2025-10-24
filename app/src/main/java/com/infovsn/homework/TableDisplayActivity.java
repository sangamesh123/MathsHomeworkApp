package com.infovsn.homework;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;

public class TableDisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        FontUtils.applyToActivity(this);
        TextView at = findViewById(R.id.txtScr);
        at.setMovementMethod(new ScrollingMovementMethod());
        at.setTypeface(FontUtils.getRobotoMono(this));
        at.setLetterSpacing(0f);

        String tableNumberStr = getIntent().getStringExtra("tableNumber");
        if (tableNumberStr == null || tableNumberStr.isEmpty()) {
            at.setText("No number provided");
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
                at.setText("Invalid number");
            }
        }

        // Dynamic native ad or banner fallback at bottom without affecting content
        final View root = findViewById(android.R.id.content);
        final View mainContent = findViewById(R.id.txtScr);
        final MaxHeightFrameLayout adContainer = findViewById(R.id.ad_container);
        if (adContainer != null && root != null && mainContent != null) {
            root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override public void onGlobalLayout() {
                    root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int rootH = root.getHeight();
                    int contentH = mainContent.getHeight();
                    int remaining = Math.max(0, rootH - contentH);
                    if (remaining <= 0) {
                        adContainer.setVisibility(View.GONE);
                    } else {
                        NativeAdHelper.loadDynamicNativeOrBanner(TableDisplayActivity.this, adContainer, remaining);
                    }
                }
            });
        }
    }
}

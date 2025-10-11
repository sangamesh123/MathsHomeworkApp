package com.infovsn.homework;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class Lcm extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr; // numeric + clear
    TextView et;
    ImageButton bsp,badd,beq;
    TextView at;

    private static final int MAX_DIGITS = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcm);
        FontUtils.applyToActivity(this);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);

        // find views (no casts needed with API 26+ compile + androidx)
        b1 = findViewById(R.id.one);
        b2 = findViewById(R.id.two);
        b3 = findViewById(R.id.three);
        b4 = findViewById(R.id.four);
        b5 = findViewById(R.id.five);
        b6 = findViewById(R.id.six);
        b7 = findViewById(R.id.seven);
        b8 = findViewById(R.id.eight);
        b9 = findViewById(R.id.nine);
        b0 = findViewById(R.id.zero);
        badd = findViewById(R.id.add);
        bsp = findViewById(R.id.backspace);
        bclr = findViewById(R.id.clear);
        beq = findViewById(R.id.equal);
        et = findViewById(R.id.txtScreen);
        et.setMovementMethod(new ScrollingMovementMethod());

        // Digit listeners (explicit for readability)
        b1.setOnClickListener(v -> appendDigit("1"));
        b2.setOnClickListener(v -> appendDigit("2"));
        b3.setOnClickListener(v -> appendDigit("3"));
        b4.setOnClickListener(v -> appendDigit("4"));
        b5.setOnClickListener(v -> appendDigit("5"));
        b6.setOnClickListener(v -> appendDigit("6"));
        b7.setOnClickListener(v -> appendDigit("7"));
        b8.setOnClickListener(v -> appendDigit("8"));
        b9.setOnClickListener(v -> appendDigit("9"));
        b0.setOnClickListener(v -> appendDigit("0"));

        badd.setOnClickListener(v -> et.append("\n"));
        bclr.setOnClickListener(v -> et.setText(""));

        bsp.setOnClickListener(v -> {
            String smp = et.getText().toString();
            if (smp.isEmpty()) return;
            smp = smp.substring(0, smp.length()-1); // remove last char
            while (smp.endsWith("\n\n")) { // collapse extra trailing newlines
                smp = smp.substring(0, smp.length()-1);
            }
            et.setText(smp);
        });

        beq.setOnClickListener(v -> {
            // Capture input BEFORE switching layout
            String raw = et.getText().toString();

            setContentView(R.layout.added);
            at = findViewById(R.id.txtScr);
            at.setMovementMethod(new ScrollingMovementMethod());
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            String[] lines = raw.split("\n");
            List<Long> nums = new ArrayList<>();
            boolean error = false;
            StringBuilder echo = new StringBuilder();

            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue; // skip blanks
                if (trimmed.length() > MAX_DIGITS) {
                    at.setText(getString(R.string.error_max_digits, MAX_DIGITS));
                    at.append("\n");
                    error = true;
                    break;
                }
                try {
                    long value = Long.parseLong(trimmed);
                    nums.add(value);
                    echo.append(value).append('\n');
                } catch (NumberFormatException nfe) {
                    at.setText(getString(R.string.error_invalid_number, trimmed));
                    at.append("\n");
                    error = true;
                    break;
                }
            }

            if (!error) {
                if (nums.isEmpty()) {
                    at.setText(getString(R.string.error_enter_numbers_first));
                    at.append("\n\n");
                    return;
                }
                long result = lcmSafe(nums);
                at.setText(echo.toString());
                if (result == -1L) {
                    at.append(getString(R.string.error_overflow));
                    at.append("\n\n");
                    return;
                }
                SpannableString ss1 = new SpannableString("\n" + getString(R.string.label_lcm, String.valueOf(result)));
                ss1.setSpan(new RelativeSizeSpan(1.2f), 0, ss1.length(), 0);
                ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, ss1.length(), 0);
                at.append(ss1);
                at.append("\n\n");
            } else {
                at.append("\n");
            }
        });
    }

    private void appendDigit(String d) { et.append(d); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --- Math helpers ---
    private static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return Math.abs(a);
    }

    private static long lcmPair(long a, long b) {
        if (a == 0 || b == 0) return 0;
        long g = gcd(a, b);
        return Math.abs(a / g * b);
    }

    @SuppressWarnings("unused")
    private static long lcmArray(List<Long> nums) { // retained if future reuse needed
        long result = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            result = lcmPair(result, nums.get(i));
        }
        return result;
    }

    private static long lcmSafe(List<Long> nums) {
        long result = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            long a = result;
            long b = nums.get(i);
            if (a == 0 || b == 0) { result = 0; continue; }
            long g = gcd(a, b);
            long divided = a / g;
            if (divided != 0 && divided > Long.MAX_VALUE / Math.abs(b)) {
                return -1L; // overflow
            }
            result = Math.abs(divided * b);
        }
        return result;
    }
}

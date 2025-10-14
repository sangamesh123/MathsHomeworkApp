package com.infovsn.homework;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Hcf extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr;
    TextView et;
    ImageButton bsp,badd,beq;

    // Table UI (used after pressing equal)
    LcmTableView hcfTable;
    TextView headingTv, answerTv;

    private static final int MAX_DIGITS = 15;
    private static final int MAX_STEPS = 2000;
    // Track whether we're showing a result layout (error/simple result or division table)
    private boolean isShowingResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcf);
        FontUtils.applyToActivity(this);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);

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

        // Digits
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
            smp = smp.substring(0, smp.length()-1);
            while (smp.endsWith("\n\n")) {
                smp = smp.substring(0, smp.length()-1);
            }
            et.setText(smp);
        });

        beq.setOnClickListener(v -> onCompute());
    }

    private void appendDigit(String d) { et.append(d); }

    private void onCompute() {
        // Read input lines first
        String raw = et.getText().toString();
        String[] lines = raw.split("\n");
        List<Long> nums = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            if (trimmed.length() > MAX_DIGITS) {
                isShowingResult = true;
                setContentView(R.layout.added);
                FontUtils.applyToActivity(Hcf.this);
                TextView at = findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setText(getString(R.string.error_max_digits, MAX_DIGITS));
                at.append("\n\n");
                return;
            }
            // digits only
            if (!trimmed.matches("\\d+")) {
                isShowingResult = true;
                setContentView(R.layout.added);
                FontUtils.applyToActivity(Hcf.this);
                TextView at = findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setText(getString(R.string.error_invalid_number, trimmed));
                at.append("\n\n");
                return;
            }
            try {
                nums.add(Long.parseLong(trimmed));
            } catch (NumberFormatException nfe) {
                isShowingResult = true;
                setContentView(R.layout.added);
                FontUtils.applyToActivity(Hcf.this);
                TextView at = findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setText(getString(R.string.error_invalid_number, trimmed));
                at.append("\n\n");
                return;
            }
        }

        if (nums.isEmpty()) {
            isShowingResult = true;
            setContentView(R.layout.added);
            FontUtils.applyToActivity(Hcf.this);
            TextView at = findViewById(R.id.txtScr);
            at.setMovementMethod(new ScrollingMovementMethod());
            at.setText(getString(R.string.error_enter_numbers_first));
            at.append("\n\n");
            return;
        }

        // Handle zeros specially: gcd(0,0,...)=0; gcd(a,0,...) = gcd of non-zero values
        boolean allZero = true;
        for (Long n : nums) { if (n != 0L) { allZero = false; break; } }
        if (allZero) {
            isShowingResult = true;
            setContentView(R.layout.added);
            FontUtils.applyToActivity(Hcf.this);
            TextView at = findViewById(R.id.txtScr);
            at.setMovementMethod(new ScrollingMovementMethod());
            StringBuilder echo = new StringBuilder();
            for (Long n : nums) echo.append(n).append('\n');
            at.setText(echo.toString());
            SpannableString ss1 = new SpannableString("\n" + getString(R.string.label_hcf, "0"));
            ss1.setSpan(new RelativeSizeSpan(1.2f), 0, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, ss1.length(), 0);
            at.append(ss1);
            at.append("\n\n");
            return;
        }

        // If any 1 present, HCF is 1; if zeros present, ignore zeros for gcd
        List<Long> nonZero = new ArrayList<>();
        boolean anyOne = false;
        for (Long v : nums) {
            if (v == 0L) continue;
            if (v == 1L) anyOne = true;
            nonZero.add(v);
        }
        if (anyOne) {
            isShowingResult = true;
            setContentView(R.layout.added);
            FontUtils.applyToActivity(Hcf.this);
            TextView at = findViewById(R.id.txtScr);
            at.setMovementMethod(new ScrollingMovementMethod());
            StringBuilder echo = new StringBuilder();
            for (Long n : nums) echo.append(n).append('\n');
            at.setText(echo.toString());
            SpannableString ss1 = new SpannableString("\n" + getString(R.string.label_hcf, "1"));
            ss1.setSpan(new RelativeSizeSpan(1.2f), 0, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, ss1.length(), 0);
            at.append(ss1);
            at.append("\n\n");
            return;
        }

        // If there were zeros but no ones, show simple result using Euclid (table method is ambiguous with zeros)
        if (nonZero.size() != nums.size()) {
            long g = gcdArray(nonZero);
            isShowingResult = true;
            setContentView(R.layout.added);
            FontUtils.applyToActivity(Hcf.this);
            TextView at = findViewById(R.id.txtScr);
            at.setMovementMethod(new ScrollingMovementMethod());
            StringBuilder echo = new StringBuilder();
            for (Long n : nums) echo.append(n).append('\n');
            at.setText(echo.toString());
            SpannableString ss1 = new SpannableString("\n" + getString(R.string.label_hcf, String.valueOf(g)));
            ss1.setSpan(new RelativeSizeSpan(1.2f), 0, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, ss1.length(), 0);
            at.append(ss1);
            at.append("\n\n");
            return;
        }

        // All inputs are >=2 now; proceed with common prime division method
        isShowingResult = true;
        setContentView(R.layout.hcf_division);
        FontUtils.applyToActivity(Hcf.this);
        hcfTable = findViewById(R.id.hcfTable);
        headingTv = findViewById(R.id.txtHeading);
        answerTv = findViewById(R.id.txtAnswer);

        // Ads
        mAdView = findViewById(R.id.adView);
        if (mAdView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        headingTv.setText(getString(R.string.heading_hcf_of, humanJoin(nums)));

        List<Long> work = new ArrayList<>(nums);
        long hcf = 1L;
        long prime = 2L;
        int steps = 0;
        boolean overflow = false;
        List<Long> usedPrimes = new ArrayList<>();
        List<List<Long>> tableRows = new ArrayList<>();
        tableRows.add(new ArrayList<>(work));

        while (minValue(work) > 1L && steps < MAX_STEPS) {
            boolean dividedAtLeastOnce = false;
            while (allDivisibleBy(work, prime)) {
                for (int i = 0; i < work.size(); i++) {
                    work.set(i, work.get(i) / prime);
                }
                if (hcf != 0 && hcf > Long.MAX_VALUE / prime) { overflow = true; break; }
                hcf *= prime;
                usedPrimes.add(prime);
                tableRows.add(new ArrayList<>(work));
                steps++;
                dividedAtLeastOnce = true;
                if (steps >= MAX_STEPS) break;
            }
            if (overflow || steps >= MAX_STEPS) break;
            prime = nextPrime(prime);
            // Early exit: if next prime exceeds min(work), no more common factors
            if (!dividedAtLeastOnce && prime > minValue(work)) break;
        }

        hcfTable.setData(tableRows, usedPrimes);

        if (overflow) {
            answerTv.setText(getString(R.string.error_overflow));
        } else {
            String chain = buildChain(usedPrimes);
            if (chain.isEmpty()) {
                // co-prime inputs => HCF is 1, or no prime divisions performed
                answerTv.setText(getString(R.string.label_hcf, String.valueOf(hcf)));
            } else {
                String ans = getString(R.string.label_hcf_chain, chain, String.valueOf(hcf));
                int eqIdx = ans.lastIndexOf("=");
                if (eqIdx != -1 && eqIdx + 2 < ans.length()) {
                    SpannableString spanAns = new SpannableString(ans);
                    spanAns.setSpan(new StyleSpan(Typeface.BOLD), eqIdx + 2, ans.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    answerTv.setText(spanAns);
                } else {
                    answerTv.setText(ans);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isShowingResult) {
                isShowingResult = false;
                recreate();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isShowingResult) {
            isShowingResult = false;
            recreate();
        } else {
            super.onBackPressed();
        }
    }

    // --- Helpers ---
    private static long gcd(long a, long b) {
        while (b != 0) { long t = b; b = a % b; a = t; }
        return Math.abs(a);
    }
    private static long gcdArray(List<Long> arr) {
        long r = Math.abs(arr.get(0));
        for (int i = 1; i < arr.size(); i++) r = gcd(r, Math.abs(arr.get(i)));
        return r;
    }
    private static long minValue(List<Long> arr) {
        long m = Long.MAX_VALUE;
        for (Long v : arr) if (v < m) m = v;
        return m;
    }
    private static boolean allDivisibleBy(List<Long> arr, long p) {
        if (p <= 1) return false;
        for (Long v : arr) { if (v < 1 || v % p != 0) return false; }
        return true;
    }
    private static String humanJoin(List<Long> nums) {
        if (nums.isEmpty()) return "";
        if (nums.size() == 1) return String.valueOf(nums.get(0));
        if (nums.size() == 2) return String.format(Locale.getDefault(), "%d and %d", nums.get(0), nums.get(1));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.size(); i++) {
            if (i > 0) { if (i == nums.size() - 1) sb.append(" and "); else sb.append(", "); }
            sb.append(nums.get(i));
        }
        return sb.toString();
    }
    private static String buildChain(List<Long> primes) {
        if (primes.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        final String times = " × ";
        for (int i = 0; i < primes.size(); i++) {
            if (i > 0) sb.append(times);
            sb.append(primes.get(i));
        }
        return sb.toString();
    }
    private static long nextPrime(long current) {
        if (current < 2) return 2;
        long p = current + 1;
        if ((p & 1) == 0) p++;
        while (!isPrime(p)) p += 2;
        return p;
    }
    private static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        for (long d = 3; d * d <= n; d += 2) { if (n % d == 0) return false; }
        return true;
    }
}
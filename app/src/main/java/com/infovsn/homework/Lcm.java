package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Lcm extends AppCompatActivity {
    // Removed AdView; division screen now uses dynamic native helper with banner fallback
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr; // numeric + clear
    TextView et;
    ImageButton bsp,badd,beq;
    // Remove numbers/primes TextViews; use custom table view instead
    LcmTableView lcmTable;
    TextView headingTv, answerTv; // new heading and answer (green)

    private static final int MAX_DIGITS = 15;
    private static final int MAX_STEPS = 2000; // guard to avoid pathological loops
    // Track whether we're showing a result layout (error or division) so Back/Up returns to input
    private boolean isShowingResult = false;

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
            String[] lines = raw.split("\n");
            List<Long> nums = new ArrayList<>();

            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue; // skip blanks
                if (trimmed.length() > MAX_DIGITS) {
                    isShowingResult = true;
                    setContentView(R.layout.added);
                    FontUtils.applyToActivity(Lcm.this);
                    TextView at = findViewById(R.id.txtScr);
                    at.setMovementMethod(new ScrollingMovementMethod());
                    // Load adaptive in-flow ad
                    setupAdaptiveAdForAdded();
                    at.setText(getString(R.string.error_max_digits, MAX_DIGITS));
                    at.append("\n\n");
                    return;
                }
                try {
                    long value = Long.parseLong(trimmed);
                    nums.add(value);
                } catch (NumberFormatException nfe) {
                    isShowingResult = true;
                    setContentView(R.layout.added);
                    FontUtils.applyToActivity(Lcm.this);
                    TextView at = findViewById(R.id.txtScr);
                    at.setMovementMethod(new ScrollingMovementMethod());
                    // Load adaptive in-flow ad
                    setupAdaptiveAdForAdded();
                    at.setText(getString(R.string.error_invalid_number, trimmed));
                    at.append("\n\n");
                    return;
                }
            }

            if (nums.isEmpty()) {
                isShowingResult = true;
                setContentView(R.layout.added);
                FontUtils.applyToActivity(Lcm.this);
                TextView at = findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                // Load adaptive in-flow ad
                setupAdaptiveAdForAdded();
                at.setText(getString(R.string.error_enter_numbers_first));
                at.append("\n\n");
                return;
            }

            // If any input is 0, LCM is 0 (show echo + result)
            boolean anyZero = false;
            for (Long n : nums) { if (n == 0L) { anyZero = true; break; } }
            if (anyZero) {
                isShowingResult = true;
                setContentView(R.layout.added);
                FontUtils.applyToActivity(Lcm.this);
                TextView at = findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                // Load adaptive in-flow ad
                setupAdaptiveAdForAdded();
                StringBuilder echo = new StringBuilder();
                for (Long n : nums) echo.append(n).append('\n');
                at.setText(echo.toString());
                SpannableString ss1 = new SpannableString("\n" + getString(R.string.label_lcm, "0"));
                ss1.setSpan(new RelativeSizeSpan(1.2f), 0, ss1.length(), 0);
                ss1.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, ss1.length(), 0);
                at.append(ss1);
                at.append("\n\n");
                return;
            }

            // Now show the division-method layout
            isShowingResult = true;
            setContentView(R.layout.lcm_division);
            FontUtils.applyToActivity(Lcm.this);
            lcmTable = findViewById(R.id.lcmTable);
            headingTv = findViewById(R.id.txtHeading);
            answerTv = findViewById(R.id.txtAnswer);

            // Load adaptive ad below content in-flow on division layout
            setupAdaptiveAdForDivision();

            // Build heading like "LCM of 6 and 8" (or "LCM of 2, 3 and 5")
            headingTv.setText(getString(R.string.heading_lcm_of, humanJoin(nums)));

            List<Long> work = new ArrayList<>(nums);

            long lcm = 1L;
            long prime = 2L;
            int steps = 0;
            boolean overflow = false;
            List<Long> usedPrimes = new ArrayList<>();
            List<List<Long>> tableRows = new ArrayList<>();
            tableRows.add(new ArrayList<>(work)); // initial row

            while (!allOnes(work) && steps < MAX_STEPS) {
                boolean divisible = anyDivisible(work, prime);
                if (!divisible) {
                    prime = nextPrime(prime);
                    continue;
                }
                for (int i = 0; i < work.size(); i++) {
                    long val = work.get(i);
                    if (val % prime == 0) {
                        work.set(i, val / prime);
                    }
                }
                if (lcm != 0 && lcm > Long.MAX_VALUE / prime) {
                    overflow = true; break;
                }
                lcm *= prime;
                usedPrimes.add(prime);

                tableRows.add(new ArrayList<>(work)); // append step row
                steps++;
            }

            // Feed data to custom view
            lcmTable.setData(tableRows, usedPrimes);

            // Set bottom green sentence
            if (overflow) {
                answerTv.setText(getString(R.string.error_overflow));
            } else {
                String chain = buildChain(usedPrimes);
                String ans = getString(R.string.label_lcm_chain, chain, String.valueOf(lcm));
                int eqIdx = ans.lastIndexOf("=");
                if (eqIdx != -1 && eqIdx + 2 < ans.length()) {
                    SpannableString spanAns = new SpannableString(ans);
                    spanAns.setSpan(new StyleSpan(Typeface.BOLD), eqIdx + 2, ans.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    answerTv.setText(spanAns);
                } else {
                    answerTv.setText(ans);
                }
            }

            // Dynamic native ad or banner fallback replaced by adaptive in-flow above
        });
    }

    private void appendDigit(String d) { et.append(d); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isShowingResult) {
                isShowingResult = false;
                recreate(); // Return to input layout
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

    // --- Math helpers ---
    private static boolean allOnes(List<Long> arr) {
        for (Long v : arr) if (v != 1L) return false; return true;
    }
    private static boolean anyDivisible(List<Long> arr, long p) {
        for (Long v : arr) if (v % p == 0 && v > 1L) return true; return false;
    }
    private static String joinRow(List<Long> arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            if (i > 0) sb.append("    "); // spacing between columns
            sb.append(arr.get(i));
        }
        return sb.toString();
    }

    private static String humanJoin(List<Long> nums) {
        if (nums.isEmpty()) return "";
        if (nums.size() == 1) return String.valueOf(nums.get(0));
        if (nums.size() == 2) return String.format(Locale.getDefault(), "%d and %d", nums.get(0), nums.get(1));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.size(); i++) {
            if (i > 0) {
                if (i == nums.size() - 1) sb.append(" and "); else sb.append(", ");
            }
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
        for (long d = 3; d * d <= n; d += 2) {
            if (n % d == 0) return false;
        }
        return true;
    }

    // Retained efficient direct LCM if needed elsewhere
    private static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return Math.abs(a);
    }
    @SuppressWarnings("unused")
    private static long lcmPair(long a, long b) {
        if (a == 0 || b == 0) return 0;
        long g = gcd(a, b);
        return Math.abs(a / g * b);
    }
    @SuppressWarnings("unused")
    private static long lcmArray(List<Long> nums) {
        long result = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            result = lcmPair(result, nums.get(i));
        }
        return result;
    }

    private void setupAdaptiveAdForAdded() {
        final ScrollView scroll = findViewById(R.id.scroll);
        final View contentCard = findViewById(R.id.content_card);
        final View adCard = findViewById(R.id.ad_card);
        final MaxHeightFrameLayout adContainer = findViewById(R.id.ad_container);
        if (scroll == null || contentCard == null || adCard == null || adContainer == null) return;
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
                    NativeAdHelper.loadAdaptiveBySpace(Lcm.this, adContainer, adCard, remaining);
                }
            }
        });
    }

    private void setupAdaptiveAdForDivision() {
        final ScrollView scroll = findViewById(R.id.scroll);
        final View contentCard = findViewById(R.id.content_card);
        final View adCard = findViewById(R.id.ad_card);
        final MaxHeightFrameLayout adContainer = findViewById(R.id.ad_container);
        if (scroll == null || contentCard == null || adCard == null || adContainer == null) return;
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
                    NativeAdHelper.loadAdaptiveBySpace(Lcm.this, adContainer, adCard, remaining);
                }
            }
        });
    }
}

package com.infovsn.homework;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SquareRoot extends AppCompatActivity {
    int ex=0;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,back;
    ImageButton bsp, beq;
    TextView et;
    TextView at;
    private boolean isShowingResult = false;

    // Buffer for keypad input
    private final StringBuilder inputBuffer = new StringBuilder();
    private static final int MAX_DIGITS = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_root);
        FontUtils.applyToActivity(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        b1=(Button) findViewById(R.id.one);
        b2=(Button) findViewById(R.id.two);
        b3=(Button) findViewById(R.id.three);
        b4=(Button) findViewById(R.id.four);
        b5=(Button) findViewById(R.id.five);
        b6=(Button) findViewById(R.id.six);
        b7=(Button) findViewById(R.id.seven);
        b8=(Button) findViewById(R.id.eight);
        b9=(Button) findViewById(R.id.nine);
        b0=(Button) findViewById(R.id.zero);
        badd=(Button) findViewById(R.id.add);
        bsp=(ImageButton) findViewById(R.id.backspace);
        bclr=(Button) findViewById(R.id.clear);
        beq=(ImageButton) findViewById(R.id.equal);
        et=(TextView) findViewById(R.id.tabl);
        et.setMovementMethod(new ScrollingMovementMethod());

        // Digit buttons
        View.OnClickListener digitClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.one) {
                    appendDigit('1');
                } else if (id == R.id.two) {
                    appendDigit('2');
                } else if (id == R.id.three) {
                    appendDigit('3');
                } else if (id == R.id.four) {
                    appendDigit('4');
                } else if (id == R.id.five) {
                    appendDigit('5');
                } else if (id == R.id.six) {
                    appendDigit('6');
                } else if (id == R.id.seven) {
                    appendDigit('7');
                } else if (id == R.id.eight) {
                    appendDigit('8');
                } else if (id == R.id.nine) {
                    appendDigit('9');
                } else if (id == R.id.zero) {
                    appendDigit('0');
                }
            }
        };
        b1.setOnClickListener(digitClick);
        b2.setOnClickListener(digitClick);
        b3.setOnClickListener(digitClick);
        b4.setOnClickListener(digitClick);
        b5.setOnClickListener(digitClick);
        b6.setOnClickListener(digitClick);
        b7.setOnClickListener(digitClick);
        b8.setOnClickListener(digitClick);
        b9.setOnClickListener(digitClick);
        b0.setOnClickListener(digitClick);

        // Decimal point button
        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDot();
            }
        });

        // Clear button
        bclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputBuffer.setLength(0);
                updateEquationDisplay();
            }
        });

        // Backspace button
        bsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputBuffer.length() > 0) {
                    inputBuffer.deleteCharAt(inputBuffer.length() - 1);
                }
                updateEquationDisplay();
            }
        });

        // Equals/Result button
        beq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cc = inputBuffer.toString();
                setContentView(R.layout.added);
                isShowingResult = true;
                FontUtils.applyToActivity(SquareRoot.this);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                // Load adaptive native ad in-flow
                setupAdaptiveAdForAdded();
                if(cc.length()>0)
                    squareroot();
            }
        });

        updateEquationDisplay();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isShowingResult) { isShowingResult = false; recreate(); }
                else this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (isShowingResult) { isShowingResult = false; recreate(); }
        else super.onBackPressed();
    }

    // Keypad screen: show only √N
    private void updateEquationDisplay() {
        if (et == null) return;
        if (inputBuffer.length() == 0) {
            et.setText(null);
            return;
        }
        String input = inputBuffer.toString();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("\u221A"); // Unicode square root symbol
        builder.setSpan(new RelativeSizeSpan(1.4f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int numStart = builder.length();
        builder.append(input);
        builder.setSpan(new RelativeSizeSpan(1.2f), numStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        et.setText(builder);
    }

    private void appendDigit(char d) {
        if (countDigits(inputBuffer) >= MAX_DIGITS) {
            return;
        }
        inputBuffer.append(d);
        updateEquationDisplay();
    }

    private void appendDot() {
        if (indexOfDot(inputBuffer) >= 0) {
            return;
        }
        if (inputBuffer.length() == 0) {
            inputBuffer.append('0');
        }
        inputBuffer.append('.');
        updateEquationDisplay();
    }

    private static int countDigits(CharSequence s) {
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) c++;
        }
        return c;
    }

    private static int indexOfDot(CharSequence s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') return i;
        }
        return -1;
    }

    private static Double tryParseDouble(String s) {
        try {
            if (s.equals("-") || s.equals(".")) return null;
            return Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }

    private static String formatNumber(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(8, RoundingMode.HALF_UP).stripTrailingZeros();
        String out = bd.toPlainString();
        if (out.equals("-0")) out = "0";
        return out;
    }

    // Result screen: show only √N = result (styled)
    private void squareroot() {
        boolean temp=false;
        double num=99999999f;
        Double parsed = tryParseDouble(inputBuffer.toString());
        if (parsed == null) return;
        double myNum = parsed;
        if(myNum>num) temp=true;
        SpannableStringBuilder eq = new SpannableStringBuilder();
        eq.append("\u221A"); // Unicode square root symbol
        eq.setSpan(new RelativeSizeSpan(1.4f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int numStart = eq.length();
        eq.append(formatNumber(myNum));
        eq.setSpan(new RelativeSizeSpan(1.2f), numStart, eq.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        double rooted = Math.sqrt(myNum);
        String res = formatNumber(rooted);
        eq.append(" = ");
        int resStart = eq.length();
        eq.append(res);
        eq.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), resStart, eq.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        eq.setSpan(new RelativeSizeSpan(1.2f), resStart, eq.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        at.setText(eq);
        if(temp)
            at.setText("Maximum digits(8) exceeded\n");
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
                    NativeAdHelper.loadAdaptiveBySpace(SquareRoot.this, adContainer, adCard, remaining);
                }
            }
        });
    }
}
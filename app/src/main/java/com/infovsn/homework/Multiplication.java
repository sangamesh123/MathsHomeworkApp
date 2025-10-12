package com.infovsn.homework;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Spanned;
import android.text.style.ReplacementSpan;

public class Multiplication extends AppCompatActivity {
    private AdView mAdView;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, badd, bclr, back, bdot; // add dot
    TextView et;
    ImageButton bsp, beq;
    TextView at;
    long val1 = 0, val2 = 0;
    boolean add;

    // helpers
    private static final char NBSP = '\u00A0';

    private static String digitsOnly(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') sb.append(c);
        }
        return sb.toString();
    }

    private static int[] toDigitsLeftToRight(String s) {
        int[] d = new int[s.length()];
        for (int i = 0; i < s.length(); i++) d[i] = s.charAt(i) - '0';
        return d;
    }

    // Carry markers for multiplicand * single digit (0-9). markers placed above the next-left column
    private static char[] computeCarryRowForSingleDigit(int[] multiplicand, int digit) {
        int L = multiplicand.length;
        char[] mark = new char[L];
        for (int i = 0; i < L; i++) mark[i] = NBSP;
        if (digit == 0) return mark;
        int carry = 0;
        for (int k = L - 1; k >= 0; k--) {
            int prod = multiplicand[k] * digit + carry;
            carry = prod / 10;
            if (carry > 0 && k - 1 >= 0) {
                int v = carry % 10; // fit a single digit carry in the cell
                mark[k - 1] = (char) ('0' + v);
            }
        }
        return mark;
    }

    // Carry markers for column-wise addition of multiple numeric strings (right-aligned, digits only strings with optional right-shift zeros already applied)
    private static char[] computeAdditionCarryMarkers(List<String> nums) {
        int maxLen = 0;
        for (String s : nums) if (s.length() > maxLen) maxLen = s.length();
        char[] mark = new char[maxLen];
        for (int i = 0; i < maxLen; i++) mark[i] = NBSP;
        int carry = 0;
        for (int idx = maxLen - 1; idx >= 0; idx--) {
            int sum = carry;
            for (String s : nums) {
                int j = s.length() - 1 - (maxLen - 1 - idx);
                if (j >= 0 && j < s.length()) sum += s.charAt(j) - '0';
            }
            carry = sum / 10;
            if (carry > 0 && idx - 1 >= 0) {
                int v = carry % 10;
                mark[idx - 1] = (char) ('0' + v);
            }
        }
        return mark;
    }

    private static String insertDotPlaceholder(char[] markers, int dotIndex) {
        if (dotIndex < 0) return new String(markers);
        char[] out = new char[markers.length + 1];
        System.arraycopy(markers, 0, out, 0, dotIndex);
        out[dotIndex] = NBSP; // placeholder for dot
        System.arraycopy(markers, dotIndex, out, dotIndex + 1, markers.length - dotIndex);
        return new String(out);
    }

    private static String padRightZeros(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < n; i++) sb.append('0');
        return sb.toString();
    }

    private static String ensureMinLengthLeft(String s, int minLen, char pad) {
        if (s.length() >= minLen) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < minLen - s.length(); i++) sb.append(pad);
        sb.append(s);
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);
        FontUtils.applyToActivity(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        b1 = (Button) findViewById(R.id.one);
        b2 = (Button) findViewById(R.id.two);
        b3 = (Button) findViewById(R.id.three);
        b4 = (Button) findViewById(R.id.four);
        b5 = (Button) findViewById(R.id.five);
        b6 = (Button) findViewById(R.id.six);
        b7 = (Button) findViewById(R.id.seven);
        b8 = (Button) findViewById(R.id.eight);
        b9 = (Button) findViewById(R.id.nine);
        b0 = (Button) findViewById(R.id.zero);
        badd = (Button) findViewById(R.id.add);
        bdot = (Button) findViewById(R.id.dot);
        bsp = (ImageButton) findViewById(R.id.backspace);
        bclr = (Button) findViewById(R.id.clear);
        beq = (ImageButton) findViewById(R.id.equal);
        et = (TextView) findViewById(R.id.txtScreen);
        et.setMovementMethod(new ScrollingMovementMethod());
        et.setTypeface(FontUtils.getRobotoMono(Multiplication.this));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "1");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "2");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "3");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "4");
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "5");
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "6");
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "7");
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "8");
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "9");
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText() + "0");
            }
        });


        badd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mm = et.getText().toString();
                if (!mm.contains("x")) {
                    et.setText(et.getText() + "\n" + "x ");
                }
            }
        });

        bclr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                et.setText(null);
            }
        });

        bsp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String smp = et.getText().toString();
                if (smp.length() > 1) {
                    char qq2 = smp.charAt(smp.length() - 1);
                    if (qq2 == ' ') {
                        smp = smp.substring(0, smp.length() - 2);
                    } else
                        smp = smp.substring(0, smp.length() - 1);
                    et.setText(smp);
                    if (!smp.isEmpty()) {
                        char qq = smp.charAt(smp.length() - 1);
                        if (qq == '\n') {
                            smp = smp.substring(0, smp.length() - 1);
                            et.setText(smp);
                        }
                    }
                } else if (smp.length() <= 1) {
                    et.setText(null);
                }

            }
        });

        // Dot button behavior (allow at most one '.' per current line)
        if (bdot != null) {
            bdot.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    String full = et.getText().toString();
                    int ln = full.lastIndexOf('\n');
                    String currentLine = (ln==-1)? full : full.substring(ln+1);
                    if(currentLine.startsWith("x ")) currentLine = currentLine.substring(2);
                    if(currentLine.contains(".")) return;
                    if(full.endsWith("\nx ") || full.endsWith("x ") || currentLine.isEmpty()){
                        et.append("0.");
                    } else {
                        et.append(".");
                    }
                }
            });
        }

        beq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String raw = et.getText()+"";
                if (!raw.contains("x")) {
                    raw = raw + "\n" + "x ";
                }

                setContentView(R.layout.added);
                FontUtils.applyToActivity(Multiplication.this);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setTypeface(FontUtils.getRobotoMono(Multiplication.this));
                mAdView = (AdView) findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                if (mAdView != null) {
                    mAdView.loadAd(adRequest);
                }

                String[] lines = raw.split("\n");
                String a = lines.length>0 ? lines[0] : "";
                String b = lines.length>1 ? (lines[1].startsWith("x ")? lines[1].substring(2): lines[1]) : "";
                if (a.isEmpty()) a = "1";
                if (b.isEmpty()) b = "1";

                if (!a.matches("\\d+(\\.\\d+)?") || !b.matches("\\d+(\\.\\d+)?")) {
                    at.setText("Invalid number format\n");
                    return;
                }

                if (digitsOnly(a).length() > 16 || digitsOnly(b).length() > 16) {
                    at.setText("Maximum digits(16) exceeded\n");
                    return;
                }

                // split int/frac (keep original fractional lengths)
                int ad = a.indexOf('.');
                int bd = b.indexOf('.');
                String ai = (ad>=0? a.substring(0, ad) : a);
                String af = (ad>=0? a.substring(ad+1) : "");
                String bi = (bd>=0? b.substring(0, bd) : b);
                String bf = (bd>=0? b.substring(bd+1) : "");

                // Display exactly as typed (no zero-padding for B)
                at.setText(a + "\n" + "x " + b);

                // Arithmetic scaled integers use original fraction lengths only
                String aScaled = ai + af;
                String bScaled = bi + bf;
                if (aScaled.isEmpty()) aScaled = "0";
                if (bScaled.isEmpty()) bScaled = "0";

                BigInteger A = new BigInteger(aScaled);
                BigInteger B = new BigInteger(bScaled);
                int aIntLen = ai.length();
                int resultFrac = af.length() + bf.length();

                // Pre-compute final product string and expected column width (including dot if any)
                BigInteger prod = A.multiply(B);
                String rsExpected = prod.toString();
                if (resultFrac > 0) {
                    while (rsExpected.length() <= resultFrac) rsExpected = "0" + rsExpected;
                    int splitExp = rsExpected.length() - resultFrac;
                    rsExpected = rsExpected.substring(0, splitExp) + "." + rsExpected.substring(splitExp);
                }
                int expectedLenForResult = rsExpected.length();

                // First multiplication carry row: from LSD of B
                int[] aDigits = toDigitsLeftToRight(aScaled);
                int bLen = bScaled.length();
                int lsdDigit = (bLen>0)? (bScaled.charAt(bLen-1) - '0') : 0;
                char[] mulCarry = computeCarryRowForSingleDigit(aDigits, lsdDigit);
                // Ignore fraction special spacing here (no dot placeholder)
                String mulCarryRow = new String(mulCarry);
                // Do not append yet; we will decide after building partials

                // Build partial products (include zeros except trailing fractional zeros)
                List<String> partials = new ArrayList<>();
                List<String> displayPartials = new ArrayList<>();
                int fracLenB = bf.length();
                int trailingFracZeros = 0;
                for (int i = bf.length() - 1; i >= 0; i--) {
                    if (bf.charAt(i) == '0') trailingFracZeros++; else break;
                }
                if (bLen > 0) {
                    for (int idx = bLen - 1, shift = 0; idx >= 0; idx--, shift++) {
                        int d = bScaled.charAt(idx) - '0';
                        boolean inFraction = (fracLenB > 0 && shift < fracLenB);
                        boolean isTrailingFracZero = inFraction && (shift < trailingFracZeros) && d == 0;
                        if (isTrailingFracZero) continue;
                        BigInteger p = A.multiply(BigInteger.valueOf(Math.max(0, d)));
                        String ps = p.toString();
                        String shifted = padRightZeros(ps, shift);
                        partials.add(shifted);
                        StringBuilder vv = new StringBuilder(ps);
                        for (int s = 0; s < shift; s++) vv.append('+');
                        displayPartials.add(vv.toString());
                    }
                }

                // Show carry row above result for all cases
                at.append("\n");
                SpannableString mcr = new SpannableString(mulCarryRow);
                mcr.setSpan(new UnderlineSpan(), 0, mcr.length(), 0);
                mcr.setSpan(new ForegroundColorSpan(Color.RED), 0, mcr.length(), 0);
                at.append(mcr);

                // Show partials; if only one non-zero line, keep it hidden to avoid duplicate result
                if (displayPartials.size() > 1) {
                    for (String dp : displayPartials) {
                        at.append("\n");
                        at.append(dp);
                    }
                    // Addition carry row across partials: only show for multi-partial
                    final char NBSP = '\u00A0';
                    char[] carr = new char[expectedLenForResult];
                    for (int i=0;i<expectedLenForResult;i++) carr[i] = NBSP;
                    // Exact column carries
                    int L = 0;
                    for (String s : partials) if (s.length() > L) L = s.length();
                    int[] carryOut = new int[Math.max(L,1)];
                    int carryAdd = 0;
                    for (int col = 0; col < L; col++) {
                        int columnSum = carryAdd;
                        for (String s : partials) {
                            int id = s.length() - 1 - col;
                            if (id >= 0) columnSum += (s.charAt(id) - '0');
                        }
                        carryAdd = columnSum / 10;
                        carryOut[col] = carryAdd;
                    }
                    // Place carries above next-left column; do not special-case decimal column
                    for (int c = 0; c < L; c++) {
                        int cv = carryOut[c] % 10;
                        if (cv == 0) continue;
                        int pos = expectedLenForResult - 1 - (c + 1);
                        if (pos >= 0 && pos < expectedLenForResult) carr[pos] = (char)('0' + cv);
                    }
                    // Print the carry row (only for multi-partial)
                    at.append("\n");
                    SpannableString acr = new SpannableString(new String(carr));
                    acr.setSpan(new UnderlineSpan(), 0, acr.length(), 0);
                    acr.setSpan(new ForegroundColorSpan(Color.RED), 0, acr.length(), 0);
                    at.append(acr);
                }

                // Final result with decimal point (no left-padding; apply digit+dot compression; replace trailing frac 0 with NBSP)
                String rs = prod.toString();
                if (resultFrac > 0) {
                    while (rs.length() <= resultFrac) rs = "0" + rs;
                    int split = rs.length() - resultFrac;
                    rs = rs.substring(0, split) + "." + rs.substring(split);
                }
                // Build spannable and compress digit+dot as one cell
                SpannableString blue;
                int dp = rs.indexOf('.')
                        ;
                if (dp > 0 && Character.isDigit(rs.charAt(dp-1))) {
                    // Optionally replace trailing fractional 0 with NBSP so "2." shows when needed
                    if (dp < rs.length()-1 && rs.charAt(rs.length()-1) == '0') {
                        char[] rc = rs.toCharArray();
                        rc[rc.length - 1] = NBSP;
                        rs = new String(rc);
                    }
                    blue = new SpannableString(rs);
                    blue.setSpan(new CombinedDigitDotSpan(), dp - 1, dp + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    blue = new SpannableString(rs);
                }
                at.append("\n");
                blue.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, blue.length(), 0);
                at.append(blue);
                at.append("\n\n");
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Draw digit and following '.' as a single cell width (for final result only)
    static class CombinedDigitDotSpan extends ReplacementSpan {
        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            if (fm != null) {
                Paint.FontMetricsInt pfm = paint.getFontMetricsInt();
                fm.ascent = pfm.ascent;
                fm.descent = pfm.descent;
                fm.top = pfm.top;
                fm.bottom = pfm.bottom;
            }
            float w = paint.measureText("0"); // approximate monospace cell
            return (int) Math.ceil(w);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int oldColor = paint.getColor();
            float cell = paint.measureText("0");
            // Draw digit in BLUE within this cell
            paint.setColor(Colors.LCM_GREEN);
            char d = text.charAt(start);
            canvas.drawText(new char[]{d}, 0, 1, x, y, paint);
            // Draw the dot in RED centered at the boundary between this cell and the next
            float dotW = paint.measureText(".");
            float dotX = x + cell - (dotW / 2f);
            paint.setColor(Color.RED);
            canvas.drawText(".", dotX, y, paint);
            // Restore original paint color
            paint.setColor(oldColor);
        }
    }
}

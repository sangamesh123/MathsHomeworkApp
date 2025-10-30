package com.infovsn.homework;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class Addition extends BaseActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,bdot;
    ImageButton bsp, beq;
    TextView et;
    TextView at;
    private boolean isShowingResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        FontUtils.applyToActivity(this);

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
        bdot = findViewById(R.id.dot);
        bsp = findViewById(R.id.backspace);
        bclr = findViewById(R.id.clear);
        beq = findViewById(R.id.equal);
        et = findViewById(R.id.txtScreen);
        et.setMovementMethod(new ScrollingMovementMethod());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"1"); }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"2"); }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"3"); }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"4"); }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"5"); }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"6"); }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"7"); }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"8"); }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"9"); }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"0"); }
        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(et.getText()+"\n+ "); }
        });

        bclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et.setText(null); }
        });

        bsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smp = et.getText().toString();
                if (smp.length() > 1) {
                    char qq2=smp.charAt(smp.length()-1);
                    if(qq2==' ') { smp = smp.substring(0, smp.length() - 2); }
                    else smp = smp.substring(0, smp.length() - 1);
                    et.setText(smp);
                    char qq=smp.charAt(smp.length()-1);
                    if(qq=='\n') { smp = smp.substring(0, smp.length() - 1); et.setText(smp); }
                } else if (smp.length() <= 1) {
                    et.setText(null);
                }
            }
        });

        bdot.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String full = et.getText().toString();
                int ln = full.lastIndexOf('\n');
                String currentLine = (ln==-1)? full : full.substring(ln+1);
                if(currentLine.startsWith("+ ")) currentLine = currentLine.substring(2);
                if(currentLine.contains(".")) return;
                if(full.endsWith("\n+ ") || full.endsWith("+ ") || currentLine.length()==0){
                    et.append("0.");
                } else {
                    et.append(".");
                }
            }
        });

        beq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String raw = et.getText()+"";
                if(raw.trim().isEmpty()) return;
                boolean hasDecimal = raw.contains(".");
                if(!hasDecimal) {
                    setContentView(R.layout.added);
                    isShowingResult = true;
                    FontUtils.applyToActivity(Addition.this);
                    at= findViewById(R.id.txtScr);
                    at.setMovementMethod(new ScrollingMovementMethod());
                    at.setTypeface(FontUtils.getRobotoMono(Addition.this));
                    setupAdaptiveAdForAdded();

                    String[] lines = raw.split("\n");
                    java.util.List<String> nums = new java.util.ArrayList<>();
                    int L = 0;
                    for (int i=0;i<lines.length;i++) {
                        String s = (i==0) ? lines[i] : (lines[i].length()>=2 ? lines[i].substring(2) : "");
                        if (s.isEmpty()) s = "0";
                        if (!s.matches("\\d+")) { at.setText("Invalid number format\n"); return; }
                        if (s.length() > 15) { at.setText("Maximum digits(15) exceeded\n"); return; }
                        if (s.length() > L) L = s.length();
                        nums.add(s);
                    }

                    StringBuilder expr = new StringBuilder();
                    expr.append(nums.get(0));
                    for (int i=1;i<nums.size();i++) expr.append("\n+ ").append(nums.get(i));
                    at.setText(expr.toString());

                    int[] carryOut = new int[Math.max(L,1)];
                    StringBuilder resRev = new StringBuilder();
                    int carry = 0;
                    for (int col = 0; col < L; col++) {
                        int columnSum = carry;
                        for (String s : nums) {
                            int idx = s.length() - 1 - col;
                            if (idx >= 0) columnSum += (s.charAt(idx) - '0');
                        }
                        int digit = columnSum % 10;
                        carry = columnSum / 10;
                        carryOut[col] = carry;
                        resRev.append((char)('0' + digit));
                    }
                    while (carry > 0) {
                        resRev.append((char)('0' + (carry % 10)));
                        carry /= 10;
                    }
                    String result = resRev.reverse().toString();

                    final char NBSP = '\u00A0';
                    StringBuilder carryFull = new StringBuilder();
                    int finalCarry = (L > 0) ? carryOut[L-1] : 0;
                    if (finalCarry > 0) carryFull.append((char)('0' + finalCarry));
                    for (int col = L - 1; col >= 0; col--) {
                        if (col == 0) {
                            carryFull.append(NBSP);
                        } else {
                            int c = carryOut[col - 1];
                            carryFull.append(c == 0 ? NBSP : (char)('0' + c));
                        }
                    }
                    String carryStr = carryFull.toString();
                    boolean hasCarry = finalCarry > 0;
                    for (int col = 0; col < L-1 && !hasCarry; col++) if (carryOut[col] > 0) hasCarry = true;

                    if (hasCarry) {
                        SpannableString ss2 = new SpannableString(carryStr);
                        ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                        ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                        at.append("\n");
                        at.append(ss2);
                    } else {
                        SpannableString ss2 = new SpannableString(carryStr);
                        ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                        ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                        at.append("\n");
                        at.append(ss2);
                    }

                    at.append("\n");
                    SpannableString ss1 = new SpannableString(result);
                    ss1.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, ss1.length(), 0);
                    at.append(ss1);
                    at.append("\n");
                    return;
                }

                setContentView(R.layout.added);
                isShowingResult = true;
                FontUtils.applyToActivity(Addition.this);
                at= findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setTypeface(FontUtils.getRobotoMono(Addition.this));
                setupAdaptiveAdForAdded();

                String[] rawLines = raw.split("\n");
                java.util.List<String> original = new java.util.ArrayList<>();
                java.util.List<String> partsInt = new java.util.ArrayList<>();
                java.util.List<String> partsFrac = new java.util.ArrayList<>();
                int maxFrac = 0;
                int maxIntLen = 0;
                for (int i=0; i<rawLines.length; i++) {
                    String line = rawLines[i];
                    String num = (i==0) ? line : (line.length()>=2 ? line.substring(2) : "");
                    if (num.isEmpty()) num = "0";
                    if (!num.matches("\\d+(\\.\\d+)?")) { at.setText("Invalid number format\n"); return; }
                    original.add(num);
                    int dot = num.indexOf('.');
                    if (dot >= 0) {
                        String ip = num.substring(0, dot);
                        String f = num.substring(dot+1);
                        partsInt.add(ip);
                        partsFrac.add(f);
                        if (f.length() > maxFrac) maxFrac = f.length();
                        if (ip.length() > maxIntLen) maxIntLen = ip.length();
                    } else {
                        partsInt.add(num);
                        partsFrac.add("");
                        if (num.length() > maxIntLen) maxIntLen = num.length();
                    }
                }

                StringBuilder expr2 = new StringBuilder();
                for (int i=0;i<original.size();i++) {
                    String di = partsInt.get(i);
                    StringBuilder df = new StringBuilder(partsFrac.get(i));
                    while (df.length() < maxFrac) df.append('0');
                    String line = (maxFrac > 0) ? (di + "." + df) : di;
                    if (i == 0) expr2.append(line);
                    else expr2.append("\n+ ").append(line);
                }
                at.setText(expr2.toString());

                int L = maxIntLen + maxFrac;
                java.util.List<String> scaled = new java.util.ArrayList<>();
                for (int i=0; i<original.size(); i++) {
                    StringBuilder frac = new StringBuilder(partsFrac.get(i));
                    while (frac.length() < maxFrac) frac.append('0');
                    String sNoPad = partsInt.get(i) + frac;
                    int pad = L - sNoPad.length();
                    StringBuilder sb = new StringBuilder();
                    for (int p=0; p<pad; p++) sb.append('0');
                    sb.append(sNoPad);
                    String s = sb.toString();
                    if (s.length() > 15) { at.setText("Maximum digits(15) exceeded\n"); return; }
                    scaled.add(s);
                }

                int[] carryOut = new int[Math.max(L,1)];
                StringBuilder resRev = new StringBuilder();
                int carry = 0;
                for (int col=0; col<L; col++) {
                    int columnSum = carry;
                    for (String s : scaled) {
                        int idx = s.length() - 1 - col;
                        if (idx >= 0) columnSum += (s.charAt(idx) - '0');
                    }
                    int digit = columnSum % 10;
                    carry = columnSum / 10;
                    carryOut[col] = carry;
                    resRev.append((char)('0' + digit));
                }
                while (carry > 0) {
                    resRev.append((char)('0' + (carry % 10)));
                    carry /= 10;
                }
                String resultScaled = resRev.reverse().toString();

                String resultOut;
                if (maxFrac > 0) {
                    while (resultScaled.length() <= maxFrac) resultScaled = "0" + resultScaled;
                    int split = resultScaled.length() - maxFrac;
                    String intPart = resultScaled.substring(0, split);
                    String fracPart = resultScaled.substring(split);
                    if (intPart.isEmpty()) intPart = "0";
                    if (fracPart.length() < maxFrac) {
                        StringBuilder pad = new StringBuilder(fracPart);
                        while (pad.length() < maxFrac) pad.append('0');
                        fracPart = pad.toString();
                    }
                    resultOut = intPart + "." + fracPart;
                } else {
                    resultOut = resultScaled;
                }

                final char NBSP = '\u00A0';
                StringBuilder carryFull2 = new StringBuilder();
                int finalCarry2 = (L > 0) ? carryOut[L-1] : 0;
                if (finalCarry2 > 0) carryFull2.append((char)('0' + finalCarry2));
                for (int col = L - 1; col >= 0; col--) {
                    if (col == 0) {
                        carryFull2.append(NBSP);
                    } else {
                        int c = carryOut[col - 1];
                        carryFull2.append(c == 0 ? NBSP : (char)('0' + c));
                    }
                }
                String carryStr2DigitsOnly = carryFull2.toString();
                if (maxFrac > 0) {
                    int prefix = (finalCarry2 > 0) ? 1 : 0;
                    int splitPos = prefix + (L - maxFrac);
                    String left = carryStr2DigitsOnly.substring(0, splitPos);
                    String right = carryStr2DigitsOnly.substring(splitPos);
                    carryStr2DigitsOnly = left + NBSP + right;
                }
                String carryStr2 = carryStr2DigitsOnly;
                boolean hasCarry2 = finalCarry2 > 0;
                for (int col = 0; col < L-1 && !hasCarry2; col++) if (carryOut[col] > 0) hasCarry2 = true;

                if (hasCarry2) {
                    SpannableString ss2 = new SpannableString(carryStr2);
                    ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                    ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                    at.append("\n");
                    at.append(ss2);
                } else {
                    SpannableString ss2 = new SpannableString(carryStr2);
                    ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                    ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                    at.append("\n");
                    at.append(ss2);
                }

                at.append("\n");
                SpannableString ss1 = new SpannableString(resultOut);
                ss1.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, ss1.length(), 0);
                at.append(ss1);
                at.append("\n");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isShowingResult) { isShowingResult = false; recreate(); }
            else this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isShowingResult) { isShowingResult = false; recreate(); }
        else super.onBackPressed();
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
                    NativeAdHelper.loadAdaptiveBySpace(Addition.this, adContainer, adCard, remaining);
                }
            }
        });
    }
}

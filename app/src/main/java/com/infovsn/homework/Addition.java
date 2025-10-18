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

public class Addition extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,back,bdot; // added bdot
    ImageButton bsp, beq;
    TextView et;
    TextView at;
    long val1=0,val2=0;
    boolean add;
    // Track whether we're showing a result layout (so back/Up returns to input instead of exiting)
    private boolean isShowingResult = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
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
        bdot=(Button) findViewById(R.id.dot); // new decimal button
        bsp=(ImageButton) findViewById(R.id.backspace);
        bclr=(Button) findViewById(R.id.clear);
        beq=(ImageButton) findViewById(R.id.equal);
        et=(TextView) findViewById(R.id.txtScreen);
        et.setMovementMethod(new ScrollingMovementMethod());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"1");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"2");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"3");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"4");
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"5");
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"6");
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"7");
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"8");
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"9");
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText()+"0");
            }
        });


        badd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                val1=Integer.parseInt(et.getText()+"");
//                add=true;
                et.setText(et.getText()+"\n"+"+ ");
            }
        });

        bclr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                val1=Integer.parseInt(et.getText()+"");
//                add=true;
                et.setText(null);
            }
        });

        bsp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                val1=Integer.parseInt(et.getText()+"");
//                add=true;
                String smp = et.getText().toString();
                if (smp.length() > 1) {
                    char qq2=smp.charAt(smp.length()-1);
                    if(qq2==' ')
                    {
                        smp = smp.substring(0, smp.length() - 2);
                    }
                    else
                        smp = smp.substring(0, smp.length() - 1);
                    et.setText(smp);
                    char qq=smp.charAt(smp.length()-1);
                    if(qq=='\n')
                    {
                        smp = smp.substring(0, smp.length() - 1);
                        et.setText(smp);
                    }
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
                // Remove "+ " prefix from current line for decimal placement check
                if(currentLine.startsWith("+ ")) currentLine = currentLine.substring(2);
                if(currentLine.contains(".")) return; // already has a decimal
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
                    // Show result layout
                    setContentView(R.layout.added);
                    isShowingResult = true;
                    FontUtils.applyToActivity(Addition.this);
                    at=(TextView) findViewById(R.id.txtScr);
                    at.setMovementMethod(new ScrollingMovementMethod());
                    at.setTypeface(FontUtils.getRobotoMono(Addition.this));
                    mAdView=(AdView)findViewById(R.id.adView);
                    // Adaptive banner
                    if (mAdView != null) {
                        AdUtils.loadAdaptiveBanner(Addition.this, mAdView);
                    }

                    // Parse numbers (integers only)
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

                    // Show the expression as entered
                    StringBuilder expr = new StringBuilder();
                    expr.append(nums.get(0));
                    for (int i=1;i<nums.size();i++) expr.append("\n+ ").append(nums.get(i));
                    at.setText(expr.toString());

                    // Column-wise addition to compute carries and result (manual style)
                    int[] carryOut = new int[Math.max(L,1)]; // carry sent to the next higher column for each column
                    StringBuilder resRev = new StringBuilder();
                    int carry = 0;
                    for (int col = 0; col < L; col++) {
                        int columnSum = carry; // include carry-in from previous column
                        for (String s : nums) {
                            int idx = s.length() - 1 - col;
                            if (idx >= 0) columnSum += (s.charAt(idx) - '0');
                        }
                        int digit = columnSum % 10;
                        carry = columnSum / 10;  // carry to next column
                        carryOut[col] = carry;   // record carry produced by this column
                        resRev.append((char)('0' + digit));
                    }
                    // Flush any remaining carry to result
                    while (carry > 0) {
                        resRev.append((char)('0' + (carry % 10)));
                        carry /= 10;
                    }
                    String result = resRev.reverse().toString();

                    // Build the carry row with exact column alignment using NBSP placeholders
                    final char NBSP = '\u00A0';
                    StringBuilder carryFull = new StringBuilder();
                    int finalCarry = (L > 0) ? carryOut[L-1] : 0;
                    if (finalCarry > 0) carryFull.append((char)('0' + finalCarry));
                    // Per-column carries from MS digit down to LSD, inserting LSD placeholder at the end
                    for (int col = L - 1; col >= 0; col--) {
                        if (col == 0) {
                            // LSD placeholder so the row width matches the numbers
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

                    // Print result in blue
                    at.append("\n");
                    SpannableString ss1 = new SpannableString(result);
                    ss1.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, ss1.length(), 0);
                    at.append(ss1);
                    at.append("\n\n");
                    return;
                }

                // DECIMAL BRANCH
                setContentView(R.layout.added);
                isShowingResult = true;
                FontUtils.applyToActivity(Addition.this);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setTypeface(FontUtils.getRobotoMono(Addition.this));
                mAdView=(AdView)findViewById(R.id.adView);
                // Adaptive banner
                if (mAdView != null) {
                    AdUtils.loadAdaptiveBanner(Addition.this, mAdView);
                }

                // Parse and normalize numbers with decimals
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

                // Build expression display normalized for fractional alignment only
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

                // Prepare scaled integer strings for arithmetic: left-pad to fixed L = maxIntLen + maxFrac
                int L = maxIntLen + maxFrac; // digits-only width
                java.util.List<String> scaled = new java.util.ArrayList<>();
                for (int i=0; i<original.size(); i++) {
                    StringBuilder frac = new StringBuilder(partsFrac.get(i));
                    while (frac.length() < maxFrac) frac.append('0');
                    String sNoPad = partsInt.get(i) + frac; // do not strip leading zeros
                    // left-pad to L
                    int pad = L - sNoPad.length();
                    StringBuilder sb = new StringBuilder();
                    for (int p=0; p<pad; p++) sb.append('0');
                    sb.append(sNoPad);
                    String s = sb.toString();
                    if (s.length() > 15) { at.setText("Maximum digits(15) exceeded\n"); return; }
                    scaled.add(s);
                }

                // Column-wise addition on scaled strings
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

                // Insert decimal point and keep exactly maxFrac fractional digits
                String resultOut;
                if (maxFrac > 0) {
                    // Ensure the scaled result has at least maxFrac digits
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

                // Build carry row with NBSPs and a NBSP placeholder for the decimal point
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
                // Insert dot placeholder after integer digits (taking into account optional leading final carry)
                if (maxFrac > 0) {
                    int prefix = (finalCarry2 > 0) ? 1 : 0;
                    int splitPos = prefix + (L - maxFrac); // integer digits count
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

                // Print result in blue
                at.append("\n");
                SpannableString ss1 = new SpannableString(resultOut);
                ss1.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN), 0, ss1.length(), 0);
                at.append(ss1);
                at.append("\n\n");
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // If on result screen, go back to input by recreating; else finish as usual
                if (isShowingResult) {
                    isShowingResult = false;
                    recreate();
                } else {
                    this.finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
}

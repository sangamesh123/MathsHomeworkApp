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

public class Subtraction extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,bdot; // add dot
    ImageButton bsp, beq;
    TextView et;
    TextView at;
    long val1=0,val2=0;
    boolean add;

    // Region: helpers for borrow computation and rendering
    private static final char NBSP = '\u00A0';

    // Build borrow markers for integer-like digit arrays (no dot).
    // digits[0]..digits[L-1] are left->right digits of the minuend; subs[i] arrays same size for each subtrahend (already padded on the left with zeros).
    private char[] computeBorrowMarkers(int[] top, int[][] subs) {
        int L = top.length;
        char[] mark = new char[L];
        for (int i = 0; i < L; i++) mark[i] = NBSP;
        int borrowIn = 0;
        for (int k = L - 1; k >= 0; k--) {
            int sumSub = 0;
            for (int i = 0; i < subs.length; i++) sumSub += subs[i][k];
            int available = top[k] - borrowIn;
            if (available < sumSub) {
                // borrow from next higher column -> place mark above that column (k-1)
                if (k - 1 >= 0) mark[k - 1] = '1';
                borrowIn = 1;
            } else {
                borrowIn = 0;
            }
        }
        // LSD (rightmost) must never show a mark; ensure NBSP (already by initialization)
        return mark;
    }

    // Convert a string of digits into an int array of digits left->right, padded on the left to length L with zeros
    private int[] leftPadToDigits(String s, int L) {
        int[] out = new int[L];
        int offset = L - s.length();
        for (int i = 0; i < offset; i++) out[i] = 0;
        for (int i = 0; i < s.length(); i++) out[offset + i] = s.charAt(i) - '0';
        return out;
    }

    // Build a display string for the borrow row with optional decimal point. The underline will be applied to the entire returned string.
    private String buildBorrowRowString(char[] markers, int maxFrac) {
        // markers length = intLen + maxFrac (digits only). We need to insert a NBSP placeholder for the decimal point if maxFrac>0
        if (maxFrac <= 0) {
            return new String(markers);
        }
        int L = markers.length; // = intLen + maxFrac
        int intLen = L - maxFrac;
        char[] out = new char[L + 1];
        // copy integer part marks
        System.arraycopy(markers, 0, out, 0, intLen);
        // dot placeholder (NBSP so it doesn't draw a mark)
        out[intLen] = NBSP;
        // copy fractional part marks after the placeholder
        System.arraycopy(markers, intLen, out, intLen + 1, maxFrac);
        return new String(out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);
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
        bdot=(Button) findViewById(R.id.dot);
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
                et.setText(et.getText()+"\n"+"- ");
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

        // Dot button behavior (allow at most one '.' per current line)
        if (bdot != null) {
            bdot.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    String full = et.getText().toString();
                    int ln = full.lastIndexOf('\n');
                    String currentLine = (ln==-1)? full : full.substring(ln+1);
                    if(currentLine.startsWith("- ")) currentLine = currentLine.substring(2);
                    if(currentLine.contains(".")) return;
                    if(full.endsWith("\n- ") || full.endsWith("- ") || currentLine.isEmpty()){
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
                if(raw.trim().isEmpty()) return;
                boolean hasDecimal = raw.contains(".");

                if(!hasDecimal) {
                    // Integer branch with correct borrow alignment
                    setContentView(R.layout.added);
                    FontUtils.applyToActivity(Subtraction.this);
                    at=(TextView) findViewById(R.id.txtScr);
                    at.setMovementMethod(new ScrollingMovementMethod());
                    at.setTypeface(FontUtils.getRobotoMono(Subtraction.this));
                    mAdView=(AdView)findViewById(R.id.adView);
                    AdRequest adRequest =new AdRequest.Builder().build();
                    if (mAdView != null) {
                        mAdView.loadAd(adRequest);
                    }

                    String[] lines = raw.split("\n");
                    java.util.List<String> nums = new java.util.ArrayList<>();
                    int L = 0;
                    for(int i=0;i<lines.length;i++){
                        String s = (i==0)? lines[i] : (lines[i].length()>=2? lines[i].substring(2):"");
                        if(s.isEmpty()) s = "0";
                        if(!s.matches("\\d+")) { at.setText("Invalid number format\n"); return; }
                        if(s.length()>15){ at.setText("Maximum digits(15) exceeded\n"); return; }
                        if(s.length()>L) L=s.length();
                        nums.add(s);
                    }
                    // Show expression as typed
                    StringBuilder expr = new StringBuilder();
                    expr.append(nums.get(0));
                    for(int i=1;i<nums.size();i++) expr.append("\n- ").append(nums.get(i));
                    at.setText(expr.toString());

                    // Prepare digits arrays (left-padded)
                    int[] top = leftPadToDigits(nums.get(0), L);
                    int[][] subs = new int[Math.max(0, nums.size()-1)][];
                    for (int i=1; i<nums.size(); i++) subs[i-1] = leftPadToDigits(nums.get(i), L);

                    // Compute borrow markers across integer columns
                    char[] markers = computeBorrowMarkers(top, subs);
                    String borrowRow = new String(markers); // digits-only width L

                    // Compute numeric result (BigInteger)
                    java.math.BigInteger minuend = new java.math.BigInteger(nums.get(0));
                    java.math.BigInteger sub = java.math.BigInteger.ZERO;
                    for(int i=1;i<nums.size();i++) sub = sub.add(new java.math.BigInteger(nums.get(i)));
                    java.math.BigInteger resBI = minuend.subtract(sub);

                    // Render borrow row (aligned to digits area only)
                    if(resBI.signum()>=0) {
                        at.append("\n");
                        SpannableString ss2 = new SpannableString(borrowRow);
                        ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                        ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                        at.append(ss2);
                    } else {
                        at.append("\n");
                        // show only an underline with no carry digits
                        char[] blanks = new char[borrowRow.length()];
                        java.util.Arrays.fill(blanks, NBSP);
                        SpannableString ss2 = new SpannableString(new String(blanks));
                        ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                        ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                        at.append(ss2);
                    }

                    // Print result
                    String out = resBI.toString();
                    SpannableString ss1 = new SpannableString(out);
                    ss1.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN),0,ss1.length(),0);
                    at.append("\n");
                    at.append(ss1);
                    at.append("\n\n");
                    return;
                }

                // DECIMAL BRANCH (normalize display and compute with scaled integers)
                setContentView(R.layout.added);
                FontUtils.applyToActivity(Subtraction.this);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setTypeface(FontUtils.getRobotoMono(Subtraction.this));
                mAdView=(AdView)findViewById(R.id.adView);
                AdRequest adRequest =new AdRequest.Builder().build();
                if (mAdView != null) {
                    mAdView.loadAd(adRequest);
                }

                String[] lines = raw.split("\n");
                java.util.List<String> original = new java.util.ArrayList<>();
                java.util.List<String> ints = new java.util.ArrayList<>();
                java.util.List<String> fracs = new java.util.ArrayList<>();
                int maxFrac = 0;
                int maxIntLen = 0;
                for (int i=0;i<lines.length;i++){
                    String s = (i==0)? lines[i] : (lines[i].length()>=2? lines[i].substring(2):"");
                    if(s.isEmpty()) s="0";
                    if(!s.matches("\\d+(\\.\\d+)?")) { at.setText("Invalid number format\n"); return; }
                    original.add(s);
                    int dot = s.indexOf('.');
                    if(dot>=0) {
                        String ip = s.substring(0,dot);
                        String fp = s.substring(dot+1);
                        ints.add(ip);
                        fracs.add(fp);
                        if(fp.length()>maxFrac) maxFrac=fp.length();
                        if(ip.length()>maxIntLen) maxIntLen=ip.length();
                    }
                    else {
                        ints.add(s);
                        fracs.add("");
                        if(s.length()>maxIntLen) maxIntLen=s.length();
                    }
                }
                // Normalize fractional widths only for display
                StringBuilder expr = new StringBuilder();
                for(int i=0;i<original.size();i++){
                    StringBuilder f = new StringBuilder(fracs.get(i));
                    while(f.length()<maxFrac) f.append('0');
                    String line = (maxFrac>0)? (ints.get(i)+"."+f) : ints.get(i);
                    if(i==0) expr.append(line); else expr.append("\n- ").append(line);
                }
                at.setText(expr.toString());

                // Prepare scaled integer strings for arithmetic (no trimming of leading zeros; just left-pad to equal length)
                int L = maxIntLen + maxFrac; // digits only width (no dot)
                java.util.List<String> scaled = new java.util.ArrayList<>();
                for(int i=0;i<original.size();i++){
                    String ip = ints.get(i);
                    StringBuilder fp = new StringBuilder(fracs.get(i));
                    while(fp.length()<maxFrac) fp.append('0');
                    String s = ip + fp;
                    // left pad to L to keep arrays same length
                    StringBuilder pad = new StringBuilder();
                    for(int k=0;k<L-s.length();k++) pad.append('0');
                    scaled.add(pad + s);
                }

                // Convert to digit arrays
                int[] top = leftPadToDigits(scaled.get(0), L);
                int[][] subs = new int[Math.max(0, scaled.size()-1)][];
                for (int i=1; i<scaled.size(); i++) subs[i-1] = leftPadToDigits(scaled.get(i), L);

                // Compute numeric result using BigInteger with scaling 10^maxFrac
                BigInteger minuend = new BigInteger(scaled.get(0));
                BigInteger sub = BigInteger.ZERO;
                for(int i=1;i<scaled.size();i++) sub = sub.add(new BigInteger(scaled.get(i)));
                BigInteger result = minuend.subtract(sub);

                // Build borrow markers across integer and fractional columns
                char[] markers = computeBorrowMarkers(top, subs); // length L
                String borrowRow = buildBorrowRowString(markers, maxFrac); // length L (+1 if has dot)

                // Render borrow row with underline exactly across digits width (including dot placeholder)
                if(result.signum()>=0) {
                    at.append("\n");
                    SpannableString ss2 = new SpannableString(borrowRow);
                    ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                    ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                    at.append(ss2);
                } else {
                    at.append("\n");
                    // show only an underline with no carry digits
                    char[] blanks = new char[borrowRow.length()];
                    java.util.Arrays.fill(blanks, NBSP);
                    SpannableString ss2 = new SpannableString(new String(blanks));
                    ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                    ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                    at.append(ss2);
                }

                // Format and append result with exactly maxFrac fractional digits
                String sign = "";
                if(result.signum()<0){ sign = "-"; result = result.negate(); }
                String rs = result.toString();
                while(maxFrac>0 && rs.length()<=maxFrac) rs = "0"+rs;
                String out;
                if(maxFrac>0){
                    int split = rs.length()-maxFrac; if(split<0) split=0;
                    String ip = rs.substring(0, split);
                    String fp = rs.substring(split);
                    if(ip.isEmpty()) ip="0";
                    while(fp.length()<maxFrac) fp = fp+"0";
                    out = sign + ip + "." + fp;
                } else { out = sign + rs; }

                at.append("\n");
                SpannableString ss = new SpannableString(out);
                ss.setSpan(new ForegroundColorSpan(Colors.LCM_GREEN),0,ss.length(),0);
                at.append(ss);
                at.append("\n\n");
            }
        });


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

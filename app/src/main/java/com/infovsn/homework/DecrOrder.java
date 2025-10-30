package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class DecrOrder extends BaseActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr,back;
    TextView et;
    ImageButton bsp,badd,beq;
    TextView at;
    private boolean isShowingResult = false; // track if showing result layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decr_order);
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
        badd=(ImageButton) findViewById(R.id.add);
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
                et.setText(et.getText()+"\n");
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

        beq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture input before switching layout
                String txt = et.getText()+"";

                setContentView(R.layout.added);
                isShowingResult = true; // now in result layout
                // Set toolbar title for Descending Order result
                MaterialToolbar tb = findViewById(R.id.toolbar);
                if (tb != null) tb.setTitle(R.string.m7);
                FontUtils.applyToActivity(DecrOrder.this);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                at.setTypeface(FontUtils.getRobotoMono(DecrOrder.this));

                // Load adaptive in-flow native ad (no overlap) like Table and Addition
                setupAdaptiveAdForAdded();

                String[] split=txt.split("\n");
                String fn="";
                String sn="";
               boolean trick=false;
                // Use lists that preserve original string formatting while sorting by numeric value (descending)
                List<String> originals = new ArrayList<>();
                List<Double> values = new ArrayList<>();
                for(int i=0;i<split.length;i++)
                {
                    fn = split[i];
                    if(fn.length()==0)
                    {
                        fn = "0";
                    }

                    String toParse = fn;
                    if (toParse.endsWith(".")) toParse = toParse + "0";

                    // Validate numeric format (integer or decimal)
                    if (!toParse.matches("\\d+(\\.\\d+)?")) {
                        at.setText("Invalid number format\n");
                        trick=true;
                        break;
                    }

                    if (toParse.replace(".", "").length() > 15) {
                        at.setText("Maximum digits(15) exceeded\n");
                        trick=true;
                        break;
                    }

                    double dval = Double.parseDouble(toParse);
                    originals.add(fn); // preserve original formatting
                    values.add(dval);
                }

                if (trick) {
                    // message already set above; finalize and return with monospace
                  //  at.append("\n");
                    at.setTypeface(FontUtils.getRobotoMono(DecrOrder.this));
                    return;
                }

                // Sort descending (keep original strings in sync)
                for (int i = 0; i < (values.size()-1); i++) {
                    for (int j = 0; j < (values.size()-i-1); j++) {
                        if (values.get(j) < values.get(j+1))
                        {
                            double tempVal = values.get(j);
                            values.set(j, values.get(j+1));
                            values.set(j+1, tempVal);
                            String tempStr = originals.get(j);
                            originals.set(j, originals.get(j+1));
                            originals.set(j+1, tempStr);
                        }
                    }
                }

                StringBuilder sb = new StringBuilder();
                for(String s : originals)
                {
                    sb.append(s).append("\n");
                }
                at.setText(sb.toString());

                // finalize
               // at.append("\n");
            }
        });

        Button bdot = (Button) findViewById(R.id.dot);
        bdot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full = et.getText().toString();
                int ln = full.lastIndexOf('\n');
                String currentLine = (ln==-1)? full : full.substring(ln+1);
                if(currentLine.contains(".")) return; // already has a decimal
                if(full.endsWith("\n") || currentLine.length()==0){
                    et.append("0.");
                } else {
                    et.append(".");
                }
            }
        });
        // When showing result, set monospace font
        // Example: at.setTypeface(Typeface.MONOSPACE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isShowingResult) {
                    isShowingResult = false;
                    recreate(); // return to input screen
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

    // Helper: measure remaining viewport and load native ad into added.xml ad_card accordingly
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
                    NativeAdHelper.loadAdaptiveBySpace(DecrOrder.this, adContainer, adCard, remaining);
                }
            }
        });
    }
}
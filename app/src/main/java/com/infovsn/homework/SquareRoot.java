package com.infovsn.homework;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SquareRoot extends AppCompatActivity {
    private AdView mAdView;
    int ex=0;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,back;
    TextView et;
    ImageButton bsp, beq;
    TextView at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_root);
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
                et.setText(et.getText()+".");
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
                String cc="";
                setContentView(R.layout.added);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());

                //ADDS BY GOOGLE
                mAdView=(AdView)findViewById(R.id.adView);
//                mAdView.setAdListener(new ToastAdListener(SquareRoot.this));
                AdRequest adRequest =new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

                cc=et.getText().toString();
                if(cc.length()>0)
                squareroot();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void squareroot()
    {
        boolean temp=false;
        String sqr,sq;
        double myNum = 0;
        double num=999999999999999f;
        myNum = Double.parseDouble(et.getText().toString());
        if(myNum>num)
        {
            temp=true;
        }
        sq="SqareRoot\n";
        at.setText(et.getText());
        at.append("\n\n");
        SpannableString ss1=new SpannableString(sq);
        ss1.setSpan(new RelativeSizeSpan(1.2f),0,ss1.length(),0);
        ss1.setSpan(new ForegroundColorSpan(Color.BLUE),0,ss1.length(),0);
        at.append(ss1);
       num=Math.sqrt(myNum);

        sqr=String.format("%.2f",num)+"";
        SpannableString ss2=new SpannableString(sqr);
        ss2.setSpan(new RelativeSizeSpan(1.2f),0,ss2.length(),0);
        ss2.setSpan(new ForegroundColorSpan(Color.RED),0,ss2.length(),0);
        at.append(ss2);
        if(temp)
            at.setText("\"Maximum digits(15) exceeded\\n\"");
        at.append("\n\n");
    }
}
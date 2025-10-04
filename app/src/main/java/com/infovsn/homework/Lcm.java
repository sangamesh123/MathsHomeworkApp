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

import java.util.ArrayList;
import java.util.List;

public class Lcm extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr,beq,back;
    TextView et;
    ImageButton bsp,badd;
    TextView at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
        beq=(Button) findViewById(R.id.equal);
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

                setContentView(R.layout.added);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());

                back=(Button) findViewById(R.id.bck);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                //ADDS BY GOOGLE
                mAdView=(AdView)findViewById(R.id.adView);
//                mAdView.setAdListener(new ToastAdListener(Lcm.this));
                AdRequest adRequest =new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                String txt=et.getText()+"";
                String[] split=txt.split("\n");
                String fn="";
                String sn="";
                boolean trick=false;
                long m=0,res=0;
                List<Long> list = new ArrayList<>();
                for(int i=0;i<split.length;i++)
                {
                    fn = split[i];
                    if(fn.length()==0)
                    {
                        fn=0+"";
                    }
                    else if(fn.length()>15)
                    {
                        at.setText("Maximum digits(15) exceeded\n");
                        trick=true;
                        break;
                    }
                    else
                    {
                        m=Long.parseLong(fn);
                        list.add(m);
                    }
                }
                if(list.size()==0)
                {
                    list.add(0L);
                }
                long n[]=new long[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    n[i]=list.get(i);
                    sn=sn+n[i]+"\n";
                }
                at.setText(sn);
                res=lcm(n);
                if(res<0)
                {
                    trick=true;
                }
                sn="\n"+"LCM : "+res;
                SpannableString ss1=new SpannableString(sn);
                ss1.setSpan(new RelativeSizeSpan(1.2f),0,ss1.length(),0);
                ss1.setSpan(new ForegroundColorSpan(Color.RED),0,ss1.length(),0);
                at.append(ss1);
                if(trick)
                {
                    at.setText("Maximum digits(15) exceeded\n");
                }
                at.append("\n\n");
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
    private static long gcd(long a, long b)
    {
        while(b>0)
        {
            long temp=b;
            b=a%b;
            a=temp;
        }
        return a;
    }

    private static long gcd(long[] input)
    {
        long result=input[0];
        for(int i=1;i<input.length;i++)
            result=gcd(result,input[i]);
        return result;
    }

    private static long lcm(long a,long b)
    {
        return a*(b/gcd(a,b));
    }
    private static long lcm(long[] input)
    {
        long result=input[0];
        for(int i=1;i<input.length;i++)
            result=lcm(result,input[i]);
        return result;
    }
}

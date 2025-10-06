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

public class Subtraction extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,beq;
    TextView et;
    ImageButton bsp;
    TextView at;
    long val1=0,val2=0;
    boolean add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);
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
        badd=(Button) findViewById(R.id.add);
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

        beq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.added);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());


                //ADDS BY GOOGLE
                mAdView=(AdView)findViewById(R.id.adView);
//                mAdView.setAdListener(new ToastAdListener(Subtraction.this));
                AdRequest adRequest =new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                String txt=et.getText()+"";
                String[] split=txt.split("\n");
                String fn="";
                String sn="";
                String dn="";
                long m=0,n=0;
                long rem_m,rem_n,sum=0;
                int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0,c8=0,c9=0,c10=0,c11=0,c12=0,c13=0,c14=0,c15=0,c16=0,l=0;
                int t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0,t10=0,t11=0,t12=0,t13=0,t14=0,t15=0;
                for(int i=0;i<split.length;i++)
                {
                    int round=1;
                    if(i==0) {
                        fn = split[i];
                        dn=fn;
                        l = fn.length();
                        if(fn.length()==0)
                        {
                            fn=0+"";
                        }
                        if(l>15)
                        {
                            at.setText("Maximum digits(15) exceeded\n");
                            break;
                        }
                    }
                    if(i>0) {
                        fn = split[i].substring(2);
                        if(l<fn.length())
                        {
                            l=fn.length();
                        }
                        if(fn.length()==0)
                        {
                            fn=0+"";
                        }
                        if(l>15)
                        {
                            at.setText("Maximum digits(15) exceeded\n");
                            break;
                        }
                    }
                    val2=Long.parseLong(fn);
                    n=val2;
                    while(m!=0 && n!=0)
                    {
                        rem_m=m%10;
                        rem_n=n%10;

                        if (round == 1) {
                            sum = rem_m - rem_n;
                            if (sum < 0)
                                c1++;
                        }
                        if (round == 2) {
                            sum = rem_m - rem_n - c1;
                            if (sum < 0)
                                c2++;
                        }
                        if (round == 3) {
                            sum = rem_m - rem_n - c2;
                            if (sum <0)
                                c3++;
                        }
                        if (round == 4) {
                            sum = rem_m - rem_n - c3;
                            if (sum <0)
                                c4++;
                        }
                        if (round == 5) {
                            sum = rem_m - rem_n - c4;
                            if (sum <0)
                                c5++;
                        }
                        if (round == 6) {
                            sum = rem_m - rem_n - c5;
                            if (sum <0)
                                c6++;
                        }
                        if (round == 7) {
                            sum = rem_m - rem_n - c6;
                            if (sum <0)
                                c7++;
                        }
                        if(round==8)
                        {
                            sum=rem_m-rem_n-c7;
                            if(sum<0)
                                c8++;
                        }
                        if(round==9)
                        {
                            sum=rem_m-rem_n-c8;
                            if(sum<0)
                                c9++;
                        }
                        if(round==10)
                        {
                            sum=rem_m-rem_n-c9;
                            if(sum<0)
                                c10++;
                        }
                        if(round==11)
                        {
                            sum=rem_m-rem_n-c10;
                            if(sum<0)
                                c11++;
                        }
                        if(round==12)
                        {
                            sum=rem_m-rem_n-c11;
                            if(sum<0)
                                c12++;
                        }
                        if(round==13)
                        {
                            sum=rem_m-rem_n-c12;
                            if(sum<0)
                                c13++;
                        }
                        if(round==14)
                        {
                            sum=rem_m-rem_n-c13;
                            if(sum<0)
                                c14++;
                        }
                        if(round==15)
                        {
                            sum=rem_m-rem_n-c14;
                            if(sum<0)
                                c15++;
                        }
                        if(round==16)
                        {
                            sum=rem_m-rem_n-c15;
                            if(sum<0)
                                c16++;
                        }


                        m=m/10;
                        n=n/10;
                        round++;
                    }
                    if(i==0)
                        val1=val2;
                    else
                        val1=val1-val2;
                    m=val1;
                }


//              val2 = Integer.parseInt(et.getText() + "");
//                if (add == true)
//                    et.setText(null);
//                    et.setText(val1 + val2 + "");
//                    add = false;
//                }

                sn=sn+et.getText()+"\n";
                at.setText(sn);
                String s3="";
                int flag=0;
                int gr=8;
                if(l>gr)
                {
                    gr=l-1;
                }
                for(int i=gr;i>0;i--)
                {
                    if(i==16)
                    {
                        if((c16>0)&&(i<=l-1)) {
                            s3 = s3 + c16 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==15)
                    {
                        if((c15>0)&&(i<=l-1)) {
                            s3 = s3 + c15 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==14)
                    {
                        if((c14>0)&&(i<=l-1)) {
                            s3 = s3 + c14 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==13)
                    {
                        if((c13>0)&&(i<=l-1)) {
                            s3 = s3 + c13 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==12)
                    {
                        if((c12>0)&&(i<=l-1)) {
                            s3 = s3 + c12 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==11)
                    {
                        if((c11>0)&&(i<=l-1)) {
                            s3 = s3 + c11 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==10)
                    {
                        if((c10>0)&&(i<=l-1)) {
                            s3 = s3 + c10 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==9)
                    {
                        if((c9>0)&&(i<=l-1)) {
                            s3 = s3 + c9 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==8)
                    {
                        if((c8>0)&&(i<=l-1)) {
                            s3 = s3 + c8 + "";
                            flag=1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==7)
                    {if((c7>0)&&(i<=l-1))
                    {
                        s3=s3+c7+"";
                        flag=1;}
                    else
                        s3=s3+"  ";
                    }
                    if(i==6)
                    {if((c6>0)&&(i<=l-1)) {
                        s3 = s3 + c6 + "";
                        flag = 1;
                    }
                    else
                        s3=s3+"  ";
                    }
                    if(i==5)
                    {if((c5>0)&&(i<=l-1)) {
                        s3 = s3 + c5 + "";
                        flag = 1;
                    }
                    else
                        s3=s3+"  ";
                    }
                    if(i==4)
                    {if((c4>0)&&(i<=l-1)) {
                        s3 = s3 + c4 + "";
                        flag = 1;
                    }
                    else
                        s3=s3+"  ";
                    }
                    if(i==3)
                    {
                        if((c3>0)&&(i<=l-1)) {
                            s3 = s3 + c3 + "";
                            flag = 1;
                        }
                        else
                            s3=s3+"  ";
                    }
                    if(i==2)
                    {if((c2>0)&&(i<=l-1))
                    {
                        s3=s3+c2+"";
                        flag=1;}
                    else
                        s3=s3+"  ";
                    }
                    if(i==1)
                    {if((c1>0)&&(i<=l-1)) {
                        s3 = s3 + c1 + "";
                        flag = 1;
                    }
                    else
                        s3=s3+"  ";
                    }

                }
                s3=s3+"  ";
                if(val1<0)
                {
                    flag=0;
                }
                if(flag==1) {
                    SpannableString ss2 = new SpannableString(s3);
                    ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                    ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                    at.append(ss2);
                }
                if(flag==0)
                {
                    s3="____________";
                    at.append(s3);
                }
                at.append("\n");
                sn=val1+"";
                int we=sn.length();
                if(we<dn.length())
                {
                    for(int i=0;i<(dn.length()-we);i++)
                    {
                        sn="0"+sn;
                    }
                }
                SpannableString ss1=new SpannableString(sn);
                ss1.setSpan(new ForegroundColorSpan(Color.BLUE),0,ss1.length(),0);
                at.append(ss1);

//             sn=sn+ss1+"\n"+val1;
//                et.setText(sn);
                if(l>15)
                {
                    at.setText("Maximum digits(15) exceeded\n");
                }
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
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

public class Multiplication extends AppCompatActivity {
    private AdView mAdView;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, badd, bclr, back;
    TextView et;
    ImageButton bsp, beq;
    TextView at;
    long val1 = 0, val2 = 0;
    boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        bsp = (ImageButton) findViewById(R.id.backspace);
        bclr = (Button) findViewById(R.id.clear);
        beq = (ImageButton) findViewById(R.id.equal);
        et = (TextView) findViewById(R.id.txtScreen);
        et.setMovementMethod(new ScrollingMovementMethod());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "1");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "2");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "3");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "4");
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "5");
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "6");
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "7");
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "8");
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "9");
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setText(et.getText() + "0");
            }
        });


        badd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                val1=Integer.parseInt(et.getText()+"");
                String mm=et.getText().toString();
            if(!mm.contains("x")) {
                et.setText(et.getText() + "\n" + "x ");
            }
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
                at = (TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());

                //ADDS BY GOOGLE
                mAdView=(AdView)findViewById(R.id.adView);
//                mAdView.setAdListener(new ToastAdListener(Multiplication.this));
                AdRequest adRequest =new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

                int fl=0;
                long v1=0,v2=0;
                String txt = et.getText() + "";
                if(!txt.contains("x")) {
                    txt=txt+"\n" + "x ";
                }

                at.setText(txt);

                int tm=0;
                String yn="";
                String zn="";

                String[] split = txt.split("\n");
//                int gh=split.length;
//                String thh=gh+"";
//                at.append(thh);
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        yn = split[i];

                        if(yn.length()==0)
                        {
                            v1=1;
                            yn=1+"";
                            tm++;
                        }
                        else {
                            if(yn.length()>16)
                            {
                                v2=0;
                                zn=0+"";
                                break;
                            }
                            v1 = Long.parseLong(yn);
                        }
                    }
                    if (i > 0) {
                        zn = split[i].substring(2);

                        if(zn.length()==0)
                        {
                            v2=1;
                            zn=1+"";

                            if(tm==1)
                            {
                                v1=0;
                                v2=0;
                                zn=0+"";
                            }
                        }
                        else
                        {
                            if((zn.length()+yn.length())>16)
                            {
                                v1=0;
                                zn=0+"";
                                break;
                            }
                            v2=Long.parseLong(zn);
                            if(tm==1)
                            {
                                v1=v2;
                                v2=1;
                                zn=1+"";
                            }
                        }

                    }
                }

                String iii=v1+"";
                String ii=v2+"";
                String vv="";
                String mm="";
                for(int i=ii.length()-1;i>=0;i--)
                {
                    if(i==(ii.length()-1))
                    {

                            String q1=zn.charAt(i)+"";
                            int kkk=Integer.parseInt(q1);
                            mm=kkk*v1+"";
                            vv=kkk*v1+"";
                        if(kkk==0)
                        {
                            for(int k=0;k<iii.length()-1;k++)
                            {
                                vv=vv+"0";
                            }
                        }


                        //Temp******************************************************************************************
                        long ms=v1,rem_ms=0,um=0;
                        int ns=Integer.parseInt(q1);;
                        int roun=1;
                        long cd1=0,cd2=0,cd3=0,cd4=0,cd5=0,cd6=0,cd7=0,cd8=0,cd9=0,cd10=0,cd11=0,cd12=0,cd13=0,cd14=0,cd15=0,cd16=0;
                        while (ms != 0 && ns != 0) {
                            rem_ms = ms % 10;
                            if (roun == 1) {
                                um = rem_ms * ns;
                                if (um >= 10)
                                    cd1=um/10;
                            }
                            if (roun == 2) {
                                um = rem_ms *ns + cd1;
                                if (um >= 10)
                                    cd2=um/10;
                            }
                            if (roun == 3) {
                                um = rem_ms * ns + cd2;
                                if (um >= 10)
                                    cd3=um/10;
                            }
                            if (roun == 4) {
                                um = rem_ms * ns + cd3;
                                if (um >= 10)
                                    cd4=um/10;
                            }
                            if (roun == 5) {
                                um = rem_ms * ns + cd4;
                                if (um >= 10)
                                    cd5=um/10;
                            }
                            if (roun == 6) {
                                um = rem_ms * ns + cd5;
                                if (um >= 10)
                                    cd6=um/10;
                            }
                            if (roun == 7) {
                                um = rem_ms * ns + cd6;
                                if (um >= 10)
                                    cd7=um/10;
                            }
                            if (roun == 8) {
                                um = rem_ms * ns + cd7;
                                if (um >= 10)
                                    cd8=um/10;
                            }
                            if (roun == 9) {
                                um = rem_ms * ns + cd8;
                                if (um >= 10)
                                    cd9=um/10;
                            }
                            if (roun == 10) {
                                um = rem_ms * ns + cd9;
                                if (um >= 10)
                                    cd10=um/10;
                            }
                            if (roun == 11) {
                                um = rem_ms * ns + cd10;
                                if (um >= 10)
                                    cd11=um/10;
                            }
                            if (roun == 12) {
                                um = rem_ms * ns + cd11;
                                if (um >= 10)
                                    cd12=um/10;
                            }
                            if (roun == 13) {
                                um = rem_ms * ns + cd12;
                                if (um >= 10)
                                    cd13=um/10;
                            }
                            if (roun == 14) {
                                um = rem_ms * ns + cd13;
                                if (um >= 10)
                                    cd14=um/10;
                            }
                            if (roun == 15) {
                                um = rem_ms * ns + cd14;
                                if (um >= 10)
                                    cd15=um/10;
                            }
                            if (roun == 16) {
                                um = rem_ms * ns + cd15;
                                if (um >= 10)
                                    cd16=um/10;
                            }
                            ms = ms / 10;

                            roun++;
                        }

                        int kj=iii.length()-1;
                        int gr=8;
                        if(kj>gr)
                        {
                            gr=kj;
                        }
                        String sd3 = "";
                        for (int ig = gr; ig > 0; ig--) {
                            if (ig == 16) {
                                if ((cd16 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd16 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 15) {
                                if ((cd15 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd15 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 14) {
                                if ((cd14 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd14 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 13) {
                                if ((cd13 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd13 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 12) {
                                if ((cd12 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd12 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 11) {
                                if ((cd11 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd11 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 10) {
                                if ((cd10 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd10 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 9) {
                                if ((cd9 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd9 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 8) {
                                if ((cd8 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd8 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig== 7) {
                                if ((cd7 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd7 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 6) {
                                if ((cd6 > 0)&&(ig<=kj)){
                                    sd3 = sd3 + cd6 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 5) {
                                if ((cd5 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd5 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 4) {
                                if ((cd4 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd4 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 3) {
                                if ((cd3 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd3 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 2) {
                                if ((cd2 > 0)&&(ig<=kj)) {
                                    sd3 = sd3 + cd2 + "";
                                    fl= 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }
                            if (ig == 1) {
                                if ((cd1 > 0)&&(ig<=kj)){
                                    sd3 = sd3 + cd1 + "";
                                    fl = 1;
                                } else
                                    sd3 = sd3 + "  ";
                            }

                        }
                        sd3 = sd3 + "  ";
                        if (fl == 1) {
                            SpannableString ssd2 = new SpannableString(sd3);
                            ssd2.setSpan(new UnderlineSpan(), 0, ssd2.length(), 0);
                            ssd2.setSpan(new ForegroundColorSpan(Color.RED), 0, ssd2.length(), 0);
                            at.append("\n");
                            at.append(ssd2);
                        }
                        if (fl==0) {
                            sd3 = "\n____________";
                            at.append(sd3);
                        }

                        //Temp*******************************************************************************************

                         if(ii.length()!=1) {
                            at.append("\n");
                            at.append(vv);
                            fl = 2;
                        }
                    }
                    else{
                        String q1=zn.charAt(i)+"";
                        int kkk=Integer.parseInt(q1);
                        mm=mm+"\n"+kkk*v1+"";
                        vv="\n"+kkk*v1+"";
                        if(kkk==0)
                        {
                            for(int k=0;k<iii.length()-1;k++)
                            {
                                vv=vv+"0";
                            }
                        }
                        for(int j=ii.length()-1;j>i;j--)
                         {
                               vv=vv+"+";
                             mm=mm+"0";
                         }
                         at.append(vv);
                    }
                }
                String[] spin = mm.split("\n");
                String fn = "";
                String sn = "";
                long m = 0, n = 0;
                long rem_m, rem_n, sum = 0;
                int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0,c8=0,c9=0,c10=0,c11=0,c12=0,c13=0,c14=0,c15=0,c16=0,l=0;
                int t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0,t10=0,t11=0,t12=0,t13=0,t14=0,t15=0;
                for (int i = 0; i < spin.length; i++) {
                    int round = 1;
                    if (i == 0) {
                        fn = spin[i];
                        l = fn.length();
                    }
                    if (i > 0) {
                        fn = spin[i];
                        if (l < fn.length()) {
                            l = fn.length();
                        }
                    }
                    val2 = Long.parseLong(fn);
                    n = val2;
                    while (m != 0 && n != 0) {
                        rem_m = m % 10;
                        rem_n = n % 10;

                        if(round==1)
                        {
                            sum=rem_m+rem_n;
                            if(sum>=10)
                            {
                                c1++;
                                if(i==split.length-1)
                                    t1=1;
                            }

                        }
                        if(round==2)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t1;
                            if(sum>=10)
                            {
                                c2++;
                                if(i==split.length-1)
                                    t2=1;
                            }
                        }
                        if(round==3)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t2;
                            if(sum>=10)
                            {
                                c3++;
                                if(i==split.length-1)
                                    t3=1;
                            }
                        }
                        if(round==4)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t3;
                            if(sum>=10)
                            {
                                c4++;
                                if(i==split.length-1)
                                    t4=1;
                            }
                        }
                        if(round==5)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t4;
                            if(sum>=10)
                            {
                                c5++;
                                if(i==split.length-1)
                                    t5=1;
                            }
                        }
                        if(round==6)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t5;
                            if(sum>=10)
                            {
                                c6++;
                                if(i==split.length-1)
                                    t6=1;
                            }
                        }
                        if(round==7)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t6;
                            if(sum>=10)
                            {
                                c7++;
                                if(i==split.length-1)
                                    t7=1;
                            }
                        }
                        if(round==8)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t7;
                            if(sum>=10)
                            {
                                c8++;
                                if(i==split.length-1)
                                    t8=1;
                            }
                        }
                        if(round==9)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t8;
                            if(sum>=10)
                            {
                                c9++;
                                if(i==split.length-1)
                                    t9=1;
                            }
                        }
                        if(round==10)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t9;
                            if(sum>=10)
                            {
                                c10++;
                                if(i==split.length-1)
                                    t10=1;
                            }
                        }
                        if(round==11)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t10;
                            if(sum>=10)
                            {
                                c11++;
                                if(i==split.length-1)
                                    t11=1;
                            }
                        }
                        if(round==12)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t11;
                            if(sum>=10)
                            {
                                c12++;
                                if(i==split.length-1)
                                    t12=1;
                            }
                        }
                        if(round==13)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t12;
                            if(sum>=10)
                            {
                                c13++;
                                if(i==split.length-1)
                                    t13=1;
                            }
                        }
                        if(round==14)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t13;
                            if(sum>=10)
                            {
                                c14++;
                                if(i==split.length-1)
                                    t14=1;
                            }
                        }
                        if(round==15)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t14;
                            if(sum>=10)
                            {
                                c15++;
                                if(i==split.length-1)
                                    t15=1;
                            }
                        }
                        if(round==16)
                        {
                            sum=rem_m+rem_n;
                            if(i==split.length-1)
                                sum=rem_m+rem_n+t15;
                            if(sum>=10)
                            {
                                c16++;
                            }
                        }
                        m = m / 10;
                        n = n / 10;
                        round++;
                    }
                    val1 = val1 + val2;
                    m = val1;
                }



//                sn = sn + et.getText() + "\n";
//                at.setText(sn);
                String s3 = "";
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
                if (flag == 1) {
                    SpannableString ss2 = new SpannableString(s3);
                    ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
                    ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), 0);
                    at.append("\n");
                    at.append(ss2);
                }
                if ((flag == 0)&&(fl==2)) {
                    s3 = "\n____________";
                    at.append(s3);
                }
                at.append("\n");
               sn=v1*v2+"";
                SpannableString ss1 = new SpannableString(sn);
                ss1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, ss1.length(), 0);
                at.append(ss1);
                if((tm==0)&&(v1==0)&&(v2==0))
                {
                    at.setText("Maximum digits(16) exceeded\n");
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
}
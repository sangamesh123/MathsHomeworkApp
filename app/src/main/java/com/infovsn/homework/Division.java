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

public class Division extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,back;
    TextView et;
    ImageButton bsp, beq;
    TextView at,kt,qt;
    long val1=0,val2=0;
    int move=0;
    boolean add;
    boolean pms=false;
    boolean down=false;
    int move2=0;
    String ipl="";
    // NBSP for building underlined blank rows (consistent with Subtraction)
    private static final char NBSP = '\u00A0';
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Apply Roboto Mono to all TextViews in this layout
        FontUtils.applyToActivity(this);


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
                String mm=et.getText().toString();
                if(!mm.contains("\u00F7")) {
                    et.setText(et.getText()+"\n"+"\u00F7"+" ");
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
                    // Guard against empty string before accessing last char
                    if (!smp.isEmpty()) {
                        char qq=smp.charAt(smp.length()-1);
                        if(qq=='\n')
                        {
                            smp = smp.substring(0, smp.length() - 1);
                            et.setText(smp);
                        }
                    }
                } else if (smp.length() <= 1) {
                    et.setText(null);
                }

            }
        });

        beq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.division);
                // Apply Roboto Mono to all TextViews in result layout
                FontUtils.applyToActivity(Division.this);
                at=(TextView) findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());
                kt=(TextView) findViewById(R.id.txtdiv);
                kt.setMovementMethod(new ScrollingMovementMethod());
                qt=(TextView) findViewById(R.id.quot);
                qt.setMovementMethod(new ScrollingMovementMethod());
                // Ensure main math output uses the monospace typeface explicitly
                at.setTypeface(FontUtils.getRobotoMono(Division.this));
                kt.setTypeface(FontUtils.getRobotoMono(Division.this));
                qt.setTypeface(FontUtils.getRobotoMono(Division.this));

                //ADDS BY GOOGLE
                mAdView=(AdView)findViewById(R.id.adView);
//                mAdView.setAdListener(new ToastAdListener(Division.this));
                AdRequest adRequest =new AdRequest.Builder().build();
                if (mAdView != null) {
                    mAdView.loadAd(adRequest);
                }

                long v1=0,v2=1;
                String txt = et.getText() + "";
                if(!txt.contains("\u00F7")) {
                    txt=txt+"\n" + "\u00F7 ";
                }

                int tm=0;
                String yn="";
                String zn="";
                String[] split = txt.split("\n");

                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        yn = split[i];

                        if(yn.length()==0)
                        {
                            v1=0;
                            yn=0+"";
                            tm++;
                        }
                        else {
                            if(yn.length()>10)
                            {
                                v1=0;
                                tm=4;
                            }
                            else {
                                // validate integer input
                                if (!yn.matches("\\d+")) {
                                    at.setText("Invalid number format\n");
                                    at.append("\n\n");
                                    return;
                                }
                                try {
                                    v1 = Long.parseLong(yn);
                                } catch (NumberFormatException nfe) {
                                    at.setText("Invalid number format\n");
                                    at.append("\n\n");
                                    return;
                                }
                            }
                        }
                    }
                    if (i > 0) {
                        // Ensure the line is long enough before substring(2)
                        String line = split[i];
                        if (line.length() >= 2) {
                            zn = line.substring(2);
                        } else {
                            zn = "";
                        }

                        if(zn.length()==0)
                        {
                            v2=1;
                            zn=1+"";
                            tm=3;

                            if(tm==1)
                            {
                                v1=0;
                                v2=1;
                                zn=1+"";
                            }
                        }
                        else
                        {
                            if((zn.length())>4)
                            {
                                v2=1;
                                zn=1+"";
                                tm=5;
                            }
                            else {
                                // validate integer input
                                if (!zn.matches("\\d+")) {
                                    at.setText("Invalid number format\n");
                                    at.append("\n\n");
                                    return;
                                }
                                try {
                                    v2=Long.parseLong(zn);
                                } catch (NumberFormatException nfe) {
                                    at.setText("Invalid number format\n");
                                    at.append("\n\n");
                                    return;
                                }
                            }
                        }

                    }
                }

                if(v2==0)
                {
                    tm=6;
                    v2=1;
                }


                at.setText(zn);
                String pn=")";
                SpannableString ssp1=new SpannableString(pn);
//                ssp1.setSpan(new RelativeSizeSpan(1.1f),0,1,0);
                ssp1.setSpan(new ForegroundColorSpan(Color.RED),0,ssp1.length(),0);
                at.append(ssp1);
                long v3=v1/v2;
                String qu=v3+"";

                // Build fractional quotient (decimal expansion) with limits:
                // - fractional part: up to 5 digits
                // - total digits (integer + fractional): up to 10 digits
                long rem = v1 % v2;
                StringBuilder displayQuot = new StringBuilder(qu);
                StringBuilder quotientDigits = new StringBuilder(qu); // digits only for step logic
                int decCount = 0;
                int intPartLen = qu.length();

                if (intPartLen >= 10) {
                    // Cap quotient to 10 integer digits, no decimals
                    String capped = qu.substring(0, 10);
                    displayQuot = new StringBuilder(capped);
                    quotientDigits = new StringBuilder(capped);
                    decCount = 0;
                } else {
                    int allowedDec = Math.min(5, 10 - intPartLen);
                    if(rem != 0 && allowedDec > 0) {
                        displayQuot.append('.');
                        while(rem != 0 && decCount < allowedDec) {
                            rem *= 10;
                            long d = rem / v2;
                            quotientDigits.append(d);
                            displayQuot.append(d);
                            rem = rem % v2;
                            decCount++;
                        }
                    }
                }

                qt.setText(displayQuot.toString());
                kt.setText(yn);
                kt.append("\n");
                String quotient="";
                String remender="";
                // Use full digits (integer + decimal) for step logic
                quotient=quotientDigits.toString();
                long v4=0;
                String divv="";
                String cur="";
                long just1=0,just2=0;
                int j=0,k=0;

                // Prepare extended dividend with borrowed zeros for decimal part
                StringBuilder zerosForYn = new StringBuilder();
                for(int z=0;z<decCount;z++) zerosForYn.append('0');
                String ynExt = yn + zerosForYn.toString();

                for(int i=0;i<quotient.length();i++)
                {

                    // Keep old special-case breaks only for pure integer divisions
                    if(i==0 && decCount==0)
                    {
                        if((quotient.length()==2)&&(quotient.charAt(1)=='0'))
                        {
                            v4=v3*v2;
                            remender=v4+"\n";
                            kt.append(remender);
                            divv=v1+"";
                            divv=divv+"\n"+v4;
                            divisioned(divv);
                            pms=true;
                            break;
                        }
                        else
                        {
                            if(quotient.charAt(0)=='0')
                            {
                                v4=v3*v2;
                                remender=v4+"\n";
                                String mt="";
                                if(yn.length()>remender.length())
                                {
                                    for(int x=0;x<((yn.length()-1));x++)
                                    {
                                        mt=mt+"0";
                                    }

                                }
                                if(yn.length()==2)
                                    mt="0";
                                SpannableString ss6 = new SpannableString(mt);
                                ss6.setSpan(new ForegroundColorSpan(Color.WHITE), 0, ss6.length(), 0);
                                kt.append(ss6);
                                kt.append(remender);
                                divv=v1+"";
                                divv=divv+"\n"+v4;
                                divisioned(divv);
                                pms=true;
                                break;
                            }
                        }
                    }
                    if(val1!=0)
                        cur=val1+"";
                    else
                    {
                        if((i!=0)&&(v4!=0))
                            move++;
                    }

                    String tmt="";
                    String t="";
                    t=quotient.charAt(i)+"";
                    try {
                        just1=Long.parseLong(t);
                    } catch (NumberFormatException nfe) {
                        at.setText("Invalid number format\n");
                        at.append("\n\n");
                        return;
                    }
                    v4=just1*v2;
                    if(v4!=0)
                    {
                        String df=v4+"";
                        for(int g=j;g<ynExt.length();g++)
                        {
                            if(df.length()>cur.length())
                            {
                                k++;
                                cur=cur+ynExt.substring(j,k);
                                tmt=tmt+ynExt.substring(j,k);
                                j++;
                            }
                            else
                            {
                                just2=0;
                                try {
                                    just2=Long.parseLong(cur);
                                } catch (NumberFormatException nfe) {
                                    at.setText("Invalid number format\n");
                                    at.append("\n\n");
                                    return;
                                }
                                if(v4>just2)
                                {
                                    k++;
                                    cur=cur+ynExt.substring(j,k);
                                    tmt=tmt+ynExt.substring(j,k);
                                    j++;
                                    move2++;
                                    down=true;
                                }
                            }

                        }
                        if(i!=0)
                        {
                            kt.append(tmt);
                        }
                        String nt="";
                        remender=v4+"\n";

                        if(i!=0)
                            kt.append("\n");
                        for(int h=0;h<move;h++)
                        {
                            nt=nt+"0";

                        }
                        if(down)
                        {
                            for(int h=0;h<move2;h++)
                            {
                                nt=nt+"0";
                            }
                            down=false;
                            move2=0;
                        }
                        SpannableString ss7 = new SpannableString(nt);
                        ss7.setSpan(new ForegroundColorSpan(Color.WHITE), 0, ss7.length(), 0);
                        kt.append(ss7);
                        kt.append(remender);
                        divv=cur;
                        divv=divv+"\n"+v4;
                        divisioned(divv);
                        j=k;
                        cur="";
                    }

                }

                // If we produced decimal digits, skip the legacy trailing-digits append logic
                if(decCount>0) {
                    pms = true;
                }

                if(decCount>0) {
                    pms = true;
                }
                if(!pms)
                {
                    String mane="";
                    long meto=0;
                    long abc=v3*v2;
                    abc=v1-abc;

                    if(k<quotient.length())
                    {
                        mane=yn.substring(k,yn.length());
                        try {
                            meto=Long.parseLong(mane);
                        } catch (NumberFormatException nfe) {
                            at.setText("Invalid number format\n");
                            at.append("\n\n");
                            return;
                        }
                    }
                    if(meto==abc)
                    {
                        kt.append(mane);
                    }
                    else if(abc>val1)
                    {
                        String ipl2=abc+"";
                        int lle=ipl2.length();
                        ipl=val1+"";
                        int lle2=ipl.length();
                        for(int z=0;z<lle-lle2;z++)
                        {
                            ipl=ipl+"0";
                        }
                        try {
                            val1=Long.parseLong(ipl);
                        } catch (NumberFormatException nfe) {
                            at.setText("Invalid number format\n");
                            at.append("\n\n");
                            return;
                        }
                        abc=abc-val1;
                        ipl2=abc+"";
                        kt.append(ipl2);
                    }


                }

                if(tm==1)
                {
                    qt.setText("0");
                    kt.setText("0");
                }
                if(tm==3)
                {
                    at.setText(")");
                    qt.setText(yn);
                    kt.setText(yn);
                }
                if(tm==4)
                {
                    at.setText(null);
                    qt.setText(null);
                    kt.setText("Dividend exceeds maximum digits(10)");
                }
                if(tm==5)
                {
                    at.setText(null);
                    qt.setText(null);
                    kt.setText("Divisor exceeds maximum digits(4)");
                }
                if(tm==6)
                {
                    at.setText("0)");
                    qt.setText("infinity");
                    kt.setText(yn);
                }
                at.append("\n\n");
//             sn=sn+ss1+"\n"+val1;
//                et.setText(sn);

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


    public void divisioned(String sample)
    {
        String txt=sample;
        String[] split=txt.split("\n");
        String fn="";
        String sn="";
        String dn="";
        long m=0,n=0;
        long rem_m,rem_n,sum=0;
        int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0,c8=0,c9=0,l=0;
        for(int i=0;i<split.length;i++)
        {
            int round=1;
            if(i==0) {
                fn = split[i];
                dn=fn;
                l = fn.length();
            }
            if(i>0) {
                fn = split[i];
                if(l<fn.length())
                {
                    l=fn.length();
                }
                if(fn.length()==0)
                {
                    fn=0+"";
                }
            }
            // Safe parse for val2
            if (!fn.matches("\\d+")) {
                at.setText("Invalid number format\n");
                at.append("\n\n");
                return;
            }
            try {
                val2 = Long.parseLong(fn);
            } catch (NumberFormatException nfe) {
                at.setText("Invalid number format\n");
                at.append("\n\n");
                return;
            }
            n=val2;
            while(m!=0 && n!=0)
            {
                rem_m=m%10;
                rem_n=n%10;

                if(round==1)
                {
                    sum=rem_m-rem_n;
                    if(sum<0)
                        c1++;
                }
                if(round==2)
                {
                    sum=rem_m-rem_n-c1;
                    if(sum<0)
                        c2++;
                }
                if(round==3)
                {
                    sum=rem_m-rem_n-c2;
                    if(sum<0)
                        c3++;
                }
                if(round==4)
                {
                    sum=rem_m-rem_n-c3;
                    if(sum<0)
                        c4++;
                }
                if(round==5)
                {
                    sum=rem_m-rem_n-c4;
                    if(sum<0)
                        c5++;
                }
                if(round==6)
                {
                    sum=rem_m-rem_n-c5;
                    if(sum<0)
                        c6++;
                }
                if(round==7)
                {
                    sum=rem_m-rem_n-c6;
                    if(sum<0)
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

        // Build shifted carry row: one char per digit, position p shows c(p-1); p==1 has no borrow source
        StringBuilder s3b = new StringBuilder(l);
        int flag=0;
        for (int p = l; p >= 1; p--) {
            int borrow = 0;
            switch (p - 1) {
                case 1: borrow = c1; break;
                case 2: borrow = c2; break;
                case 3: borrow = c3; break;
                case 4: borrow = c4; break;
                case 5: borrow = c5; break;
                case 6: borrow = c6; break;
                case 7: borrow = c7; break;
                case 8: borrow = c8; break;
                case 9: borrow = c9; break;
                default: borrow = 0; // positions beyond tracked borrows or p==1
            }
            if (borrow > 0) {
                s3b.append((char)('0' + borrow));
                flag = 1;
            } else {
                s3b.append(NBSP);
            }
        }

        if(val1<0)
        {
            flag=0;
        }
        sn=val1+"";

        String nt="";
        for(int h=0;h<move;h++)
        {
            nt=nt+"0";
        }
        SpannableString ss8 = new SpannableString(nt);
        ss8.setSpan(new ForegroundColorSpan(Color.WHITE), 0, ss8.length(), 0);
        kt.append(ss8);

        if(flag==1) {
            SpannableString ss2 = new SpannableString(s3b.toString());
            ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(Color.BLUE), 0, ss2.length(), 0);
            kt.append(ss2);
        }

        if(flag==0)
        {
            // Build dynamic underlined blanks sized to the upcoming remainder length
            // Determine the remainder that will be shown next (val1), padded to at least the divisor width
            int remainderLen = String.valueOf(Math.abs(val1)).length();
            int divisorLen = (dn != null) ? dn.length() : remainderLen;
            int underlineWidth = Math.max(remainderLen, divisorLen);

            StringBuilder blanksBuilder = new StringBuilder(underlineWidth);
            for (int i = 0; i < underlineWidth; i++) {
                blanksBuilder.append(NBSP);
            }
            SpannableString ss2 = new SpannableString(blanksBuilder.toString());
            ss2.setSpan(new UnderlineSpan(), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(Color.BLUE), 0, ss2.length(), 0);
            kt.append(ss2);
        }
        kt.append("\n");


        String knt="";
        for(int h=0;h<move;h++)
        {
            knt=knt+"0";
        }
        SpannableString ss9 = new SpannableString(knt);
        ss9.setSpan(new ForegroundColorSpan(Color.WHITE), 0, ss9.length(), 0);
        kt.append(ss9);
        int we=sn.length();


        if(we<dn.length())
        {
            for(int i=0;i<(dn.length()-we);i++)
            {
                sn="0"+sn;
                move++;
            }
        }

//        SpannableString ss1=new SpannableString(sn);
//        ss1.setSpan(new ForegroundColorSpan(Color.BLUE),0,ss1.length(),0);
        kt.append(sn);


    }
}

package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PrimeNumbers extends AppCompatActivity {
    private AdView mAdView;
int i=0;
    int num=0;
    String primeno="";
    TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_numbers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ADDS BY GOOGLE
        mAdView=(AdView)findViewById(R.id.adView);
//        mAdView.setAdListener(new ToastAdListener(PrimeNumbers.this));
        AdRequest adRequest =new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mText= (TextView) findViewById(R.id.prime);
        for (i=1;i<=1000;i++)
        {
            int counter=0;
           for(num=i;num>=1;num--)
           {
               if(i%num==0)
               {
                   counter=counter+1;
               }
           }
           if(counter==2)
           {
               primeno=primeno+i+"  ";
           }
        }
        mText.setText(primeno);
        mText.append("\n\n");
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

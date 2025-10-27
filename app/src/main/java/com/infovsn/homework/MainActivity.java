package com.infovsn.homework;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.infovsn.homework.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontUtils.applyToActivity(this);

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});
        AdView adView = findViewById(R.id.adView);
        // Replace fixed-size banner load with anchored adaptive banner
        AdUtils.loadAdaptiveBanner(this, adView);

        Button one = (Button) findViewById(R.id.button1);
        one.setOnClickListener(this); // calling onClick() method
        Button two = (Button) findViewById(R.id.button2);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.button3);
        three.setOnClickListener(this);
        Button Four = (Button) findViewById(R.id.button4);
        Four.setOnClickListener(this);
        Button Five = (Button) findViewById(R.id.button5);
        Five.setOnClickListener(this);
        Button six = (Button) findViewById(R.id.button6);
        six.setOnClickListener(this);
        Button seven = (Button) findViewById(R.id.button7);
        seven.setOnClickListener(this);
        Button eight = (Button) findViewById(R.id.button8);
        eight.setOnClickListener(this);
        Button nine = (Button) findViewById(R.id.button9);
        nine.setOnClickListener(this);
        Button ten = (Button) findViewById(R.id.button10);
        ten.setOnClickListener(this);
        Button eleven = (Button) findViewById(R.id.button11);
        eleven.setOnClickListener(this);
        Button twelve = (Button) findViewById(R.id.button12);
        twelve.setOnClickListener(this);
        Button thirteen = (Button) findViewById(R.id.button13);
        thirteen.setOnClickListener(this);
        Button fourteen = (Button) findViewById(R.id.button14);
        fourteen.setOnClickListener(this);
        Button fifteen = (Button) findViewById(R.id.button15);
        fifteen.setOnClickListener(this);
        Button sixteen = (Button) findViewById(R.id.button16);
        sixteen.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button1) {
            Intent myIntent = new Intent(MainActivity.this, Tables.class);
            MainActivity.this.startActivity(myIntent);
        } else if (id == R.id.button2) {
            Intent i = new Intent(MainActivity.this, Addition.class);
            MainActivity.this.startActivity(i);
        } else if (id == R.id.button3) {
            Intent j = new Intent(MainActivity.this, Subtraction.class);
            MainActivity.this.startActivity(j);
        } else if (id == R.id.button4) {
            Intent k = new Intent(MainActivity.this, Multiplication.class);
            MainActivity.this.startActivity(k);
        } else if (id == R.id.button5) {
            Intent l = new Intent(MainActivity.this, Division.class);
            MainActivity.this.startActivity(l);
        } else if (id == R.id.button6) {
            Intent m = new Intent(MainActivity.this, Fraction.class);
            MainActivity.this.startActivity(m);
        } else if (id == R.id.button7) {
            Intent n = new Intent(MainActivity.this, Lcm.class);
            MainActivity.this.startActivity(n);
        } else if (id == R.id.button8) {
            Intent o = new Intent(MainActivity.this, Hcf.class);
            MainActivity.this.startActivity(o);
        } else if (id == R.id.button9) {
            Intent p = new Intent(MainActivity.this, Square.class);
            MainActivity.this.startActivity(p);
        } else if (id == R.id.button10) {
            Intent q = new Intent(MainActivity.this, Cube.class);
            MainActivity.this.startActivity(q);
        } else if (id == R.id.button11) {
            Intent r = new Intent(MainActivity.this, SquareRoot.class);
            MainActivity.this.startActivity(r);
        } else if (id == R.id.button12) {
            Intent s = new Intent(MainActivity.this, CubeRoot.class);
            MainActivity.this.startActivity(s);
        } else if (id == R.id.button13) {
            Intent t = new Intent(MainActivity.this, EvenNumbers.class);
            MainActivity.this.startActivity(t);
        } else if (id == R.id.button14) {
            Intent u = new Intent(MainActivity.this, OddNumbers.class);
            MainActivity.this.startActivity(u);
        } else if (id == R.id.button15) {
            Intent a = new Intent(MainActivity.this, PrimeNumbers.class);
            MainActivity.this.startActivity(a);
        } else if (id == R.id.button16) {
            Intent kd = new Intent(MainActivity.this, DecrOrder.class);
            MainActivity.this.startActivity(kd);
        }
    }
}

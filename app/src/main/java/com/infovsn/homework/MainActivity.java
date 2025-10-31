package com.infovsn.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // REMOVED: FontUtils.applyToActivity(this);
        // MainActivity now uses custom fonts from XML: Poppins SemiBold (header) and Inter Medium (buttons)

        // Setup MaterialToolbar as ActionBar (no Up on home)
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                // We use a centered custom TextView in the toolbar; hide default title
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            // REMOVED: Roboto Mono override - now uses Poppins SemiBold from XML
            TextView title = toolbar.findViewById(R.id.toolbarTitle);
            if (title != null) {
                title.setAllCaps(false);
                // Custom font (Poppins SemiBold) is applied via XML fontFamily attribute
                // title.setTypeface(FontUtils.getRobotoMono(this)); // REMOVED
                // title.setLetterSpacing(0.0f); // REMOVED - letterSpacing set in XML to 0.05
                title.setIncludeFontPadding(false);
            }
        }

        // Use dark status bar icons on light background for home screen
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (controller != null) {
            controller.setAppearanceLightStatusBars(true);
        }

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});
        AdView adView = findViewById(R.id.adView);
        // Replace fixed-size banner load with anchored adaptive banner
        AdUtils.loadAdaptiveBanner(this, adView);

        Button one = findViewById(R.id.button1);
        one.setOnClickListener(this);
        Button two = findViewById(R.id.button2);
        two.setOnClickListener(this);
        Button three = findViewById(R.id.button3);
        three.setOnClickListener(this);
        Button Four = findViewById(R.id.button4);
        Four.setOnClickListener(this);
        Button Five = findViewById(R.id.button5);
        Five.setOnClickListener(this);
        Button six = findViewById(R.id.button6);
        six.setOnClickListener(this);
        Button seven = findViewById(R.id.button7);
        seven.setOnClickListener(this);
        Button eight = findViewById(R.id.button8);
        eight.setOnClickListener(this);
        Button nine = findViewById(R.id.button9);
        nine.setOnClickListener(this);
        Button ten = findViewById(R.id.button10);
        ten.setOnClickListener(this);
        Button eleven = findViewById(R.id.button11);
        eleven.setOnClickListener(this);
        Button twelve = findViewById(R.id.button12);
        twelve.setOnClickListener(this);
        Button thirteen = findViewById(R.id.button13);
        thirteen.setOnClickListener(this);
        Button fourteen = findViewById(R.id.button14);
        fourteen.setOnClickListener(this);
        Button fifteen = findViewById(R.id.button15);
        fifteen.setOnClickListener(this);
        Button sixteen = findViewById(R.id.button16);
        sixteen.setOnClickListener(this);

        // REMOVED: RobotoMono font override for buttons
        // All buttons now use Inter Medium font from XML (activity_main.xml)
        // Typeface monoRegular = FontUtils.getRobotoMono(this);
        // one.setTypeface(monoRegular); ... etc. - ALL REMOVED

        // Removed marquee selection to improve button responsiveness
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

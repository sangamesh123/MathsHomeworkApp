package com.infovsn.homework;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PrimeNumbers extends AppCompatActivity {
    // Keypad buttons
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr;
    ImageButton bsp, beq;

    // Single input display
    TextView startScreen;

    private static final int MAX_INPUT_LENGTH = 16;
    private boolean clearedOnce = false;
    private boolean resetOnNextResume = false; // clear input after returning from result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_numbers);
        FontUtils.applyToActivity(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Ensure OS keyboard never shows up; we only use the in-app keypad
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        // Bind views
        startScreen = findViewById(R.id.startScreen);

        // Default value shows subtly; switch to primary color on edit
        if (startScreen != null && startScreen.length() == 0) startScreen.setText("5");

        TextView labelStart = findViewById(R.id.labelStart);
        if (startScreen != null) {
            startScreen.setOnClickListener(v -> {
                if (!clearedOnce) {
                    startScreen.setText("");
                    startScreen.setTextColor(ContextCompat.getColor(PrimeNumbers.this, R.color.textPrimary));
                    clearedOnce = true;
                }
            });
            startScreen.setLongClickable(false);
        }
        if (labelStart != null) labelStart.setOnClickListener(v -> {
            if (startScreen != null) startScreen.performClick();
        });

        // Keypad
        b1=findViewById(R.id.one);   b2=findViewById(R.id.two);   b3=findViewById(R.id.three);
        b4=findViewById(R.id.four);  b5=findViewById(R.id.five);  b6=findViewById(R.id.six);
        b7=findViewById(R.id.seven); b8=findViewById(R.id.eight); b9=findViewById(R.id.nine);
        b0=findViewById(R.id.zero);  bsp=findViewById(R.id.backspace);
        bclr=findViewById(R.id.clear); beq=findViewById(R.id.equal);

        android.view.View.OnClickListener digitListener = v -> {
            TextView target = startScreen;
            if (target == null) return;
            if (target.getText().length() >= MAX_INPUT_LENGTH) return;

            String digit = null;
            int id = v.getId();
            if (id == R.id.one)      digit = "1";
            else if (id == R.id.two)   digit = "2";
            else if (id == R.id.three) digit = "3";
            else if (id == R.id.four)  digit = "4";
            else if (id == R.id.five)  digit = "5";
            else if (id == R.id.six)   digit = "6";
            else if (id == R.id.seven) digit = "7";
            else if (id == R.id.eight) digit = "8";
            else if (id == R.id.nine)  digit = "9";
            else if (id == R.id.zero)  digit = "0";
            if (digit == null) return;

            String current = target.getText().toString();
            if ("5".equals(current) && !clearedOnce) {
                target.setText("");
                clearedOnce = true;
            }
            target.append(digit);
            target.setTextColor(ContextCompat.getColor(PrimeNumbers.this, R.color.textPrimary));
        };

        b1.setOnClickListener(digitListener);
        b2.setOnClickListener(digitListener);
        b3.setOnClickListener(digitListener);
        b4.setOnClickListener(digitListener);
        b5.setOnClickListener(digitListener);
        b6.setOnClickListener(digitListener);
        b7.setOnClickListener(digitListener);
        b8.setOnClickListener(digitListener);
        b9.setOnClickListener(digitListener);
        b0.setOnClickListener(digitListener);

        if (bclr != null) {
            bclr.setOnClickListener(v -> {
                if (startScreen != null) {
                    startScreen.setText("");
                    startScreen.setTextColor(ContextCompat.getColor(PrimeNumbers.this, R.color.textPrimary));
                    clearedOnce = true;
                }
            });
        }

        if (bsp != null) {
            bsp.setOnClickListener(v -> {
                if (startScreen == null) return;
                String s = startScreen.getText().toString();
                if (s.length() > 1) startScreen.setText(s.substring(0, s.length() - 1));
                else startScreen.setText("");
            });
        }

        if (beq != null) {
            beq.setOnClickListener(v -> goToResult());
        }
    }

    private void goToResult() {
        String sNum = startScreen != null ? startScreen.getText().toString().trim() : "";
        if (sNum.isEmpty()) sNum = "5"; // default

        // Extract only ASCII digits to avoid unexpected characters causing parse failure
        StringBuilder digitsOnly = new StringBuilder();
        for (int i = 0; i < sNum.length(); i++) {
            char c = sNum.charAt(i);
            if (c >= '0' && c <= '9') digitsOnly.append(c);
        }
        String cleaned = digitsOnly.toString();
        if (cleaned.isEmpty()) cleaned = "0";

        long n;
        try { n = Long.parseLong(cleaned); } catch (NumberFormatException e) { n = 0L; }

        Intent intent = new Intent(PrimeNumbers.this, PrimeNumbersResult.class);
        intent.putExtra("number", n);
        resetOnNextResume = true; // mark for clearing when user comes back
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resetOnNextResume) {
            resetOnNextResume = false;
            resetInputToDefault();
        }
    }

    private void resetInputToDefault() {
        if (startScreen != null) startScreen.setText("5");
        clearedOnce = false;
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

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

public class EvenNumbers extends AppCompatActivity {

    // Keypad buttons
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr;
    ImageButton bsp, beq;

    // Input displays
    TextView startScreen, endScreen;

    private static final int MAX_INPUT_LENGTH = 16;
    private boolean editingStart = true; // which input the keypad writes to
    private boolean clearedStartOnce = false;
    private boolean clearedEndOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_numbers);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FontUtils.applyToActivity(this);

        // Ensure OS keyboard never shows up; we only use the in-app keypad
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        // Bind views
        startScreen = findViewById(R.id.startScreen);
        endScreen = findViewById(R.id.endScreen);

        // Show defaults with a subtle look (layout uses @color/textSecondary)
        if (startScreen != null && startScreen.length() == 0) startScreen.setText("1");
        if (endScreen != null && endScreen.length() == 0) endScreen.setText("500");

        // Allow switching active input by tapping the fields or labels; clear on first tap
        TextView labelStart = findViewById(R.id.labelStart);
        TextView labelEnd = findViewById(R.id.labelEnd);

        if (startScreen != null) {
            startScreen.setOnClickListener(v -> {
                editingStart = true;
                // Clear once when user starts editing
                if (!clearedStartOnce) {
                    startScreen.setText("");
                    startScreen.setTextColor(ContextCompat.getColor(EvenNumbers.this, R.color.textPrimary));
                    clearedStartOnce = true;
                }
            });
            startScreen.setLongClickable(false);
        }
        if (endScreen != null) {
            endScreen.setOnClickListener(v -> {
                editingStart = false;
                if (!clearedEndOnce) {
                    endScreen.setText("");
                    endScreen.setTextColor(ContextCompat.getColor(EvenNumbers.this, R.color.textPrimary));
                    clearedEndOnce = true;
                }
            });
            endScreen.setLongClickable(false);
        }
        if (labelStart != null) labelStart.setOnClickListener(v -> {
            if (startScreen != null) startScreen.performClick();
        });
        if (labelEnd != null) labelEnd.setOnClickListener(v -> {
            if (endScreen != null) endScreen.performClick();
        });

        // Keypad
        b1=findViewById(R.id.one);   b2=findViewById(R.id.two);   b3=findViewById(R.id.three);
        b4=findViewById(R.id.four);  b5=findViewById(R.id.five);  b6=findViewById(R.id.six);
        b7=findViewById(R.id.seven); b8=findViewById(R.id.eight); b9=findViewById(R.id.nine);
        b0=findViewById(R.id.zero);  bsp=findViewById(R.id.backspace);
        bclr=findViewById(R.id.clear); beq=findViewById(R.id.equal);

        android.view.View.OnClickListener digitListener = v -> {
            TextView target = editingStart ? startScreen : endScreen;
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

            // If default is visible, replace it with first digit (safety), and switch to primary color
            String current = target.getText().toString();
            if (target == startScreen && ("1".equals(current) && !clearedStartOnce)) {
                target.setText("");
                clearedStartOnce = true;
            } else if (target == endScreen && ("500".equals(current) && !clearedEndOnce)) {
                target.setText("");
                clearedEndOnce = true;
            }
            target.append(digit);
            target.setTextColor(ContextCompat.getColor(EvenNumbers.this, R.color.textPrimary));
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
                TextView target = editingStart ? startScreen : endScreen;
                if (target != null) {
                    target.setText("");
                    target.setTextColor(ContextCompat.getColor(EvenNumbers.this, R.color.textPrimary));
                    if (target == startScreen) clearedStartOnce = true; else clearedEndOnce = true;
                }
            });
        }

        if (bsp != null) {
            bsp.setOnClickListener(v -> {
                TextView target = editingStart ? startScreen : endScreen;
                if (target == null) return;
                String s = target.getText().toString();
                if (s.length() > 1) target.setText(s.substring(0, s.length() - 1));
                else target.setText("");
            });
        }

        if (beq != null) {
            beq.setOnClickListener(v -> goToResult());
        }
    }

    private void goToResult() {
        String sStart = startScreen != null ? startScreen.getText().toString().trim() : "";
        String sEnd = endScreen != null ? endScreen.getText().toString().trim() : "";

        if (sStart.isEmpty()) sStart = "1"; // default
        if (sEnd.isEmpty())   sEnd   = "500"; // default

        int startVal;
        int endVal;
        try { startVal = Integer.parseInt(sStart); } catch (NumberFormatException e) { startVal = 1; }
        try { endVal = Integer.parseInt(sEnd); }   catch (NumberFormatException e) { endVal = 500; }

        if (startVal > endVal) { int tmp = startVal; startVal = endVal; endVal = tmp; }

        Intent intent = new Intent(EvenNumbers.this, EvenNumbersResult.class);
        intent.putExtra("start", startVal);
        intent.putExtra("end", endVal);
        startActivity(intent);
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

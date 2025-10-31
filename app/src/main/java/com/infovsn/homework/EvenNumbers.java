package com.infovsn.homework;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.core.content.ContextCompat;

public class EvenNumbers extends BaseActivity {
    TextView startScreen,endScreen;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr; // Removed add button; not needed for range
    ImageButton bsp, beq;

    private static final int MAX_INPUT_LENGTH = 16;
    private boolean clearedStartOnce = false;
    private boolean clearedEndOnce = false;
    private boolean resetOnNextResume = false;
    private boolean editingStart = true; // which input the keypad writes to

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_numbers);
        FontUtils.applyToActivity(this);
        // Setup MaterialToolbar navigation
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
            // Title is now set via custom TextView in XML layout
        }

        startScreen = findViewById(R.id.startScreen);
        endScreen = findViewById(R.id.endScreen);
        startScreen.setMovementMethod(new ScrollingMovementMethod());
        endScreen.setMovementMethod(new ScrollingMovementMethod());
        // Set default values
        if (startScreen != null && startScreen.length() == 0) startScreen.setText("1");
        if (endScreen != null && endScreen.length() == 0) endScreen.setText("500");

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
        bsp=(ImageButton) findViewById(R.id.backspace);
        bclr=(Button) findViewById(R.id.clear);
        beq=(ImageButton) findViewById(R.id.equal);

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

        View.OnClickListener digitListener = v -> {
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

        bclr.setOnClickListener(v -> {
            TextView target = editingStart ? startScreen : endScreen;
            if (target != null) {
                target.setText("");
                target.setTextColor(ContextCompat.getColor(EvenNumbers.this, R.color.textPrimary));
                if (target == startScreen) clearedStartOnce = true; else clearedEndOnce = true;
            }
        });
        bsp.setOnClickListener(v -> {
            TextView target = editingStart ? startScreen : endScreen;
            if (target == null) return;
            String s = target.getText().toString();
            if (s.length() > 1) target.setText(s.substring(0, s.length() - 1));
            else target.setText("");
        });
        beq.setOnClickListener(v -> {
            String s = startScreen.getText().toString();
            String e = endScreen.getText().toString();
            if (s.isEmpty() || e.isEmpty()) {
                endScreen.setError("Enter start and end");
                return;
            }
            try {
                int sv = Integer.parseInt(s);
                int ev = Integer.parseInt(e);
                if (sv > ev) { int tmp = sv; sv = ev; ev = tmp; }
                Intent intent = new Intent(EvenNumbers.this, EvenNumbersResult.class);
                intent.putExtra("start", sv);
                intent.putExtra("end", ev);
                resetOnNextResume = true;
                startActivity(intent);
            } catch (NumberFormatException ex) {
                endScreen.setError("Invalid number");
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resetOnNextResume) {
            resetOnNextResume = false;
            resetInputsToDefaults();
        }
    }

    private void resetInputsToDefaults() {
        if (startScreen != null) startScreen.setText("1");
        if (endScreen != null) endScreen.setText("500");
        clearedStartOnce = false;
        clearedEndOnce = false;
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

package com.infovsn.homework;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

public class Tables extends AppCompatActivity {
    private AdView mAdView;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr; // badd & back removed
    TextView et;
    ImageButton bsp, beq;
    private static final int MAX_INPUT_LENGTH = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
        bsp=(ImageButton) findViewById(R.id.backspace);
        bclr=(Button) findViewById(R.id.clear);
        beq=(ImageButton) findViewById(R.id.equal);
        et=(TextView) findViewById(R.id.tabl);
        et.setMovementMethod(new ScrollingMovementMethod());
        et.setTypeface(FontUtils.getRobotoMono(this));
        et.setLetterSpacing(0f);

        View.OnClickListener digitListener = v -> {
            if (et.getText().length() >= MAX_INPUT_LENGTH) return;
            int id = v.getId();
            if (id == R.id.one)      et.append("1");
            else if (id == R.id.two)   et.append("2");
            else if (id == R.id.three) et.append("3");
            else if (id == R.id.four)  et.append("4");
            else if (id == R.id.five)  et.append("5");
            else if (id == R.id.six)   et.append("6");
            else if (id == R.id.seven) et.append("7");
            else if (id == R.id.eight) et.append("8");
            else if (id == R.id.nine)  et.append("9");
            else if (id == R.id.zero)  et.append("0");
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

        bclr.setOnClickListener(v -> et.setText(null));

        bsp.setOnClickListener(v -> {
            String smp = et.getText().toString();
            if (smp.length() > 1) {
                smp = smp.substring(0, smp.length() - 1);
                et.setText(smp);
            } else if (smp.length() <= 1) {
                et.setText(null);
            }
        });

        beq.setOnClickListener(v -> {
            String input = et.getText().toString();
            if (input.isEmpty()) {
                et.setError("Please enter a number");
                return;
            }
            try {
                Long.parseLong(input);
            } catch (NumberFormatException e) {
                et.setError("Invalid number");
                return;
            }
            Intent intent = new Intent(Tables.this, TableDisplayActivity.class);
            intent.putExtra("tableNumber", input);
            Tables.this.startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear the input field when returning to this screen
        if (et != null) {
            et.setText("");
        }
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

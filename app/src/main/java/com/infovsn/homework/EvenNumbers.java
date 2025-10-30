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

public class EvenNumbers extends BaseActivity {
    TextView startScreen,endScreen;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bclr; // Removed add button; not needed for range
    ImageButton bsp, beq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_numbers);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FontUtils.applyToActivity(this);

        startScreen = findViewById(R.id.startScreen);
        endScreen = findViewById(R.id.endScreen);
        startScreen.setMovementMethod(new ScrollingMovementMethod());
        endScreen.setMovementMethod(new ScrollingMovementMethod());

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

        View.OnClickListener digitListener = v -> {
            int id = v.getId();
            TextView target = (endScreen.hasFocus() ? endScreen : startScreen);
            if (id == R.id.one)      target.append("1");
            else if (id == R.id.two)   target.append("2");
            else if (id == R.id.three) target.append("3");
            else if (id == R.id.four)  target.append("4");
            else if (id == R.id.five)  target.append("5");
            else if (id == R.id.six)   target.append("6");
            else if (id == R.id.seven) target.append("7");
            else if (id == R.id.eight) target.append("8");
            else if (id == R.id.nine)  target.append("9");
            else if (id == R.id.zero)  target.append("0");
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

        bclr.setOnClickListener(v -> { startScreen.setText(""); endScreen.setText(""); });
        bsp.setOnClickListener(v -> {
            TextView target = (endScreen.hasFocus() ? endScreen : startScreen);
            CharSequence t = target.getText();
            if (t != null && t.length() > 0) {
                target.setText(t.subSequence(0, t.length()-1));
            }
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
                android.content.Intent intent = new android.content.Intent(EvenNumbers.this, EvenNumbersResult.class);
                intent.putExtra("start", sv);
                intent.putExtra("end", ev);
                startActivity(intent);
            } catch (NumberFormatException ex) {
                endScreen.setError("Invalid number");
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

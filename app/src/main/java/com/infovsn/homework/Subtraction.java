package com.infovsn.homework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Subtraction extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bclr,beq,back;
    TextView et;
    ImageButton bsp;
    TextView at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b1 = findViewById(R.id.one);
        b2 = findViewById(R.id.two);
        b3 = findViewById(R.id.three);
        b4 = findViewById(R.id.four);
        b5 = findViewById(R.id.five);
        b6 = findViewById(R.id.six);
        b7 = findViewById(R.id.seven);
        b8 = findViewById(R.id.eight);
        b9 = findViewById(R.id.nine);
        b0 = findViewById(R.id.zero);
        badd = findViewById(R.id.add);
        bsp = findViewById(R.id.backspace);
        bclr = findViewById(R.id.clear);
        beq = findViewById(R.id.equal);
        et = findViewById(R.id.txtScreen);
        et.setMovementMethod(new ScrollingMovementMethod());

        View.OnClickListener digitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                et.setText(et.getText().toString() + btn.getText().toString());
            }
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

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(et.getText().toString() + "\n- ");
            }
        });

        bclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(null);
            }
        });

        bsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smp = et.getText().toString();
                if (smp.length() > 1) {
                    char last = smp.charAt(smp.length() - 1);
                    if (last == ' ') {
                        if (smp.length() >= 2) {
                            smp = smp.substring(0, smp.length() - 2);
                        } else {
                            smp = smp.substring(0, smp.length() - 1);
                        }
                    } else {
                        smp = smp.substring(0, smp.length() - 1);
                    }
                    et.setText(smp);
                } else {
                    et.setText(null);
                }
            }
        });

        beq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch to result layout and compute
                setContentView(R.layout.added);
                at = findViewById(R.id.txtScr);
                at.setMovementMethod(new ScrollingMovementMethod());

                back = findViewById(R.id.bck);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                String txt = et.getText().toString();
                String[] lines = txt.split("\\n");
                boolean error = false;
                long result = 0L;

                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    if (i == 0) {
                        if (line.length() == 0) line = "0";
                        if (line.length() > 15) {
                            at.setText("Maximum digits(15) exceeded\n");
                            error = true;
                            break;
                        }
                        try {
                            result = Long.parseLong(line);
                        } catch (NumberFormatException ex) {
                            at.setText("Invalid input\n");
                            error = true;
                            break;
                        }
                    } else {
                        if (line.startsWith("- ")) line = line.substring(2);
                        if (line.length() == 0) line = "0";
                        if (line.length() > 15) {
                            at.setText("Maximum digits(15) exceeded\n");
                            error = true;
                            break;
                        }
                        try {
                            long val = Long.parseLong(line);
                            result -= val;
                        } catch (NumberFormatException ex) {
                            at.setText("Invalid input\n");
                            error = true;
                            break;
                        }
                    }
                }

                if (!error) {
                    StringBuilder sb = new StringBuilder();
                    for (String l : lines) sb.append(l).append('\n');
                    sb.append('\n').append("Subtraction : ").append(result);
                    at.setText(sb.toString());
                }
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

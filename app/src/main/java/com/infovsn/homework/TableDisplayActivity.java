package com.infovsn.homework;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.graphics.Typeface;

public class TableDisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        TextView at = findViewById(R.id.txtScr);
        at.setMovementMethod(new ScrollingMovementMethod());
        Typeface tf = loadRobotoMonoFromAssets();
        if (tf != null) {
            at.setTypeface(tf);
        } else {
            at.setTypeface(Typeface.MONOSPACE);
        }
        at.setLetterSpacing(0f);
        String tableNumberStr = getIntent().getStringExtra("tableNumber");
        if (tableNumberStr == null || tableNumberStr.isEmpty()) {
            at.setText("No number provided");
            return;
        }
        long myNum = 0;
        String table = "";
        try {
            myNum = Long.parseLong(tableNumberStr);
            if (myNum > 99999) {
                at.setTextSize(26);
            }
            for (int i = 1; i <= 10; i++) {
                if (i == 10) {
                    table = table + "\n" + myNum + "  x  10  =   " + myNum * i;
                } else {
                    table = table + "\n" + myNum + "  x   " + i + "   =   " + myNum * i;
                }
            }
            at.setText(table);
        } catch (NumberFormatException nfe) {
            at.setText("Invalid number");
        }
    }

    private Typeface loadRobotoMonoFromAssets() {
        try {
            return Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
        } catch (Exception e) {
            return null;
        }
    }
}

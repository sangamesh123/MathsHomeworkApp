package com.infovsn.homework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LcmTableView extends View {
    private final Paint primesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint numbersPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Rect textBounds = new Rect();

    private List<List<Long>> rows = new ArrayList<>(); // includes initial row and final 1..1 row
    private List<Long> primes = new ArrayList<>();     // size = rows.size()-1

    // layout metrics
    private float padding;
    private float colGap;      // gap between numbers columns
    private float primeGap;    // gap between primes and vertical line
    private float numbersStartGap; // gap after vertical line before numbers text
    private float textSize;
    private float outerInset;  // extra spacing before primes for nicer look

    private float[] colMaxWidths = new float[0];

    public LcmTableView(Context context) { this(context, null); }
    public LcmTableView(Context context, AttributeSet attrs) { this(context, attrs, 0); }
    public LcmTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        textSize = sp(22);
        padding = dp(12);           // a little more breathing room
        colGap = dp(28);
        primeGap = dp(10);          // primes closer to the divider
        numbersStartGap = dp(16);   // more space between divider and numbers text
        outerInset = dp(6);         // overall left breathing room before primes

        primesPaint.setTextSize(textSize);
        primesPaint.setColor(0xFF9C2780);
        primesPaint.setTypeface(FontUtils.getRobotoMono(getContext()));

        numbersPaint.setTextSize(textSize);
        numbersPaint.setColor(0xFF222222);
        numbersPaint.setTypeface(FontUtils.getRobotoMono(getContext()));

        linePaint.setColor(0xFF222222);
        linePaint.setStrokeWidth(dp(2));
    }

    public void setData(List<List<Long>> rowsNumbers, List<Long> primesUsed) {
        this.rows = rowsNumbers != null ? rowsNumbers : new ArrayList<>();
        this.primes = primesUsed != null ? primesUsed : new ArrayList<>();
        // Precompute column max widths for alignment
        computeColumnWidths();
        requestLayout();
        invalidate();
    }

    private void computeColumnWidths() {
        if (rows == null || rows.isEmpty()) { colMaxWidths = new float[0]; return; }
        int cols = rows.get(0).size();
        colMaxWidths = new float[cols];
        for (int c = 0; c < cols; c++) colMaxWidths[c] = 0f;
        for (List<Long> r : rows) {
            for (int c = 0; c < cols && c < r.size(); c++) {
                String s = String.valueOf(r.get(c));
                float w = numbersPaint.measureText(s);
                if (w > colMaxWidths[c]) colMaxWidths[c] = w;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Desired height based on number of rows
        int rowsCount = rows != null ? rows.size() : 0;
        float rowHeight = getRowHeight();
        int desiredHeight = (int) (padding + rowsCount * rowHeight + padding);

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int h;
        if (hMode == MeasureSpec.EXACTLY) h = hSize;
        else if (hMode == MeasureSpec.AT_MOST) h = Math.min(desiredHeight, hSize);
        else h = desiredHeight;
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        if (rows == null || rows.isEmpty()) return;

        // compute x for primes column max width
        float maxPrimeW = 0f;
        for (int i = 0; i < primes.size(); i++) {
            String s = String.valueOf(primes.get(i));
            float w = primesPaint.measureText(s);
            if (w > maxPrimeW) maxPrimeW = w;
        }
        float xPrimeStart = padding + outerInset;
        float xLine = xPrimeStart + maxPrimeW + primeGap; // vertical line x
        float xNumbersStart = xLine + numbersStartGap;

        // Precompute total numbers width (fixed for all rows)
        float numbersWidth = 0f;
        for (int i = 0; i < colMaxWidths.length; i++) {
            numbersWidth += colMaxWidths[i];
            if (i < colMaxWidths.length - 1) numbersWidth += colGap;
        }
        float xNumbersEnd = xNumbersStart + numbersWidth;

        float rowHeight = getRowHeight();
        float baselineOffset = getBaselineOffset(numbersPaint);

        // Determine vertical span to draw the vertical line exactly covering the rows area
        float firstBaseline = padding + baselineOffset;
        float topFirst = firstBaseline + numbersPaint.getFontMetrics().ascent;
        float lastBaseline = padding + (rows.size() - 1) * rowHeight + baselineOffset;
        float bottomLast = lastBaseline + numbersPaint.getFontMetrics().descent;

        // Draw vertical line (cut at end of calculation)
        c.drawLine(xLine, topFirst, xLine, bottomLast, linePaint);

        // Draw rows
        for (int r = 0; r < rows.size(); r++) {
            float baseY = padding + r * rowHeight + baselineOffset;

            // Draw prime for rows after the first (align to corresponding row)
            if (r > 0 && r - 1 < primes.size()) {
                String p = String.valueOf(primes.get(r - 1));
                c.drawText(p, xPrimeStart, baseY, primesPaint);
            }

            // Draw numbers row cells using fixed column widths
            List<Long> nums = rows.get(r);
            float x = xNumbersStart;
            for (int i = 0; i < nums.size(); i++) {
                String s = String.valueOf(nums.get(i));
                c.drawText(s, x, baseY, numbersPaint);
                float wCol = (i < colMaxWidths.length ? colMaxWidths[i] : numbersPaint.measureText(s));
                x += wCol + colGap;
            }

            // Draw a horizontal line under all rows except the last
            if (r < rows.size() - 1) {
                float y = baseY + numbersPaint.getFontMetrics().descent + dp(6);
                // Start exactly at the vertical divider; end at the end of numbers area
                c.drawLine(xLine, y, xNumbersEnd, y, linePaint);
            }
        }
    }

    private float getRowHeight() {
        Paint.FontMetrics fm = numbersPaint.getFontMetrics();
        return (fm.descent - fm.ascent) + dp(10); // include extra spacing
    }

    private float getBaselineOffset(Paint p) {
        Paint.FontMetrics fm = p.getFontMetrics();
        return -fm.ascent; // distance from top of row to baseline
    }

    private float dp(float v) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, getResources().getDisplayMetrics());
    }
    private float sp(float v) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, v, getResources().getDisplayMetrics());
    }
}

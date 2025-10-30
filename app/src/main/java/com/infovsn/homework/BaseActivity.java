package com.infovsn.homework;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Draw behind system bars
        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        // Make bars transparent; theme also sets these, but ensure at runtime
        window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
        window.setNavigationBarColor(android.graphics.Color.TRANSPARENT);

        // Prefer light navigation bar icons (dark content) on light backgrounds
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
        if (controller != null) {
            // Status bar over app bar (red) needs light icons for contrast
            controller.setAppearanceLightStatusBars(false);
            // Navigation bar over light background needs dark icons
            controller.setAppearanceLightNavigationBars(true);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        // Apply system bar insets as padding to the root view of the activity content
        final View contentRootContainer = findViewById(android.R.id.content);
        if (!(contentRootContainer instanceof android.view.ViewGroup)) return;
        android.view.ViewGroup vg = (android.view.ViewGroup) contentRootContainer;
        if (vg.getChildCount() == 0) return;
        final View root = vg.getChildAt(0);

        // Try to find a MaterialToolbar by conventional id
        final View toolbarView = findViewById(R.id.toolbar);
        final MaterialToolbar materialToolbar = (toolbarView instanceof MaterialToolbar) ? (MaterialToolbar) toolbarView : null;

        // If we have a toolbar and no ActionBar, provide default Up navigation icon/behavior
        if (materialToolbar != null && getSupportActionBar() == null) {
            if (materialToolbar.getNavigationIcon() == null) {
                materialToolbar.setNavigationIcon(R.drawable.ic_back_arrow_white);
            }
            materialToolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        final int originalLeft = root.getPaddingLeft();
        final int originalTop = root.getPaddingTop();
        final int originalRight = root.getPaddingRight();
        final int originalBottom = root.getPaddingBottom();

        // Track original toolbar paddings if present
        final int tbOrigLeft = materialToolbar != null ? materialToolbar.getPaddingLeft() : 0;
        final int tbOrigTop = materialToolbar != null ? materialToolbar.getPaddingTop() : 0;
        final int tbOrigRight = materialToolbar != null ? materialToolbar.getPaddingRight() : 0;
        final int tbOrigBottom = materialToolbar != null ? materialToolbar.getPaddingBottom() : 0;

        // Resolve the themed action bar size in pixels (for classic ActionBar screens)
        final int actionBarSizePx;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(androidx.appcompat.R.attr.actionBarSize, tv, true)) {
            actionBarSizePx = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        } else {
            actionBarSizePx = 0;
        }

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply top inset to toolbar if present.
            if (materialToolbar != null) {
                materialToolbar.setPadding(tbOrigLeft + sys.left, tbOrigTop + sys.top, tbOrigRight + sys.right, tbOrigBottom);
            }

            // Decide whether to apply top padding to root:
            // - If classic ActionBar present and no toolbar: push content below ActionBar height.
            // - If neither ActionBar nor toolbar: apply status bar inset to root.
            ActionBar ab = getSupportActionBar();
            int topPadding = originalTop;
            if (ab != null && materialToolbar == null) {
                topPadding = originalTop + actionBarSizePx; // push below ActionBar
            } else if (ab == null && materialToolbar == null) {
                topPadding = originalTop + sys.top; // no bar: use status bar inset
            }

            v.setPadding(
                    originalLeft + sys.left,
                    topPadding,
                    originalRight + sys.right,
                    originalBottom + sys.bottom
            );
            return insets;
        });
        // Request insets now
        ViewCompat.requestApplyInsets(root);
    }
}

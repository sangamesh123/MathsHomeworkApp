package com.infovsn.homework;

import android.app.Activity;

import java.lang.reflect.Method;

/**
 * Small compatibility helper that attempts to call Material's Edge-to-Edge helper if it's
 * available on the classpath. This uses reflection and is safe to call on older
 * library versions / SDKs - it will silently no-op if the class/method isn't present.
 */
public final class EdgeToEdgeUtil {

    private EdgeToEdgeUtil() { /* no instances */ }

    public static void enableEdgeToEdgeIfAvailable(Activity activity) {
        if (activity == null) return;
        try {
            // Try to load Material's EdgeToEdge class
            Class<?> cls = Class.forName("com.google.android.material.edge.EdgeToEdge");
            // First try a method named "enable" with Activity parameter
            try {
                Method m = cls.getMethod("enable", Activity.class);
                m.invoke(null, activity);
            } catch (NoSuchMethodException ignored) {
            }
            // Then try a method named "enableEdgeToEdge"
            try {
                Method m = cls.getMethod("enableEdgeToEdge", Activity.class);
                m.invoke(null, activity);
            } catch (NoSuchMethodException ignored) {
            }
        } catch (Throwable ignored) {
            // Class not found or invocation failed: silently ignore for compatibility
        }
    }
}

package com.bhaskar.fonticon;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import java.io.File;

public class FontIconTypefaceHolder {
    private static Typeface sTypeface;

    public static Typeface getTypeface() {
        if (sTypeface == null) {
            throw new IllegalStateException();
        }

        return sTypeface;
    }

    public static void setTypeface(Typeface typeface) {
        sTypeface = typeface;
    }

    public static void init(AssetManager assets, String path) {
        try {
            sTypeface = Typeface.createFromAsset(assets, path);
        } catch (Exception e) {
            Log.e("FontIconTypefaceHolder", "init: " + e.getMessage());
        }
    }

    public static void init(File file) {
        sTypeface = Typeface.createFromFile(file);
    }
}

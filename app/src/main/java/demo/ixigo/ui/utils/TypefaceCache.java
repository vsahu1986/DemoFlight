package demo.ixigo.ui.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TypefaceCache {
    public static final int MEDUIM = 0;
    public static final int REGULAR = 1;

    private static final LruCache<String, Typeface> mTypefaceCache;

    static {
        mTypefaceCache = new LruCache<String, Typeface>(2);
    }

    protected static Typeface getTypeface(Context context) {
        return getTypeface(context, Typeface.NORMAL);
    }

    public static Typeface getTypeface(Context context, int typefacetype) {
        if (context == null) return null;
        String typefaceName = "";
        switch (typefacetype) {
            case MEDUIM:
                typefaceName = "SansMedium.ttf";
                break;
            case REGULAR:
                typefaceName = "SansRegular.ttf";
                break;

        }

        synchronized (mTypefaceCache) {

            if (mTypefaceCache.get(typefaceName) != null) {
                Typeface typeface = mTypefaceCache.get(typefaceName);
                if (typeface != null) {
                    return typeface;
                }
            }

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), typefaceName);
            mTypefaceCache.put(typefaceName, typeface);
            return typeface;

        }

    }

    /*
     * sets the typeface for a TextView (or TextView descendant such as EditText
     * or Button) based on the passed attributes, defaults to normal typeface
     */
    protected static void setCustomTypeface(Context context, TextView view, int typefacetype) {
        if (context == null || view == null) return;

        // skip at design-time
        if (view.isInEditMode()) return;

        Typeface typeface = getTypeface(context, typefacetype);
        if (typeface != null) {
            view.setTypeface(typeface);
        }
    }

    /**
     * @param pContext
     * @return true if font is applied, false otherwise
     */
    public static boolean changeDeviceTypeface(Context pContext, String pStaticFieldName, int typefacetype) {
        try {
            Field StaticField = Typeface.class.getDeclaredField(pStaticFieldName);
            StaticField.setAccessible(true);
            Typeface newTypeface = getTypeface(pContext, typefacetype);
            StaticField.set(null, newTypeface);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param pView
     * @param pContext
     * @return
     */
    public static boolean setCustomFont(View pView, Context pContext, int typefacetype) {

        try {
            Typeface tf = getTypeface(pContext, typefacetype);
            if (pView instanceof TextView) {
                ((TextView) pView).setTypeface(tf);
            } else {
                ((Button) pView).setTypeface(tf);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
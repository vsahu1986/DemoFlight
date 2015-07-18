package demo.ixigo.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import demo.ixigo.ui.utils.TypefaceCache;


public class RegularTextView extends TextView {

    public RegularTextView(Context context) {
        super(context);
        setCustomFont(context);
    }

    public RegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public RegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context);
    }

    public void setCustomFont(Context ctx) {
        if (isInEditMode()) return ;
        Typeface tf = null;
        try {
            tf = TypefaceCache.getTypeface(ctx, TypefaceCache.REGULAR);
        } catch (Exception e) {
        }

        setTypeface(tf);
    }

    @Override
    public boolean onSetAlpha(int alpha) {
        setTextColor(getTextColors().withAlpha(alpha));
        setHintTextColor(getHintTextColors().withAlpha(alpha));
        setLinkTextColor(getLinkTextColors().withAlpha(alpha));
        return true;
    }
}
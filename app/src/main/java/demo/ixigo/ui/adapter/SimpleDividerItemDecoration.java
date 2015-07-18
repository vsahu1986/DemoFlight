package demo.ixigo.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import demo.ixigo.R;


public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    int mDividerHeight;
    private Drawable mDividerDrawable;
    private boolean mOverlap;

    @SuppressWarnings("NewApi")
    public SimpleDividerItemDecoration(Context context) {
        Resources res = context.getResources();
        mDividerDrawable = res.getDrawable(R.drawable.line_divider);
        mDividerHeight = mDividerDrawable.getIntrinsicHeight();
        mOverlap = true;
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        final float yPositionThreshold = (mOverlap) ? 1.0f : (mDividerHeight + 1.0f); // [px]
        final float zPositionThreshold = 1.0f; // [px]

        if (childCount == 0) {
            return;
        }

        int savedCount = c.save(Canvas.CLIP_SAVE_FLAG);

        c.clipRect(parent.getLeft() + parent.getPaddingLeft(), parent.getTop() + parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getBottom() + parent.getPaddingBottom());

        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final View nextChild = parent.getChildAt(i + 1);

            if ((child.getVisibility() != View.VISIBLE) || (nextChild.getVisibility() != View.VISIBLE)) {
                continue;
            }

            // check if the next item is placed at the bottom
            final float childBottom = child.getBottom() + ViewCompat.getTranslationY(child);
            final float nextChildTop = nextChild.getTop() + ViewCompat.getTranslationY(nextChild);

            if (!(Math.abs(nextChildTop - childBottom) < yPositionThreshold)) {
                continue;
            }

            // check if the next item is placed on the same plane
            final float childZ = ViewCompat.getTranslationZ(child) + ViewCompat.getElevation(child);
            final float nextChildZ = ViewCompat.getTranslationZ(nextChild) + ViewCompat.getElevation(nextChild);

            if (!(Math.abs(nextChildZ - childZ) < zPositionThreshold)) {
                continue;
            }

            final float childAlpha = ViewCompat.getAlpha(child);
            final float nextChildAlpha = ViewCompat.getAlpha(nextChild);

            final int tx = (int) (ViewCompat.getTranslationX(child) + 0.5f);
            final int ty = (int) (ViewCompat.getTranslationY(child) + 0.5f);
            final int left = child.getLeft();
            final int right = child.getRight();
            final int top = child.getBottom();
            final int bottom = top + mDividerHeight;

            mDividerDrawable.setAlpha((int) ((0.5f * 255) * (childAlpha + nextChildAlpha) + 0.5f));
            mDividerDrawable.setBounds(left + tx, top + ty, right + tx, bottom + ty);
            mDividerDrawable.draw(c);
        }

        c.restoreToCount(savedCount);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOverlap) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, mDividerHeight);
        }
    }
}
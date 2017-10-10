package com.zhuxiangqing.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhu on 2017/10/10.
 *
 */

public class ProgressView extends View {
    private Paint mEmptyPaint;
    private Paint mFullPaint;
    private static int EMPTY_COLOR_DEFAULT = Color.parseColor("#C5BAB9");
    private static int FULL_COLOR_DEFAULT = Color.parseColor("#B5C874");
    private int mEmptyColor = EMPTY_COLOR_DEFAULT;
    private int mFullColor = FULL_COLOR_DEFAULT;
    private int maxValue = 18;
    private int currentValue = 2;
    private Path[] paths;
    private float mLRPadding = 20;
    private float mTBPadding = 20;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        maxValue = obtainStyledAttributes.getInt(R.styleable.ProgressView_maxValue, 20);
        currentValue = obtainStyledAttributes.getInt(R.styleable.ProgressView_currentValue,0);
        mFullColor = obtainStyledAttributes.getColor(R.styleable.ProgressView_fullColor,FULL_COLOR_DEFAULT);
        mEmptyColor = obtainStyledAttributes.getColor(R.styleable.ProgressView_emptyColor,EMPTY_COLOR_DEFAULT);
        mEmptyPaint = makePaint(mEmptyColor);
        mFullPaint = makePaint(mFullColor);
        paths = new Path[maxValue];
        int length = paths.length;
        for (int i = 0; i < length; i++) {
            paths[i] = new Path();
        }
    }




    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        invalidate();
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        invalidate();
    }

    private Path fillPathByIndex(Path path, int index, float singleWidth, float distance) {
        float y = getMeasuredHeight() - calculateY(index, singleWidth, distance);
        path.moveTo(mLRPadding + singleWidth / 2, y);
        path.lineTo(getWidth() - mLRPadding - singleWidth / 2, y);
        return path;
    }

    private float calculateY(int index, float singleWidth, float distance) {
        return mTBPadding + (index + 1) * singleWidth - singleWidth / 2 + index * distance;
    }


    private Paint makePaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    private void setPaintWidth(float width, Paint... paint) {
        for (Paint aPaint : paint) {
            aPaint.setStrokeWidth(width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float maxValueNum = (float) (maxValue - 1) / 4 + maxValue;
        final float paintWidth = (getMeasuredHeight() - mTBPadding * 2) / maxValueNum;
        final float distance = paintWidth / 4;
        setPaintWidth(paintWidth, mEmptyPaint, mFullPaint);
        for (int i = 0; i < maxValue; i++) {
            fillPathByIndex(paths[i], i, paintWidth, distance);
            if (i < currentValue) {
                canvas.drawPath(paths[i], mFullPaint);
            } else {
                canvas.drawPath(paths[i], mEmptyPaint);
            }
        }
    }
}

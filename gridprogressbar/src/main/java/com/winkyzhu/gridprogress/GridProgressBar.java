package com.winkyzhu.gridprogress;

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
 */

public class GridProgressBar extends View {
    private Paint mEmptyPaint;
    private Paint mProgressPaint;
    private static int EMPTY_COLOR_DEFAULT = Color.parseColor("#C5BAB9");
    private static int FULL_COLOR_DEFAULT = Color.parseColor("#B5C874");
    private int emptyColor = EMPTY_COLOR_DEFAULT;
    private int progressColor = FULL_COLOR_DEFAULT;
    private static int PADDING_DEFAULT = 20;
    private int maxValue = 18;
    private int progress = 2;
    private Path[] paths;
    private int leftPadding;
    private int rightPadding;
    private int topPadding;
    private int bottomPadding;


    public GridProgressBar(Context context) {
        this(context, null);
    }

    public GridProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.GridProgressBar);
        maxValue = obtainStyledAttributes.getInt(R.styleable.GridProgressBar_max, 20);
        progress = obtainStyledAttributes.getInt(R.styleable.GridProgressBar_progress, 0);
        progressColor = obtainStyledAttributes.getColor(R.styleable.GridProgressBar_progressColor, FULL_COLOR_DEFAULT);
        emptyColor = obtainStyledAttributes.getColor(R.styleable.GridProgressBar_emptyColor, EMPTY_COLOR_DEFAULT);
        int gridStyle = obtainStyledAttributes.getInt(R.styleable.GridProgressBar_gridStyle, 0);
        obtainStyledAttributes.recycle();
        leftPadding = getPaddingLeft() > 0 ? getPaddingLeft() : PADDING_DEFAULT;
        rightPadding = getPaddingRight()>0?getPaddingRight():PADDING_DEFAULT;
        topPadding = getPaddingTop()>0?getPaddingTop():PADDING_DEFAULT;
        bottomPadding = getPaddingBottom()>0?getPaddingBottom():PADDING_DEFAULT;
        mEmptyPaint = makePaint(emptyColor);
        mProgressPaint = makePaint(progressColor);
        switch (gridStyle) {
            case 0:
                setPaintStrokeCap(Paint.Cap.ROUND, mProgressPaint, mEmptyPaint);
                break;
            default:
                setPaintStrokeCap(Paint.Cap.SQUARE, mProgressPaint, mEmptyPaint);
                break;
        }
        paths = new Path[maxValue];
        int length = paths.length;
        for (int i = 0; i < length; i++) {
            paths[i] = new Path();
        }
    }


    private void setPaintStrokeCap(Paint.Cap cap, Paint... paints) {
        for (Paint paint :
                paints) {
            paint.setStrokeCap(cap);
        }
    }

    public enum Style {
        ROUND, SQUARE
    }

    public void setGridStyle(Style style) {
        if (null == style) {
            return;
        }
        switch (style) {
            case ROUND:
                setPaintStrokeCap(Paint.Cap.ROUND, mProgressPaint, mEmptyPaint);
                break;
            case SQUARE:
                setPaintStrokeCap(Paint.Cap.SQUARE, mProgressPaint, mEmptyPaint);
                break;
            default:
                setPaintStrokeCap(Paint.Cap.ROUND, mProgressPaint, mEmptyPaint);
                break;
        }
        invalidate();
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    private Path fillPathByIndex(Path path, int index, float singleWidth, float distance) {
        float y = getMeasuredHeight() - calculateY(index, singleWidth, distance);
        path.moveTo(leftPadding + singleWidth / 2, y);
        path.lineTo(getWidth() - rightPadding - singleWidth / 2, y);
        return path;
    }

    private float calculateY(int index, float singleWidth, float distance) {
        return bottomPadding + (index + 1) * singleWidth - singleWidth / 2 + index * distance;
    }


    private Paint makePaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(10);
//        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    private void setPaintWidth(float width, Paint... paint) {
        if (null == paint) {
            return;
        }
        for (Paint aPaint : paint) {
            aPaint.setStrokeWidth(width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float maxValueNum = (float) (maxValue - 1) / 4 + maxValue;
        final float paintWidth = (getMeasuredHeight() - bottomPadding - topPadding) / maxValueNum;
        final float distance = paintWidth / 4;
        setPaintWidth(paintWidth, mEmptyPaint, mProgressPaint);
        for (int i = 0; i < maxValue; i++) {
            fillPathByIndex(paths[i], i, paintWidth, distance);
            if (i < progress) {
                canvas.drawPath(paths[i], mProgressPaint);
            } else {
                canvas.drawPath(paths[i], mEmptyPaint);
            }
        }
    }
}

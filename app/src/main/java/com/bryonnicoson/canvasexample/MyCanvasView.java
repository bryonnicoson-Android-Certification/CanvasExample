package com.bryonnicoson.canvasexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by bryon on 3/22/18.
 */

public class MyCanvasView extends View {

    // if the finger has barely moved, no need to draw
    // it is not necessary, using the path, to draw every pixel - interpolate to improve performance
    private static final float TOUCH_TOLERANCE = 4;

    private Paint mPaint;
    private Path mPath;
    private int mDrawColor;
    private int mBackgroundColor;
    private Canvas mExtraCanvas;
    private Bitmap mExtraBitmap;

    // path starting points
    private float mX, mY;

    MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context);

        mBackgroundColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_orange, null);
        mDrawColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_yellow, null);

        // Holds the path we are currently drawing
        mPath = new Path();
        // Set up the paint with which to draw
        mPaint = new Paint();
        mPaint.setColor(mDrawColor);
        // Smoothes out edges of what is drawn without affecting shape
        mPaint.setAntiAlias(true);
        // Dithering affects how higher-precision colors are down-sampled on lower-precision devices
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);     // default: FILL
        mPaint.setStrokeJoin(Paint.Join.ROUND);  // default: MITER
        mPaint.setStrokeCap(Paint.Cap.ROUND);    // default: BUTT
        mPaint.setStrokeWidth(12);               // default: Hairline-width
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // create bitmap and canvas with bitmap, fill canvas with color
        mExtraBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(mBackgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw the bitmap that stores the path the user has drawn
        canvas.drawBitmap(mExtraBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        // invalidate() is within cases because there are many motion events for which we do
        // not want to redraw screen
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                // nothing drawn, so no need to invalidate
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                // nothing drawn, so no need to invalidate
                break;
            default:
                // nada
        }
        return true;
    }

    private void touchStart(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        // get the distance from current point and start
        float dx = Math.abs(mX - x);
        float dy = Math.abs(mY - y);
        // if distance exceeds tolerance, draw
        if (dx > TOUCH_TOLERANCE || dy > TOUCH_TOLERANCE) {
            // QuadTo() adds a quadratic bezier from the last point to the current
            mPath.quadTo(mX, mY, (mX + x)/2, (mY + y)/2);
            // reset mX, mY to last drawn point
            mX = x;
            mY = y;
            // save the path in the extra bitmap, which we access through its canvas
            mExtraCanvas.drawPath(mPath, mPaint);
        }
    }

    private void touchUp() {
        // reset the path so it doesn't get drawn again.
        mPath.reset();
    }
}

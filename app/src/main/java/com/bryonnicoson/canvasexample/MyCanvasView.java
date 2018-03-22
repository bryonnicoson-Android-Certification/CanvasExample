package com.bryonnicoson.canvasexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bryon on 3/22/18.
 */

public class MyCanvasView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mDrawColor;
    private int mBackgroundColor;
    private Canvas mExtraCanvas;
    private Bitmap mExtraBitmap;


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
}

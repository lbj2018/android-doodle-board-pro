package com.derek.ddoodleboardpro.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {
	private Path mDrawPath; // drawing path
	private Paint mDrawPaint, mCanvasPaint; // drawing and canvas paint
	private Canvas mDrawCanvas; // canvas
	private Bitmap mCanvasBitmap; // canvas bitmap
	private boolean mPainting; // true for pen, false for eraser

	private int mPaintColor;
	private int mEraserColor;

	private PointF mEraserCenter;
	private Paint mEraserPaint;

	public Bitmap getBitmap() {
		return mCanvasBitmap;
	}

	public void clear() {
		int w = getWidth();
		int h = getHeight();

		createNewCanvasBitmap(w, h);

		mPainting = true;

		mDrawPath.reset();
		invalidate();
	}

	public void setPaintColor(int color) {
		mPaintColor = color;
	}

	public void setPaintWidth(int width) {
		mDrawPaint.setStrokeWidth(width);
	}

	public boolean isPainting() {
		return mPainting;
	}

	public void setPainting(boolean painting) {
		mPainting = painting;
	}

	private void createNewCanvasBitmap(int width, int height) {
		mCanvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		mDrawCanvas = new Canvas(mCanvasBitmap);
		mDrawCanvas.drawColor(0xFFFFFFFF);
	}

	// Initialization
	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPainting = true;
		this.setBackgroundColor(0xFFFFFFFF);
		setupDrawing();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		createNewCanvasBitmap(w, h);
	}

	private void setupDrawing() {
		mDrawPath = new Path();
		mDrawPaint = new Paint();
		mDrawPaint.setColor(0xFF000000);
		mDrawPaint.setAntiAlias(true);
		mDrawPaint.setStrokeWidth(10);
		mDrawPaint.setStyle(Paint.Style.STROKE);
		mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
		mDrawPaint.setStrokeCap(Paint.Cap.ROUND);

		mCanvasPaint = new Paint(Paint.DITHER_FLAG);

		mEraserColor = 0xFFFFFFFF;
		mEraserPaint = new Paint();
		mEraserPaint.setColor(0xFF000000);
		mEraserPaint.setAntiAlias(true);
		mEraserPaint.setStrokeWidth(4);
		mEraserPaint.setStyle(Paint.Style.STROKE);
		mEraserPaint.setStrokeJoin(Paint.Join.ROUND);
		mEraserPaint.setStrokeCap(Paint.Cap.ROUND);
	}

	// Draw
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mCanvasBitmap, 0, 0, mCanvasPaint);
		canvas.drawPath(mDrawPath, mDrawPaint);

		if (!mPainting) {
			if (mEraserCenter != null) {
				canvas.drawCircle(mEraserCenter.x, mEraserCenter.y, 98, mEraserPaint);
			}
		}
	}

	// Touch
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();
		float touchY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mPainting) {
				mDrawPaint.setColor(mPaintColor);
				setPaintWidth(10);
			} else {
				mDrawPaint.setColor(mEraserColor);
				setPaintWidth(200);

				mEraserCenter = new PointF(touchX, touchY);
			}
			mDrawPath.moveTo(touchX, touchY);
			break;
		case MotionEvent.ACTION_MOVE:
			mDrawPath.lineTo(touchX, touchY);

			// Eraser
			if (!mPainting) {
				mEraserCenter = new PointF(touchX, touchY);
			}
			break;
		case MotionEvent.ACTION_UP:
			mEraserCenter = null;

			mDrawCanvas.drawPath(mDrawPath, mDrawPaint);
			mDrawPath.reset();
			break;
		default:
			return false;
		}

		invalidate();
		return true;
	}
}

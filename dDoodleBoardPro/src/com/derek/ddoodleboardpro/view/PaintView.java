package com.derek.ddoodleboardpro.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.derek.ddoodleboardpro.model.ColorStore;

public class PaintView extends View {
	private Path mDrawPath; // drawing path
	private Paint mDrawPaint, mCanvasPaint; // drawing and canvas paint
	private int mPaintColor = 0xFF660000; // brush color
	private Canvas mDrawCanvas; // canvas
	private Bitmap mCanvasBitmap; // canvas bitmap

	public Bitmap getBitmap() {
		return mCanvasBitmap;
	}

	public void clear() {
		int w = getWidth();
		int h = getHeight();

		createNewCanvasBitmap(w, h);

		mDrawPath.reset();
		invalidate();
	}

	public void setPaintColor(int color) {
		invalidate();

		mPaintColor = color;
		mDrawPaint.setColor(mPaintColor);
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setBackgroundColor(0xFFFFFFFF);
		setupDrawing();
	}

	private void createNewCanvasBitmap(int width, int height) {
		mCanvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		mDrawCanvas = new Canvas(mCanvasBitmap);
		mDrawCanvas.drawColor(0xFFFFFFFF);
	}

	private void setupDrawing() {
		mDrawPath = new Path();
		mDrawPaint = new Paint();
		mDrawPaint.setColor(mPaintColor);
		mDrawPaint.setAntiAlias(true);
		mDrawPaint.setStrokeWidth(10);
		mDrawPaint.setStyle(Paint.Style.STROKE);
		mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
		mDrawPaint.setStrokeCap(Paint.Cap.ROUND);

		mCanvasPaint = new Paint(Paint.DITHER_FLAG);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mCanvasBitmap, 0, 0, mCanvasPaint);
		canvas.drawPath(mDrawPath, mDrawPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();
		float touchY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setPaintColor(ColorStore.randomColor());
			mDrawPath.moveTo(touchX, touchY);
			break;
		case MotionEvent.ACTION_MOVE:
			mDrawPath.lineTo(touchX, touchY);
			break;
		case MotionEvent.ACTION_UP:
			mDrawCanvas.drawPath(mDrawPath, mDrawPaint);
			mDrawPath.reset();
			break;
		default:
			return false;
		}

		invalidate();
		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		createNewCanvasBitmap(w, h);
	}
}

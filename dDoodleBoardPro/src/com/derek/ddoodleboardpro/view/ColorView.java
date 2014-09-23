package com.derek.ddoodleboardpro.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ColorView extends View {
	private Paint mStokePaint;
	private Paint mFillPaint;
	private int mFillColor;
	private int mStokeColor;
	private int mStokeWidth;

	public ColorView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mStokeColor = 0xffffffff;
		mFillColor = 0xff000000;
		mStokeWidth = 4;

		setupStokePaint();
		setupFillPaint();
	}

	private void setupStokePaint() {
		mStokePaint = new Paint();
		mStokePaint.setColor(mStokeColor);
		mStokePaint.setAntiAlias(true);
		mStokePaint.setStrokeWidth(mStokeWidth);
		mStokePaint.setStyle(Paint.Style.STROKE);
		mStokePaint.setStrokeJoin(Paint.Join.ROUND);
		mStokePaint.setStrokeCap(Paint.Cap.ROUND);
	}

	private void setupFillPaint() {
		mFillPaint = new Paint();
		mFillPaint.setColor(mFillColor);
		mFillPaint.setAntiAlias(true);
		mFillPaint.setStrokeWidth(0);
		mFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mFillPaint.setStrokeJoin(Paint.Join.ROUND);
		mFillPaint.setStrokeCap(Paint.Cap.ROUND);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();

		float radius = (float) (width > height ? height / 2.0 - 2 * mStokeWidth : width / 2.0 - 2 * mStokeWidth);

		canvas.drawCircle(width / 2.0f, height / 2.0f, radius, mFillPaint);

		if (isSelected()) {
			canvas.drawCircle(width / 2.0f, height / 2.0f, radius, mStokePaint);
		}
	}

	public int getFillColor() {
		return mFillColor;
	}

	public void setFillColor(int fillColor) {
		mFillColor = fillColor;

		setupFillPaint();
		invalidate();
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);

		invalidate();
	}
}

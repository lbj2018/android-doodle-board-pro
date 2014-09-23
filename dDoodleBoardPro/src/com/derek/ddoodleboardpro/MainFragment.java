package com.derek.ddoodleboardpro;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.derek.ddoodleboardpro.view.ColorView;
import com.derek.ddoodleboardpro.view.PaintView;

public class MainFragment extends Fragment implements View.OnClickListener {
	private PaintView mPaintView;
	private Button mNewPageButton;
	private Button mPenButton;
	private Button mEraserButton;
	private Button mSaveButton;
	private ColorView mRedColorView;
	private ColorView mGreenColorView;
	private ColorView mGrayColorView;
	private ColorView mBlueColorView;
	private ColorView mYellowColorView;
	private ColorView mBlackColorView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

		mPaintView = (PaintView) rootView.findViewById(R.id.view_paint_view);
		mNewPageButton = (Button) rootView.findViewById(R.id.button_new_page);
		mPenButton = (Button) rootView.findViewById(R.id.button_pen);
		mEraserButton = (Button) rootView.findViewById(R.id.button_eraser);
		mSaveButton = (Button) rootView.findViewById(R.id.button_save);

		mNewPageButton.setOnClickListener(this);
		mPenButton.setOnClickListener(this);
		mEraserButton.setOnClickListener(this);
		mSaveButton.setOnClickListener(this);

		mRedColorView = (ColorView) rootView.findViewById(R.id.view_color_view_red);
		mGreenColorView = (ColorView) rootView.findViewById(R.id.view_color_view_green);
		mGrayColorView = (ColorView) rootView.findViewById(R.id.view_color_view_gray);
		mBlueColorView = (ColorView) rootView.findViewById(R.id.view_color_view_blue);
		mYellowColorView = (ColorView) rootView.findViewById(R.id.view_color_view_yellow);
		mBlackColorView = (ColorView) rootView.findViewById(R.id.view_color_view_black);

		mRedColorView.setOnClickListener(this);
		mGreenColorView.setOnClickListener(this);
		mGrayColorView.setOnClickListener(this);
		mBlueColorView.setOnClickListener(this);
		mYellowColorView.setOnClickListener(this);
		mBlackColorView.setOnClickListener(this);

		mRedColorView.setFillColor(getResources().getColor(R.color.color_red));
		mGreenColorView.setFillColor(getResources().getColor(R.color.color_green));
		mGrayColorView.setFillColor(getResources().getColor(R.color.color_gray));
		mBlueColorView.setFillColor(getResources().getColor(R.color.color_blue));
		mYellowColorView.setFillColor(getResources().getColor(R.color.color_yellow));
		mBlackColorView.setFillColor(getResources().getColor(R.color.color_black));

		mBlackColorView.setSelected(true);
		mPaintView.setPaintColor(mBlackColorView.getFillColor());

		updateUI();

		return rootView;
	}

	private void updateUI() {
		if (mPaintView.isPainting()) {
			mPenButton.setTextColor(0xFFFFFFFF);
			mEraserButton.setTextColor(0xFF000000);
		} else {
			mPenButton.setTextColor(0xFF000000);
			mEraserButton.setTextColor(0xFFFFFFFF);
		}
	}

	@Override
	public void onClick(View view) {
		if (view instanceof ColorView) {
			ColorView colorView = (ColorView) view;

			mBlackColorView.setSelected(false);
			mRedColorView.setSelected(false);
			mGreenColorView.setSelected(false);
			mGrayColorView.setSelected(false);
			mBlueColorView.setSelected(false);
			mYellowColorView.setSelected(false);

			colorView.setSelected(true);
			mPaintView.setPaintColor(colorView.getFillColor());
		} else if (view instanceof Button) {
			if (mNewPageButton == view) {
				mPaintView.clear();
				updateUI();
			} else if (mPenButton == view) {
				mPaintView.setPainting(true);
				updateUI();
			} else if (mEraserButton == view) {
				mPaintView.setPainting(false);
				updateUI();
			} else if (mSaveButton == view) {
				Bitmap bitmap = mPaintView.getBitmap();

				ContentResolver cr = getActivity().getContentResolver();
				String imagePath = MediaStore.Images.Media.insertImage(cr, bitmap, "myPhoto", "this is a Photo");

				if (imagePath != null) {
					Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(imagePath));
					getActivity().sendBroadcast(intent);

					Toast.makeText(getActivity(), R.string.tip_save_album_success, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), R.string.tip_save_albim_failed, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}

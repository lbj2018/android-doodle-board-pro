package com.derek.ddoodleboardpro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.LayoutInflater;

public class ColorsDialogFragment extends DialogFragment {

	@Override
	public Dialog getDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setView(inflater.inflate(R.layout.dialog_colors, null));

		return builder.create();
	}

}

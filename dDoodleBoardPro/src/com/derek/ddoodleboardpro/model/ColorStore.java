package com.derek.ddoodleboardpro.model;

import java.util.Random;

import android.graphics.Color;

public class ColorStore {

	public static int randomColor() {
		Random r = new Random();
		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);

		return Color.rgb(red, green, blue);
	}
}

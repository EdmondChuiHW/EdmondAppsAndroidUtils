package com.edmondapps.utils.android;

import android.os.Build;

public final class Utils {
	private Utils() {
		throw new AssertionError();
	}

	public static boolean hasHoneyComb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneyCombMr1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasHoneyCombMr2() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
	}

	public static boolean hasIceCreamSandwich() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}
}

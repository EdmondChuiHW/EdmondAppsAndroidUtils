package com.edmondapps.utils.android;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

public final class Utils {
	private Utils() {
		throw new AssertionError("nice try");
	}

	/**
	 * @see VERSION_CODES#CUPCAKE
	 */
	public static boolean hasCupcake() {
		return VERSION.SDK_INT >= VERSION_CODES.CUPCAKE;
	}

	/**
	 * @see VERSION_CODES#DONUT
	 */
	public static boolean hasDonut() {
		return VERSION.SDK_INT >= VERSION_CODES.DONUT;
	}

	/**
	 * @see VERSION_CODES#ECLAIR
	 */
	public static boolean hasEclair() {
		return VERSION.SDK_INT >= VERSION_CODES.ECLAIR;
	}

	/**
	 * @see VERSION_CODES#ECLAIR_0_1
	 */
	public static boolean hasEclair_0_1() {
		return VERSION.SDK_INT >= VERSION_CODES.ECLAIR_0_1;
	}

	/**
	 * @see VERSION_CODES#ECLAIR_MR1
	 */
	public static boolean hasEclairMr1() {
		return VERSION.SDK_INT >= VERSION_CODES.ECLAIR_MR1;
	}

	/**
	 * @see VERSION_CODES#FROYO
	 */
	public static boolean hasFroyo() {
		return VERSION.SDK_INT >= VERSION_CODES.FROYO;
	}

	/**
	 * @see VERSION_CODES#GINGERBREAD
	 */
	public static boolean hasGingerBread() {
		return VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
	}

	/**
	 * @see VERSION_CODES#GINGERBREAD_MR1
	 */
	public static boolean hasGingerBreadMr1() {
		return VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD_MR1;
	}

	/**
	 * @see VERSION_CODES#HONEYCOMB
	 */
	public static boolean hasHoneyComb() {
		return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
	}

	/**
	 * @see VERSION_CODES#HONEYCOMB_MR1
	 */
	public static boolean hasHoneyCombMr1() {
		return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
	}

	/**
	 * @see VERSION_CODES#HONEYCOMB_MR2
	 */
	public static boolean hasHoneyCombMr2() {
		return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR2;
	}

	/**
	 * @see VERSION_CODES#ICE_CREAM_SANDWICH
	 */
	public static boolean hasIceCreamSandwich() {
		return VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/**
	 * @see VERSION_CODES#ICE_CREAM_SANDWICH_MR1
	 */
	public static boolean hasIceCreamSandwichMr1() {
		return VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}

	/**
	 * @see VERSION_CODES#JELLY_BEAN
	 */
	public static boolean hasJellyBean() {
		return VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
	}

	/**
	 * @see VERSION_CODES#JELLY_BEAN_MR1
	 */
	public static boolean hasJellyBeanMr1() {
		return VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
	}

	// let it crash run-time!
	@SuppressWarnings("unchecked")
	public static <V extends View> V findView(Activity activity, int id) {
		return (V)activity.findViewById(id);
	}

	// let it crash run-time!
	@SuppressWarnings("unchecked")
	public static <V extends View> V findView(View parent, int id) {
		return (V)parent.findViewById(id);
	}

	// let it crash run-time!
	@SuppressWarnings("unchecked")
	public static <F extends Fragment> F findFragment(FragmentManager manager, String tag) {
		return (F)manager.findFragmentByTag(tag);
	}
}

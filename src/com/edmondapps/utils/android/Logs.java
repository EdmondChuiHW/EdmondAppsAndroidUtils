package com.edmondapps.utils.android;

import android.util.Log;

/**
 * A forwarding class for {@link android.util.Log}. Use
 * {@link #setShouldLog(boolean)} to control all the logging calls.
 * 
 * @author Edmond
 * 
 */
public final class Logs {
	private Logs() {
		throw new AssertionError("nice try");
	}

	private static boolean shouldLog = false;

	/**
	 * 
	 * @param shouldLog
	 *            disables all logging calls if false
	 */
	public static void setShouldLog(boolean shouldLog) {
		Logs.shouldLog = shouldLog;
	}

	/**
	 * @see #setShouldLog(boolean)
	 */
	public static boolean getShouldLog() {
		return shouldLog;
	}

	/**
	 * @see android.util.Log#v(String, String)
	 */
	public static void v(String tag, String msg) {
		if (shouldLog) {
			Log.d(tag, msg);
		}
	}

	/**
	 * @see android.util.Log#v(String, String, Throwable)
	 */
	public static void v(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			Log.v(tag, msg, tr);
		}
	}

	/**
	 * @see android.util.Log#d(String, String)
	 */
	public static void d(String tag, String msg) {
		if (shouldLog) {
			Log.d(tag, msg);
		}
	}

	/**
	 * @see android.util.Log#d(String, String, Throwable)
	 */
	public static void d(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			Log.d(tag, msg, tr);
		}
	}

	/**
	 * @see android.util.Log#i(String, String)
	 */
	public static void i(String tag, String msg) {
		if (shouldLog) {
			Log.i(tag, msg);
		}
	}

	/**
	 * @see android.util.Log#i(String, String, Throwable)
	 */
	public static void i(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			Log.i(tag, msg, tr);
		}
	}

	/**
	 * @see android.util.Log#w(String, String)
	 */
	public static void w(String tag, String msg) {
		if (shouldLog) {
			Log.w(tag, msg);
		}
	}

	/**
	 * @see android.util.Log#w(String, Throwable)
	 */
	public static void w(String tag, Throwable tr) {
		if (shouldLog) {
			Log.w(tag, tr);
		}
	}

	/**
	 * @see android.util.Log#w(String, String, Throwable)
	 */
	public static void w(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			Log.w(tag, msg, tr);
		}
	}

	/**
	 * @see android.util.Log#e(String, String)
	 */
	public static void e(String tag, String msg) {
		if (shouldLog) {
			Log.e(tag, msg);
		}
	}

	/**
	 * @see android.util.Log#e(String, String, Throwable)
	 */
	public static void e(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			Log.e(tag, msg, tr);
		}
	}
}

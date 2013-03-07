package com.edmondapps.utils.android;

public final class Log {
	private Log() {
		throw new AssertionError("nice try");
	}

	private static final boolean shouldLog = BuildConfig.DEBUG;

	public static void v(String tag, String msg) {
		if (shouldLog) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			android.util.Log.v(tag, msg, tr);
		}
	}

	public static void d(String tag, String msg) {
		if (shouldLog) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			android.util.Log.d(tag, msg, tr);
		}
	}

	public static void i(String tag, String msg) {
		if (shouldLog) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			android.util.Log.i(tag, msg, tr);
		}
	}

	public static void w(String tag, String msg) {
		if (shouldLog) {
			android.util.Log.w(tag, msg);
		}
	}

	public static void w(String tag, Throwable tr) {
		if (shouldLog) {
			android.util.Log.w(tag, tr);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			android.util.Log.w(tag, msg, tr);
		}
	}

	public static void e(String tag, String msg) {
		if (shouldLog) {
			android.util.Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (shouldLog) {
			android.util.Log.e(tag, msg, tr);
		}
	}
}

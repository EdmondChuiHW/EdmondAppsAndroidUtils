package com.edmondapps.utils.android.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

public abstract class AbstractAsyncFragment<Params, Progress, Result> extends SherlockFragment {
	public static final String TAG = "AsyncFragment";

	private AsyncTask<Params, Progress, Result> mAsyncTask;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		setRetainInstance(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAsyncTask = onCreateAsyncTask();
		mAsyncTask.execute(getAsyncTaskParams());
	}

	protected abstract AsyncTask<Params, Progress, Result> onCreateAsyncTask();

	protected abstract Params[] getAsyncTaskParams();

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mAsyncTask != null) {
			mAsyncTask.cancel(false);
		}
	}
}

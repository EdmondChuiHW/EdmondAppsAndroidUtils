package com.edmondapps.utils.android.fragment;

import android.app.Activity;
import android.app.Service;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * A non-UI {@code Fragment} which manages {@link AsyncTask} across orientation
 * change. Attach this {@code Fragment} with
 * {@link FragmentTransaction#add(Fragment, String)}. Do <i>not</i> re-add it
 * on orientation change; it will retain itself.
 * <p/>
 * By default, the {@link AsyncTask} is cancelled when {@link #onDestroy()} is
 * invoked. You may change this behaviour with
 * {@link #setCancelTaskOnDestroy(boolean)}. Consider using a {@link Service} if
 * your task does not need to update the UI.
 * 
 * @author Edmond
 * 
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
public abstract class AbstractAsyncFragment<Params, Progress, Result> extends SherlockFragment {

	private AsyncTask<Params, Progress, Result> mAsyncTask;
	private boolean mCancelTaskOnDestroy = true;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		super.setRetainInstance(true);// magic
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		onExecuteAsyncTask(onCreateAsyncTask());
	}

	private void onExecuteAsyncTask(AsyncTask<Params, Progress, Result> asyncTask) {
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
		}
		mAsyncTask = asyncTask;
		asyncTask.execute(onCreateAsyncTaskParams(asyncTask));
	}

	/**
	 * <b>Do not use.</b> It is necessary to retain instance for this class to
	 * function properly.
	 */
	@Override
	public void setRetainInstance(boolean retain) {
		throw new UnsupportedOperationException("It is necessary to retain instance for this class to function properly.");
	}

	/**
	 * Called once during {@link #onCreate(Bundle)}.
	 * {@link #onCreateAsyncTaskParams(AsyncTask)} will be called immediately
	 * after.
	 * <p>
	 * <b>Beware!</b> Using an anonymous class can cause memory leak if it
	 * retains a reference to the enclosing {@code Activity}. However, a
	 * reference to this {@code Fragment} is OK since its instance is retained.
	 * When in doubt, consider using a static inner class instead.
	 * 
	 * @return An instance of {@link AsyncTask}.
	 * @see #onCreateAsyncTaskParams(AsyncTask)
	 * @see #getAsyncTask()
	 */
	protected abstract AsyncTask<Params, Progress, Result> onCreateAsyncTask();

	/**
	 * Submits a new {@link AsyncTask} to be executed. Any running tasks would
	 * be cancelled abruptly.
	 * <p>
	 * {@link #onCreateAsyncTaskParams(AsyncTask)} will be called immediately
	 * after.
	 * 
	 * @param asyncTask
	 *            An instance of {@link AsyncTask}.
	 */
	protected void newAsyncTask(AsyncTask<Params, Progress, Result> asyncTask) {
		onExecuteAsyncTask(asyncTask);
	}

	/**
	 * Triggered by {@link #onCreateAsyncTask()} or
	 * {@link #newAsyncTask(AsyncTask)}. {@link AsyncTask#execute(Params...)}
	 * will be called with the returned
	 * value immediately after.
	 * 
	 * @param asyncTask
	 *            the task to be executed
	 * 
	 * @return {@code null} by default; clients may override this method to
	 *         return the desired parameters.
	 */
	protected Params[] onCreateAsyncTaskParams(AsyncTask<Params, Progress, Result> asyncTask) {
		return null;
	}

	/**
	 * Note: The {@link AsyncTask} should not be kept running when the
	 * {@code Fragment} is destroyed. Consider using a {@link Service} if your
	 * task's lifecycle should be independent of the {@link Activity}'s.
	 * 
	 * @see #getCancelTaskOnDestroy()
	 * @param cancelTaskOnDestroy
	 *            true if the {@link AsyncTask} should be cancelled when
	 *            {@link #onDestroy()} is invoked; false otherwise.
	 */
	protected void setCancelTaskOnDestroy(boolean cancelTaskOnDestroy) {
		mCancelTaskOnDestroy = cancelTaskOnDestroy;
	}

	/**
	 * @see #setCancelTaskOnDestroy(boolean)
	 */
	protected boolean getCancelTaskOnDestroy() {
		return mCancelTaskOnDestroy;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mCancelTaskOnDestroy && (mAsyncTask != null)) {
			mAsyncTask.cancel(true);
			mAsyncTask = null;
		}
	}

	/**
	 * 
	 * @return the original {@link AsyncTask} instance returned by
	 *         {@link #onCreateAsyncTask()}.
	 * @see #cancelAsyncTask(boolean)
	 */
	protected AsyncTask<Params, Progress, Result> getAsyncTask() {
		return mAsyncTask;
	}
}

package com.edmondapps.utils.android.view;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

/**
 * An {@link AnimatorListener} that executes {@linkplain Runnable}s.
 * 
 * @author Edmond
 * 
 */
public final class RunnableAnimatorListener extends AnimatorListenerAdapter {
	private Runnable mStartRunnable;
	private Runnable mEndRunnable;
	private boolean mResetAfterRun;

	public RunnableAnimatorListener() {
	}

	/**
	 * @see #setResetAfterRun(boolean)
	 */
	public RunnableAnimatorListener(boolean resetAfterRun) {
		mResetAfterRun = resetAfterRun;
	}

	/**
	 * 
	 * @param r
	 *            a null-safe {@code Runnable} that will be invoked by
	 *            {@link #onAnimationStart(Animator)}.
	 * @return the same instance for chaining
	 */
	public RunnableAnimatorListener withStartAction(Runnable r) {
		mStartRunnable = r;
		return this;
	}

	/**
	 * 
	 * @param r
	 *            a null-safe {@code Runnable} that will be invoked by
	 *            {@link #onAnimationEnd(Animator)}.
	 * @return the same instance for chaining
	 */
	public RunnableAnimatorListener withEndAction(Runnable r) {
		mEndRunnable = r;
		return this;
	}

	/**
	 * Execute the {@code Runnable} given by {@link #withStartAction(Runnable)}.
	 * If {@link #isResetAfterRun()} returns true,the {@code Runnable} reference
	 * will also be nullified.
	 */
	@Override
	public void onAnimationStart(Animator animation) {
		if (mStartRunnable != null) {
			mStartRunnable.run();
			if (mResetAfterRun) {
				mStartRunnable = null;
			}
		}
	}

	/**
	 * Execute the {@code Runnable} given by {@link #withEndAction(Runnable)}.
	 * If {@link #isResetAfterRun()} returns true,the {@code Runnable} reference
	 * will also be nullified.
	 */
	@Override
	public void onAnimationEnd(Animator animation) {
		if (mEndRunnable != null) {
			mEndRunnable.run();
			if (mResetAfterRun) {
				mEndRunnable = null;
			}
		}
	}

	/**
	 * False by default.
	 * 
	 * @see #setResetAfterRun(boolean)
	 */
	public final boolean isResetAfterRun() {
		return mResetAfterRun;
	}

	/**
	 * If true, all the {@code Runnable}s will only be invoked once. False by
	 * default.
	 * 
	 * @see #withStartAction(Runnable)
	 * @see #withEndAction(Runnable)
	 * @param resetAfterRun
	 */
	public final void setResetAfterRun(boolean resetAfterRun) {
		mResetAfterRun = resetAfterRun;
	}
}

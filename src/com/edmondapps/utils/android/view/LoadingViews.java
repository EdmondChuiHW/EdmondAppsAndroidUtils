/*
 * Copyright 2013 Edmond Chui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.edmondapps.utils.android.view;

import static com.nineoldandroids.view.ViewHelper.setAlpha;
import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
import android.view.View;
import android.widget.ProgressBar;

/**
 * This class reduces the boiler-plate code needed to perform UI updates while
 * loading data. Calling {@link #startLoading()} causes the main content view to
 * fade out and the progress view to fade in. Calling {@link #doneLoading()}
 * does the opposite.
 * </p>
 * Use {@link LoadingViews#of(View, ProgressBar)} to obtain an instance.
 * 
 * @author Edmond
 * 
 * @param <V>
 *            the main content view
 * @param <P>
 *            the progress view
 */
public class LoadingViews<V extends View, P extends View> {
	/**
	 * Default duration of all animations.
	 */
	public static final long DEFAULT_DURATION = 300L;

	private final V mView;
	private final P mProgressBar;
	private final long mDuration;
	private final RunnableAnimatorListener mListener = new RunnableAnimatorListener(true);
	private final Runnable mViewEndAction = new Runnable() {
		@Override
		public void run() {
			mView.setVisibility(View.GONE);
		}
	};
	private final Runnable mProgressStartAction = new Runnable() {
		@Override
		public void run() {
			setAlpha(mProgressBar, 0F);
			mProgressBar.setVisibility(View.VISIBLE);
		}
	};
	private final Runnable mViewStartAction = new Runnable() {
		@Override
		public void run() {
			setAlpha(mView, 0F);
			mView.setVisibility(View.VISIBLE);
		}
	};
	private final Runnable mProgressEndAction = new Runnable() {
		@Override
		public void run() {
			mProgressBar.setVisibility(View.GONE);
		}
	};

	/**
	 * Creates an instance of {@link LoadingViews} with {@code DEFAULT_DURATION}
	 * of {@value #DEFAULT_DURATION}.
	 * 
	 * @param view
	 *            the reference to the main content {@code View}.
	 * @param p
	 *            the reference to the {@code ProgressBar} (usually
	 *            indeterminate)
	 * @return
	 */
	public static final <V extends View> LoadingViews<V, ProgressBar> of(V view, ProgressBar p) {
		return new LoadingViews<V, ProgressBar>(view, p, DEFAULT_DURATION);
	}

	/**
	 * Constructs a {@link LoadingViews} with custom parameters.
	 */
	public LoadingViews(V view, P progressBar, long duration) {
		mView = view;
		mProgressBar = progressBar;
		mDuration = duration;
	}

	/**
	 * Hides the main content view and displays the progress view. This action
	 * is
	 * animated with a fade in / fade out animation by default.
	 */
	public void startLoading() {
		animate(mProgressBar).alpha(1)
				.setDuration(mDuration)
				.setListener(mListener.withStartAction(mProgressStartAction));

		animate(mView).alpha(0)
				.setDuration(mDuration)
				.setListener(mListener.withEndAction(mViewEndAction));
	}

	/**
	 * Displays the main content view and hides the progress view. This action
	 * is animated with a fade in / fade out animation by default.
	 */
	public void doneLoading() {
		animate(mView).alpha(1)
				.setDuration(mDuration)
				.setListener(mListener.withStartAction(mViewStartAction));

		animate(mProgressBar).alpha(0)
				.setDuration(mDuration)
				.setListener(mListener.withEndAction(mProgressEndAction));
	}

	/**
	 * 
	 * @return the same {@code View} passed to the constructor
	 */
	public final V getView() {
		return mView;
	}

	/**
	 * 
	 * @return the same {@code View} passed to the constructor or a
	 *         {@link ProgressBar} if you used the
	 *         {@link LoadingViews#of(View, ProgressBar)} factory method.
	 */
	public final P getProgressBar() {
		return mProgressBar;
	}

	/**
	 * 
	 * @return the same value passed to the constructor or
	 *         {@value #DEFAULT_DURATION} if you used the
	 *         {@link LoadingViews#of(View, ProgressBar)} factory method.
	 */
	public final long getDuration() {
		return mDuration;
	}
}

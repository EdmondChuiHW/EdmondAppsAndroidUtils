package com.edmondapps.utils.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.edmondapps.utils.android.R;

public abstract class DualPaneActivity extends SinglePaneActivity {
	private static final String KEY_DETAIL_FRAGMENT_TAG = "ed__detail_fragment_tag";
	private static final String KEY_DETAIL_FRAGMENT_LAYOUT_ID = "ed__detail_fragment_layout_id";

	private int mDetailFragmentId;
	private String mDetailFragmentTag;
	private Fragment mDetailFragment;
	private boolean isCustomLayout = true;

	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		if (savedState != null) {
			if (!savedState.containsKey(KEY_DETAIL_FRAGMENT_TAG) ||
					!savedState.containsKey(KEY_DETAIL_FRAGMENT_LAYOUT_ID)) {
				throw new IllegalStateException("You must call through super.onSaveInstanceState(Bundle).");
			}

			mDetailFragmentTag = savedState.getString(KEY_DETAIL_FRAGMENT_TAG);
			mDetailFragmentId = savedState.getInt(KEY_DETAIL_FRAGMENT_LAYOUT_ID);
			mDetailFragment = getSupportFragmentManager().findFragmentByTag(mDetailFragmentTag);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(KEY_DETAIL_FRAGMENT_TAG, mDetailFragmentTag);
		outState.putInt(KEY_DETAIL_FRAGMENT_LAYOUT_ID, mDetailFragmentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edmondapps.utils.android.activity.SinglePaneActivity#onCreateFragment
	 * ()
	 */
	@Override
	protected Fragment onCreateFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onFragmentTranscation(FragmentTransaction ft) {
		super.onFragmentTranscation(ft);

		mDetailFragmentId = isCustomLayout ? onCreateDetailFragmentId() : R.id.ed__frame_detail;
		mDetailFragment = onCreateDetailFragment();
		mDetailFragmentTag = onCreateDetailFragmentTag();

		ft.add(mDetailFragmentId, mDetailFragment, mDetailFragmentTag);
	}

	@Override
	protected int onCreateContentViewId() {
		isCustomLayout = false;
		return R.layout.ed__layout_dual_pane;
	}

	@Override
	protected int onCreateFragmentId() {
		return R.id.ed__frame_main;
	}

	/**
	 * 
	 * 
	 * @see SinglePaneActivity#onCreateFragmentId ()
	 */
	protected int onCreateDetailFragmentId() {
		throw new UnsupportedOperationException("You must override onCreateDetailFragmentId() if you override onCreateContentViewId().");
	}

	/**
	 * Called during {@code Activity} {@link #onCreate(Bundle)} if necessary.
	 * 
	 * @see #onCreateFragment()
	 * @see #getDetailFragment()
	 * @return a new instance of detail {@code Fragment}
	 */
	protected abstract Fragment onCreateDetailFragment();

	/**
	 * Passed to {@code FragmentManager} for transactions and retrieval. <br />
	 * This method is only called once during {@link #onCreate(Bundle)} and the
	 * returned value is stored.
	 * 
	 * @see #onCreateFragmentTag()
	 * 
	 * @return {@code null} by default; or an unique {@code String} to identify
	 *         the detail {@code Fragment}.
	 */
	protected String onCreateDetailFragmentTag() {
		return null;
	}

	/**
	 * @see #getFragmentId()
	 * @return the stored {@code id} returned by
	 *         {@link #onCreateDetailFragmentId()}.
	 */
	protected final int getDetailFragmentId() {
		return mDetailFragmentId;
	}

	/**
	 * @see #getFragmentTag()
	 * @return the stored {@code String} returned by
	 *         {@link #onCreateDetailFragmentTag()}.
	 */
	protected final String getDetailFragmentTag() {
		return mDetailFragmentTag;
	}

	/**
	 * @see #getFragment()
	 * @return the stored {@code Fragment} returned by
	 *         {@link #onCreateDetailFragment()}.
	 */
	protected final Fragment getDetailFragment() {
		return mDetailFragment;
	}
}

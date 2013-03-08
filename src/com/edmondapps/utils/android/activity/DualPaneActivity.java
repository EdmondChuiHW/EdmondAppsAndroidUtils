package com.edmondapps.utils.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.edmondapps.utils.android.R;
import com.edmondapps.utils.android.annotaion.FragmentName;

/**
 * A simple {@link UpableActivity} which helps building an {@code Activity} with
 * dual-pane layout.
 * <p/>
 * Overriding {@link #onCreateFragment()} and {@link #onCreateDetailFragment()}
 * is all you needed.
 * 
 * <p/>
 * Although not necessary, it is strongly encouraged to provide tags for
 * {@code Fragment}s by annotating a {@link FragmentName} on their classes.
 * 
 * 
 * @author Edmond
 * 
 */
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
	protected void onFragmentTranscation(FragmentTransaction ft) {
		super.onFragmentTranscation(ft);

		mDetailFragmentId = isCustomLayout ? onCreateDetailFragmentId() : R.id.ed__frame_detail;
		mDetailFragment = onCreateDetailFragment();
		mDetailFragmentTag = onCreateDetailFragmentTag();

		ft.add(mDetailFragmentId, mDetailFragment, mDetailFragmentTag);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(KEY_DETAIL_FRAGMENT_TAG, mDetailFragmentTag);
		outState.putInt(KEY_DETAIL_FRAGMENT_LAYOUT_ID, mDetailFragmentId);
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
	 * @see #onCreateFragmentId()
	 */
	protected int onCreateDetailFragmentId() {
		throw new UnsupportedOperationException("You must override onCreateDetailFragmentId() if you have overriden onCreateContentViewId().");
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
	 * <b>Most classes should use {@link FragmentName} on
	 * the target {@code Fragment.}</b><br/>
	 * This method is for advanced users, for example, to differentiate multiple
	 * instances of the same {@code Fragment} class.
	 * <p/>
	 * Passed to {@code FragmentManager} for transactions and retrieval. <br />
	 * This method is guaranteed to be called after
	 * {@link #onCreateDetailFragment()}.
	 * </p>
	 * The default implementation looks for the {@link FragmentName} Annotation.
	 * You may override this method if you wish to return a dynamically
	 * generated {@code String}.
	 * 
	 * @see #getDetailFragmentTag()
	 * @return an unique {@code String} to identify the detail {@code Fragment}.
	 */
	protected String onCreateDetailFragmentTag() {
		FragmentName tag = mDetailFragment.getClass().getAnnotation(FragmentName.class);
		return tag == null ? null : tag.value();
	}

	/**
	 * @see #getFragmentId()
	 * @return the stored {@code id} returned by
	 *         {@link #onCreateDetailFragmentId()}.
	 */
	protected int getDetailFragmentId() {
		return mDetailFragmentId;
	}

	/**
	 * @see #getFragmentTag()
	 * @return the stored {@code String} annotated by {@link FragmentName}.
	 */
	protected String getDetailFragmentTag() {
		return mDetailFragmentTag;
	}

	/**
	 * 
	 * You must annotate a {@link FragmentName} on
	 * the target {@code Fragment}, or override {@link #onCreateFragmentTag()}
	 * for this method to function properly.
	 * 
	 * @see #getFragment()
	 * 
	 * @return the stored {@code Fragment} returned by
	 *         {@link #onCreateDetailFragment()}.
	 */
	protected Fragment getDetailFragment() {
		return mDetailFragment;
	}
}

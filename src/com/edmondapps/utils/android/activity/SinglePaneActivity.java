package com.edmondapps.utils.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.edmondapps.utils.android.R;
import com.edmondapps.utils.android.annotaion.FragmentName;

/**
 * A simple {@link UpableActivity} which helps building an {@code Activity}
 * containing a single {@code Fragment}.
 * <p/>
 * Overriding {@link #onCreateFragment()} is all you needed.
 * <p/>
 * Although not necessary, it is strongly recommended to provide tags for the
 * {@code Fragment} by annotating a {@link FragmentName} on its class.
 * 
 * @author Edmond
 * 
 */
public abstract class SinglePaneActivity extends UpableActivity {
	private static final String KEY_FRAGMENT_TAG = "ed__fragment_tag";
	private static final String KEY_FRAGMENT_LAYOUT_ID = "ed__fragment_layout_id";

	private boolean mCalled;
	private String mFragmentTag;
	private Fragment mFragment;
	private int mFragmentLayoutId;

	/**
	 * Throws an Exception if super class implementation is not called.
	 */
	private void throwIfNotCalled() {
		if (!mCalled) {
			throw new RuntimeException("You must call through super class implementation.");
		}
	}

	/**
	 * Calls {@link FragmentTransaction#add(int, Fragment, String)} if the
	 * {@code Activity} is freshly created.<br />
	 * <br />
	 * 
	 * If the {@code Activity} is returned from an orientation change, no
	 * {@link FragmentTransaction} will be performed.
	 */
	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		setContentView(onCreateLayoutId());

		if (savedState == null) {
			mFragmentLayoutId = onCreateFragmentLayoutId();
			mFragment = onCreateFragment();
			mFragmentTag = onCreateFragmentTag();

			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(mFragmentLayoutId, mFragment, mFragmentTag);

			mCalled = false;
			onFragmentTranscation(transaction);
			throwIfNotCalled();

			transaction.commit();
		} else {
			if (!savedState.containsKey(KEY_FRAGMENT_TAG) ||
					!savedState.containsKey(KEY_FRAGMENT_LAYOUT_ID)) {
				throw new IllegalStateException("You must call through super.onSaveInstanceState(Bundle).");
			}

			mFragmentTag = savedState.getString(KEY_FRAGMENT_TAG);
			mFragmentLayoutId = savedState.getInt(KEY_FRAGMENT_LAYOUT_ID);
			mFragment = getSupportFragmentManager().findFragmentByTag(mFragmentTag);
		}
	}

	/**
	 * Called during every time {@link #onCreate(Bundle)} is invoked, clients
	 * may override this method if a custom layout is supplied. If you do
	 * supply a custom layout, you must also override
	 * {@link #onCreateFragmentLayoutId()} or an
	 * error may occur.
	 * 
	 * @return an id of {@code R.layout.*}
	 */
	protected int onCreateLayoutId() {
		return R.layout.ed__layout_single_pane;
	}

	/**
	 * Called during {@link #onCreate(Bundle)}, client may override this method
	 * to queue additional {@code Fragment} operations.<br/>
	 * <br/>
	 * <i>Derived classes must call the
	 * super class's implementation of this method. If they do not, an
	 * exception will be thrown.</i>
	 * 
	 * @param t
	 *            A {@link FragmentTransaction} to be committed right after this
	 *            method returns.
	 */
	protected void onFragmentTranscation(FragmentTransaction ft) {
		mCalled = true;
	}

	/**
	 * Called during {@link #onCreate(Bundle)} if necessary.
	 * 
	 * Clients must override this method if a custom layout is supplied by
	 * {@link #onCreateLayoutId()}.
	 * 
	 * @return an {@code id} for the {@code Fragment} to be placed in
	 */
	protected int onCreateFragmentLayoutId() {
		return R.id.ed__frame_main;
	}

	/**
	 * Perform necessary state-save operations.<br/>
	 * <br/>
	 * <i>Derived classes must call the
	 * super class's implementation of this method. If they do not, an
	 * exception will be thrown.<i/>
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(KEY_FRAGMENT_TAG, mFragmentTag);
		outState.putInt(KEY_FRAGMENT_LAYOUT_ID, mFragmentLayoutId);
	}

	/**
	 * Called during {@code Activity} {@link #onCreate(Bundle)} if necessary.
	 * <p/>
	 * You should also supply a {@link FragmentName} to the target
	 * {@code Fragment} for {@link #getFragment()} to function properly.
	 * 
	 * @see #getFragment()
	 * @return a new instance of {@code Fragment}
	 */
	protected abstract Fragment onCreateFragment();

	/**
	 * <b>Most classes should use {@link FragmentName} on
	 * the target {@code Fragment}.</b><br/>
	 * This method is for advanced users, for example, to differentiate multiple
	 * instances of the same {@code Fragment} class.
	 * <p/>
	 * Passed to {@code FragmentManager} for transactions and retrieval. <br />
	 * This method is guaranteed to be called after {@link #onCreateFragment()}.
	 * </p>
	 * The default implementation looks for the {@link FragmentName} Annotation.
	 * You may override this method if you wish to return a dynamically
	 * generated tag.
	 * 
	 * @see #getFragmentTag()
	 * @return an unique {@code String} to identify the {@code Fragment}.
	 */
	protected String onCreateFragmentTag() {
		FragmentName tag = mFragment.getClass().getAnnotation(FragmentName.class);
		return tag == null ? null : tag.value();
	}

	/**
	 * You must supply a {@link FragmentName} to the target {@code Fragment}, or
	 * override {@link #onCreateFragmentTag()} for this method to function
	 * properly.
	 * 
	 * @return the stored {@code Fragment} returned by
	 *         {@link #onCreateFragment()}.
	 */
	protected Fragment getFragment() {
		return mFragment;
	}

	/**
	 * 
	 * @return the stored {@code String} annotated by {@link FragmentName}.
	 */
	protected String getFragmentTag() {
		return mFragmentTag;
	}

	/**
	 * 
	 * @return the stored {@code id} returned by {@link #onCreateFragmentLayoutId()}.
	 */
	protected int getFragmentId() {
		return mFragmentLayoutId;
	}
}

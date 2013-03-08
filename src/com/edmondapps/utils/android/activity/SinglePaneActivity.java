package com.edmondapps.utils.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

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
	private boolean isCustomLayout;
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
		int contentViewId = onCreateContentViewId();
		if (contentViewId > 0) {
			isCustomLayout = true;
			setContentView(contentViewId);
		}

		if (savedState == null) {
			mFragmentLayoutId = isCustomLayout ? onCreateFragmentId() : android.R.id.content;
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
	 * Called during {@link #onCreate(Bundle)}, clients may override this
	 * method to if they wish to use a custom layout. If you do supply a custom
	 * layout, you must also override {@link #onCreateFragmentId()} or an
	 * Exception will be thrown.
	 * 
	 * @return an layout {@code id > 0} or {@link #onCreateFragmentId()} will
	 *         not be invoked.
	 */
	protected int onCreateContentViewId() {
		return 0;
	}

	/**
	 * Called during {@link #onCreate(Bundle)}, client may override this method
	 * to queue additional {@code Fragment} operations.<br/>
	 * <br/>
	 * <i>Derived classes must call the
	 * super class's implementation of this method. If they do not, an
	 * exception will be thrown.<i/>
	 * 
	 * @param t
	 *            A {@link FragmentTransaction} to be committed right after this
	 *            method returns.
	 */
	protected void onFragmentTranscation(FragmentTransaction ft) {
		mCalled = true;
	}

	/**
	 * Called during {@link #onCreate(Bundle)}.
	 * 
	 * Clients must override this method if a custom layout is supplied by
	 * {@link #onCreateContentViewId()}.
	 * 
	 * @return an {@code id} for the {@code Fragment} to be placed in
	 */
	protected int onCreateFragmentId() {
		throw new UnsupportedOperationException("You must override onCreateFragmentId() if you override onCreateContentViewId(). ");
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
	 * @return the stored {@code id} returned by {@link #onCreateFragmentId()}.
	 */
	protected int getFragmentId() {
		return mFragmentLayoutId;
	}
}

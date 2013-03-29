package com.edmondapps.utils.android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.edmondapps.utils.android.R;
import com.edmondapps.utils.android.actionbar.SimpleTabListener;

/**
 * Helper class for using {@link ViewPager} along with {@link Tab}. The default
 * implementation handles two-ways selection synchronization and UI state
 * save/restore. Clients may override corresponding methods to modify their
 * behaviors.
 * 
 * @author Edmond
 * 
 */
public abstract class TabViewPagerActivity extends UpableActivity {
	private static final String KEY_VIEW_PAGER_POS = "ed__view_pager_pos";
	private static final String KEY_TAB_POS = "ed__tab_pos";

	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private TabListener mTabListener;
	private OnPageChangeListener mOnPageChangeListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ed__layout_view_pager);

		mPagerAdapter = onCreatePagerAdapter();
		mTabListener = onCreateTabListener();
		mOnPageChangeListener = onCreateOnPageChangeListener();

		mViewPager = (ViewPager)findViewById(R.id.view_pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mOnPageChangeListener);

		initTabs();
	}

	/**
	 * {@link #mPagerAdapter} and {@link #mTabListener} must not be null.
	 */
	private void initTabs() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		int count = mPagerAdapter.getCount();
		for (int i = 0; i < count; i++) {
			actionBar.addTab(newTabForPosition(actionBar, i));
		}
	}

	/**
	 * {@link #mPagerAdapter} and {@link #mTabListener} must not be null.
	 */
	private Tab newTabForPosition(ActionBar actionBar, int pos) {
		return actionBar.newTab().setText(mPagerAdapter.getPageTitle(pos)).setTabListener(mTabListener);
	}

	/**
	 * Saves the states of {@code ActionBar} and {@code ViewPager}. <br/>
	 * <br/>
	 * <i>Derived classes must call the
	 * super class's implementation of this method. If they do not, an
	 * exception will be thrown.<i/>
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(KEY_TAB_POS, getSupportActionBar().getSelectedNavigationIndex());
		outState.putInt(KEY_VIEW_PAGER_POS, mViewPager.getCurrentItem());
	}

	/**
	 * Restores the states of {@code ActionBar} and {@code ViewPager}.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);

		if (savedState.containsKey(KEY_TAB_POS) && savedState.containsKey(KEY_VIEW_PAGER_POS)) {
			getSupportActionBar().setSelectedNavigationItem(savedState.getInt(KEY_TAB_POS));
			mViewPager.setCurrentItem(savedState.getInt(KEY_VIEW_PAGER_POS), true);
		} else {
			throw new IllegalStateException("invalid Bundle, did you call super.onSaveInstanceState(Bundle)?");
		}
	}

	/**
	 * Called once during {@link #onCreate(Bundle)}, the returned
	 * {@code PagerAdapter} is stored.
	 * 
	 * @see #getPagerAdapter()
	 * 
	 * @return a non-null {@code PagerAdapter}, it must also implement
	 *         {@link PagerAdapter#getPageTitle(int)}
	 */
	protected abstract PagerAdapter onCreatePagerAdapter();

	/**
	 * Called once during {@link #onCreate(Bundle)}, the returned
	 * {@code TabListener} is stored. <br />
	 * <br />
	 * The default implementation calls
	 * {@link ViewPager#setCurrentItem(int, boolean)} when
	 * {@link TabListener#onTabSelected} is invoked.
	 * 
	 * @see #getTabListener()
	 * 
	 * @return a non-null {@code TabListener}
	 */
	protected TabListener onCreateTabListener() {
		return new SimpleTabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition(), true);
			}
		};
	}

	/**
	 * Called once during {@link #onCreate(Bundle)}, the returned
	 * {@code TabListener} is stored.<br />
	 * <br />
	 * The default implementation calls
	 * {@link ActionBar#setSelectedNavigationItem(int)} when
	 * {@link OnPageChangeListener#onPageSelected(int)} is invoked.
	 * 
	 * @see #getOnPageChangeListener()
	 * 
	 * @return an instance of {@code OnPageChangeListener} or {@code null}
	 */
	protected OnPageChangeListener onCreateOnPageChangeListener() {
		return new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				getSupportActionBar().setSelectedNavigationItem(position);
			}
		};
	}

	/**
	 * 
	 * @return the stored {@code PagerAdapter} returned by
	 *         {@link #onCreatePagerAdapter()}
	 */
	protected PagerAdapter getPagerAdapter() {
		return mPagerAdapter;
	}

	/**
	 * 
	 * @return the local {@code ViewPager} instance
	 */
	protected ViewPager getViewPager() {
		return mViewPager;
	}

	/**
	 * 
	 * @return the stored {@code TabListener} returned by
	 *         {@link #onCreateTabListener()}
	 */
	protected TabListener getTabListener() {
		return mTabListener;
	}

	/**
	 * 
	 * @return the stored {@code OnPageChangeListener} returned by
	 *         {@link #onCreateOnPageChangeListener()}
	 */
	protected OnPageChangeListener getOnPageChangeListener() {
		return mOnPageChangeListener;
	}
}

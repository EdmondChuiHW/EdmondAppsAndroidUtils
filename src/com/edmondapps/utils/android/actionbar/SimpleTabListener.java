package com.edmondapps.utils.android.actionbar;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;

/**
 * Skeleton class which implements {@link TabListener}. Extend this if you do
 * not intend to override every method of {@link TabListener}.
 * 
 * @author Edmond
 * @see SimpleOnPageChangeListener
 */
public class SimpleTabListener implements TabListener {
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
}

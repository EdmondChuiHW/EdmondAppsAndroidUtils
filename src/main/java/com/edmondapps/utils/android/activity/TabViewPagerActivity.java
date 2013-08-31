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
package com.edmondapps.utils.android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import com.edmondapps.utils.android.R;
import com.edmondapps.utils.android.actionbar.SimpleTabListener;

import static android.support.v7.app.ActionBar.Tab;
import static android.support.v7.app.ActionBar.TabListener;

/**
 * Helper class for using {@link ViewPager} along with {@link Tab}. The default
 * implementation handles two-ways selection synchronization and UI state
 * save/restore. Clients may override corresponding methods to modify their
 * behaviors.
 * 
 * @author Edmond
 * 
 */
public abstract class TabViewPagerActivity extends UpableActivity implements ViewPagerCreator {
    private static final String KEY_VIEW_PAGER_POS = "ed__view_pager_pos";
    private static final String KEY_TAB_POS = "ed__tab_pos";
    private static final String KEY_TABS_ENABLED = "ed__tabs_enabled";

    private ViewPager mViewPager;
    private boolean mTabsEnabled;
    private PagerAdapter mPagerAdapter;
    private TabListener mTabListener;
    private OnPageChangeListener mOnPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ed__layout_view_pager);

        mTabsEnabled = isTabsEnabled();

        mPagerAdapter = onCreatePagerAdapter();
        mOnPageChangeListener = onCreateOnPageChangeListener();

        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);

        if (mTabsEnabled) {
            mTabListener = onCreateTabListener();
            initTabs();
        }
    }

    @Override
    public boolean isTabsEnabled() {
        return true;
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
        outState.putBoolean(KEY_TABS_ENABLED, mTabsEnabled);
    }

    /**
     * Restores the states of {@code ActionBar} and {@code ViewPager}.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        if (savedState.containsKey(KEY_TAB_POS)
                && savedState.containsKey(KEY_VIEW_PAGER_POS)
                && savedState.containsKey(KEY_TABS_ENABLED)) {

            mTabsEnabled = savedState.getBoolean(KEY_TABS_ENABLED);
            mViewPager.setCurrentItem(savedState.getInt(KEY_VIEW_PAGER_POS), true);
            if (mTabsEnabled) {
                getSupportActionBar().setSelectedNavigationItem(savedState.getInt(KEY_TAB_POS));
            }

        } else {
            throw new IllegalStateException("invalid Bundle, did you call super.onSaveInstanceState(Bundle)?");
        }
    }

    @Override
    public TabListener onCreateTabListener() {
        return new SimpleTabListener() {
            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }
        };
    }

    @Override
    public OnPageChangeListener onCreateOnPageChangeListener() {
        return new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mTabsEnabled) {
                    getSupportActionBar().setSelectedNavigationItem(position);
                }
            }
        };
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    @Override
    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public TabListener getTabListener() {
        return mTabListener;
    }

    @Override
    public OnPageChangeListener getOnPageChangeListener() {
        return mOnPageChangeListener;
    }
}

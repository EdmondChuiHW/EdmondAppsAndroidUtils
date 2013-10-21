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

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * @author Edmond
 *
 */
public interface ViewPagerCreator {

    /**
     * Called once during {@link #onCreate(Bundle)} when necessary.
     * 
     * @return true if {@linkplain Tab}s should be used in the {@link ActionBar}
     */
    public abstract boolean isTabsEnabled();

    /**
     * Called once during {@link #onCreate(Bundle)}, the returned
     * {@code PagerAdapter} is stored.
     * 
     * @see #getPagerAdapter()
     * 
     * @return a non-null {@code PagerAdapter}, it must also implement
     *         {@link PagerAdapter#getPageTitle(int)}
     */
    public abstract PagerAdapter onCreatePagerAdapter();

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
    public abstract ActionBar.TabListener onCreateTabListener();

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
    public abstract OnPageChangeListener onCreateOnPageChangeListener();

    /**
     * 
     * @return the stored {@code PagerAdapter} returned by
     *         {@link #onCreatePagerAdapter()}
     */
    public abstract PagerAdapter getPagerAdapter();

    /**
     * 
     * @return the local {@code ViewPager} instance
     */
    public abstract ViewPager getViewPager();

    /**
     * 
     * @return the stored {@code TabListener} returned by
     *         {@link #onCreateTabListener()}
     */
    public abstract ActionBar.TabListener getTabListener();

    /**
     * 
     * @return the stored {@code OnPageChangeListener} returned by
     *         {@link #onCreateOnPageChangeListener()}
     */
    public abstract OnPageChangeListener getOnPageChangeListener();

}

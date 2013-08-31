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
package com.edmondapps.utils.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.edmondapps.utils.android.R;
import com.edmondapps.utils.android.activity.ViewPagerCreator;

import static android.support.v7.app.ActionBar.TabListener;
import static com.edmondapps.utils.android.view.ViewUtils.findView;

/**
 * @author Edmond
 * 
 */
public abstract class ViewPagerFragment extends Fragment implements ViewPagerCreator {

    private ViewPager mViewPagerV;
    private PagerAdapter mPagerAdapter;
    private OnPageChangeListener mOnPageChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ed__layout_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPagerAdapter = onCreatePagerAdapter();
        mOnPageChangeListener = onCreateOnPageChangeListener();

        mViewPagerV = findView(view, R.id.view_pager);
        mViewPagerV.setAdapter(mPagerAdapter);
        mViewPagerV.setOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public boolean isTabsEnabled() {
        return false;
    }

    @Override
    public OnPageChangeListener onCreateOnPageChangeListener() {
        return null;
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    @Override
    public ViewPager getViewPager() {
        return mViewPagerV;
    }

    @Override
    public OnPageChangeListener getOnPageChangeListener() {
        return mOnPageChangeListener;
    }

    @Override
    public TabListener onCreateTabListener() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TabListener getTabListener() {
        throw new UnsupportedOperationException();
    }
}

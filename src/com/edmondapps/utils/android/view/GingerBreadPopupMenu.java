/*
 * Copyright (C) 2010 The Android Open Source Project
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

import android.content.Context;
import android.view.View;

import com.actionbarsherlock.internal.view.menu.MenuBuilder;
import com.actionbarsherlock.internal.view.menu.MenuPopupHelper2;
import com.actionbarsherlock.internal.view.menu.MenuPresenter;
import com.actionbarsherlock.internal.view.menu.SubMenuBuilder;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

// Thank you ActionBarSherlock

/**
 * A PopupMenu displays a {@link Menu} in a modal popup window anchored to a
 * {@link View}.
 * The popup will appear below the anchor view if there is room, or above it if
 * there is not.
 * If the IME is visible the popup will not overlap it until it is touched.
 * Touching outside
 * of the popup will dismiss it.
 */
class GingerBreadPopupMenu extends PopupMenu implements MenuBuilder.Callback, MenuPresenter.Callback {
    private final Context mContext;
    private final MenuBuilder mMenu;
    private final View mAnchor;
    private final MenuPopupHelper2 mPopup;
    private OnMenuItemClickListener mMenuItemClickListener;
    private OnDismissListener mDismissListener;

    /**
     * Construct a new PopupMenu.
     * 
     * @param context
     *            Context for the PopupMenu.
     * @param anchor
     *            Anchor view for this popup. The popup will appear below the
     *            anchor if there
     *            is room, or above it if there is not.
     */
    public GingerBreadPopupMenu(Context context, View anchor) {
        mContext = context;
        mMenu = new MenuBuilder(context);
        mMenu.setCallback(this);
        mAnchor = anchor;
        mPopup = new MenuPopupHelper2(context, mMenu, anchor);
        mPopup.setCallback(this);
    }

    /**
     * Inflate a menu resource into this PopupMenu. This is equivalent to
     * calling
     * popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu()).
     * 
     * @param menuRes
     *            Menu resource to inflate
     */
    @Override
    public void inflate(int menuRes) {
        new MenuInflater(mContext).inflate(menuRes, mMenu);
    }

    /**
     * Show the menu popup anchored to the view specified during construction.
     * 
     * @see #dismiss()
     */
    @Override
    public void show() {
        mPopup.show();
    }

    /**
     * Dismiss the menu popup.
     * 
     * @see #show()
     */
    @Override
    public void dismiss() {
        mPopup.dismiss();
    }

    /**
     * Set a listener that will be notified when the user selects an item from
     * the menu.
     * 
     * @param listener
     *            Listener to notify
     */
    @Override
    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mMenuItemClickListener = listener;
    }

    /**
     * Set a listener that will be notified when this menu is dismissed.
     * 
     * @param listener
     *            Listener to notify
     */
    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        mDismissListener = listener;
    }

    /**
     * @hide
     */
    @Override
    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
        if (mMenuItemClickListener != null) {
            return mMenuItemClickListener.onMenuItemClick(item.getItemId());
        }
        return false;
    }

    /**
     * @hide
     */
    @Override
    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        if (mDismissListener != null) {
            mDismissListener.onDismiss(this);
        }
    }

    /**
     * @hide
     */
    @Override
    public boolean onOpenSubMenu(MenuBuilder subMenu) {
        if (subMenu == null) {
            return false;
        }

        if (!subMenu.hasVisibleItems()) {
            return true;
        }

        // Current menu will be dismissed by the normal helper, submenu will be
        // shown in its place.
        new MenuPopupHelper2(mContext, subMenu, mAnchor).show();
        return true;
    }

    /**
     * @hide
     */
    public void onCloseSubMenu(SubMenuBuilder menu) {
    }

    /**
     * @hide
     */
    @Override
    public void onMenuModeChange(MenuBuilder menu) {
    }
}

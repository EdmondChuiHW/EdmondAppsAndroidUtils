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
package com.edmondapps.utils.android.view;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

/**
 * @author Edmond
 * 
 */
class HoneyCombPupupMenu extends com.edmondapps.utils.android.view.PopupMenu {

    private final PopupMenu mPopupMenu;

    HoneyCombPupupMenu(Context c, View anchor) {
        mPopupMenu = new PopupMenu(c, anchor);
    }

    @Override
    public void inflate(int menuRes) {
        mPopupMenu.inflate(menuRes);
    }

    @Override
    public void show() {
        mPopupMenu.show();
    }

    @Override
    public void dismiss() {
        mPopupMenu.dismiss();
    }

    @Override
    public void setOnMenuItemClickListener(final OnMenuItemClickListener listener) {
        mPopupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return listener.onMenuItemClick(item.getItemId());
            }
        });
    }

    @Override
    public void setOnDismissListener(final OnDismissListener listener) {
        mPopupMenu.setOnDismissListener(new android.widget.PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                listener.onDismiss(HoneyCombPupupMenu.this);
            }
        });
    }
}

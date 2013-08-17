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
import android.view.View;

import com.edmondapps.utils.android.Utils;

/**
 * @author Edmond
 * 
 */
public abstract class PopupMenu {
    public static PopupMenu newInstance(Context context, View anchor) {
        if (Utils.hasHoneyComb()) {
            return new HoneyCombPupupMenu(context, anchor);
        }
        return new GingerBreadPopupMenu(context, anchor);
    }

    public interface OnDismissListener {
        public void onDismiss(PopupMenu menu);
    }

    public interface OnMenuItemClickListener {
        public boolean onMenuItemClick(int menuId);
    }

    public abstract void inflate(int menuRes);

    public abstract void show();

    public abstract void dismiss();

    public abstract void setOnMenuItemClickListener(OnMenuItemClickListener listener);

    public abstract void setOnDismissListener(OnDismissListener listener);
}

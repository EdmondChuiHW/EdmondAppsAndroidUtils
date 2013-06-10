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

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * @author Edmond
 * 
 */
public final class ViewUtils {
    private ViewUtils() {
        throw new AssertionError("nice try");
    }

    /**
     * Determines if a {@link TextView} contains empty text.
     * 
     * @see #isTextEmpty(TextView, int)
     * @see TextUtils#isEmpty(CharSequence)
     * @param e
     *            non-null instance of an {@code TextView}
     */
    public static boolean isTextEmpty(TextView e) {
        return TextUtils.isEmpty(e.getText().toString());
    }

    /**
     * Determines if a {@link TextView} contains empty text.
     * </br>
     * If it is empty, the {@code TextView} will display a error message, and
     * requests focus.
     * 
     * @see TextUtils#isEmpty(CharSequence)
     * @param errorString
     *            the resource id of {@code R.string.*}
     * @param e
     *            non-null instance of an {@code TextView}
     */
    public static boolean isTextEmpty(int errorString, TextView e) {
        boolean isEmpty = TextUtils.isEmpty(e.getText().toString());
        if (isEmpty) {
            e.setError(e.getContext().getText(errorString));
            e.requestFocus();
        }
        return isEmpty;
    }

    /**
     * Determines if multiple {@link TextView}s contain empty text.
     * </br>
     * If any one is empty, the {@code TextView} will display a error message,
     * and requests focus.
     * 
     * @see TextUtils#isEmpty(CharSequence)
     * @param errorString
     *            the resource id of {@code R.string.*}
     * @param views
     *            non-null instances of a {@code TextView}
     */
    public static boolean isAllTextNotEmpty(int errorString, TextView... views) {
        for (TextView v : views) {
            if (isTextEmpty(errorString, v)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Call {@link Activity#findViewById(int)} with the given parameters. The
     * return type of the {@code View} is inferred by the generic type V.
     * 
     * @throws ClassCastException
     *             if the view is not of type V.
     */
    // let it crash run-time!
    @SuppressWarnings("unchecked")
    public static <V extends View> V findView(Activity activity, int id) {
        return (V)activity.findViewById(id);
    }

    /**
     * Calls {@link View#findViewById(int)} with the given parameters. The
     * return type of the {@code View} is inferred by the generic type V.
     * 
     * @throws ClassCastException
     *             if the view is not of type V.
     */
    // let it crash run-time!
    @SuppressWarnings("unchecked")
    public static <V extends View> V findView(View parent, int id) {
        return (V)parent.findViewById(id);
    }
}

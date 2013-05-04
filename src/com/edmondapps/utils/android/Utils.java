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
package com.edmondapps.utils.android;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.edmondapps.utils.android.view.ViewUtils;

public final class Utils {
    private static final boolean HAS_JELLY_BEAN_MR1 = VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
    private static final boolean HAS_JELLY_BEAN = HAS_JELLY_BEAN_MR1 ? true : VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    private static final boolean HAS_ICE_CREAM_SANDWICH_MR1 = HAS_JELLY_BEAN ? true : VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    private static final boolean HAS_ICE_CREAM_SANDWICH = HAS_ICE_CREAM_SANDWICH_MR1 ? true : VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
    private static final boolean HAS_HONEY_COMB_MR2 = HAS_ICE_CREAM_SANDWICH ? true : VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR2;
    private static final boolean HAS_HONEY_COMB_MR1 = HAS_HONEY_COMB_MR2 ? true : VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    private static final boolean HAS_HONEY_COMB = HAS_HONEY_COMB_MR1 ? true : VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    private static final boolean HAS_GINGER_BREAD_MR1 = HAS_HONEY_COMB ? true : VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD_MR1;
    private static final boolean HAS_GINGER_BREAD = HAS_GINGER_BREAD_MR1 ? true : VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    private static final boolean HAS_FROYO = HAS_GINGER_BREAD ? true : VERSION.SDK_INT >= VERSION_CODES.FROYO;
    private static final boolean HAS_ECLAIR_MR1 = HAS_FROYO ? true : VERSION.SDK_INT >= VERSION_CODES.ECLAIR_MR1;
    private static final boolean HAS_ECLAIR_0_1 = HAS_ECLAIR_MR1 ? true : VERSION.SDK_INT >= VERSION_CODES.ECLAIR_0_1;
    private static final boolean HAS_ECLAIR = HAS_ECLAIR_0_1 ? true : VERSION.SDK_INT >= VERSION_CODES.ECLAIR;
    private static final boolean HAS_DOUNT = HAS_ECLAIR ? true : VERSION.SDK_INT >= VERSION_CODES.DONUT;

    private Utils() {
        throw new AssertionError("nice try");
    }

    /**
     * @see VERSION_CODES#DONUT
     */
    public static boolean hasDonut() {
        return HAS_DOUNT;
    }

    /**
     * @see VERSION_CODES#ECLAIR
     */
    public static boolean hasEclair() {
        return HAS_ECLAIR;
    }

    /**
     * @see VERSION_CODES#ECLAIR_0_1
     */
    public static boolean hasEclair_0_1() {
        return HAS_ECLAIR_0_1;
    }

    /**
     * @see VERSION_CODES#ECLAIR_MR1
     */
    public static boolean hasEclairMr1() {
        return HAS_ECLAIR_MR1;
    }

    /**
     * @see VERSION_CODES#FROYO
     */
    public static boolean hasFroyo() {
        return HAS_FROYO;
    }

    /**
     * @see VERSION_CODES#GINGERBREAD
     */
    public static boolean hasGingerBread() {
        return HAS_GINGER_BREAD;
    }

    /**
     * @see VERSION_CODES#GINGERBREAD_MR1
     */
    public static boolean hasGingerBreadMr1() {
        return HAS_GINGER_BREAD_MR1;
    }

    /**
     * @see VERSION_CODES#HONEYCOMB
     */
    public static boolean hasHoneyComb() {
        return HAS_HONEY_COMB;
    }

    /**
     * @see VERSION_CODES#HONEYCOMB_MR1
     */
    public static boolean hasHoneyCombMr1() {
        return HAS_HONEY_COMB_MR1;
    }

    /**
     * @see VERSION_CODES#HONEYCOMB_MR2
     */
    public static boolean hasHoneyCombMr2() {
        return HAS_HONEY_COMB_MR2;
    }

    /**
     * @see VERSION_CODES#ICE_CREAM_SANDWICH
     */
    public static boolean hasIceCreamSandwich() {
        return HAS_ICE_CREAM_SANDWICH;
    }

    /**
     * @see VERSION_CODES#ICE_CREAM_SANDWICH_MR1
     */
    public static boolean hasIceCreamSandwichMr1() {
        return HAS_ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * @see VERSION_CODES#JELLY_BEAN
     */
    public static boolean hasJellyBean() {
        return HAS_JELLY_BEAN;
    }

    /**
     * @see VERSION_CODES#JELLY_BEAN_MR1
     */
    public static boolean hasJellyBeanMr1() {
        return HAS_JELLY_BEAN_MR1;
    }

    /**
     * Call {@link Activity#findViewById(int)} with the given parameters. The
     * return type of the {@code View} is inferred by the generic type V.
     * 
     * @throws ClassCastException
     *             if the view is not of type V.
     * @deprecated Use {@link ViewUtils#findView(Activity, int)}
     */
    // let it crash run-time!
    @SuppressWarnings("unchecked")
    @Deprecated
    public static <V extends View> V findView(Activity activity, int id) {
        return (V)activity.findViewById(id);
    }

    /**
     * Calls {@link View#findViewById(int)} with the given parameters. The
     * return type of the {@code View} is inferred by the generic type V.
     * 
     * @throws ClassCastException
     *             if the view is not of type V.
     * @deprecated Use {@link ViewUtils#findView(View, int)}
     */
    // let it crash run-time!
    @SuppressWarnings("unchecked")
    @Deprecated
    public static <V extends View> V findView(View parent, int id) {
        return (V)parent.findViewById(id);
    }

    /**
     * Calls {@link FragmentManager#findFragmentByTag(String)} with the given
     * parameters. The return type of the {@code Fragment} is inferred by the
     * generic type F.
     * 
     * @throws ClassCastException
     *             if the view is not of type F.
     */
    // let it crash run-time!
    @SuppressWarnings("unchecked")
    public static <F extends Fragment> F findFragment(FragmentManager manager, String tag) {
        return (F)manager.findFragmentByTag(tag);
    }
}

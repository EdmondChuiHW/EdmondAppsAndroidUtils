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
package com.edmondapps.utils.android.animation;

import static com.nineoldandroids.view.ViewHelper.setScaleX;
import static com.nineoldandroids.view.ViewHelper.setScaleY;
import static com.nineoldandroids.view.ViewHelper.setTranslationX;
import static com.nineoldandroids.view.ViewHelper.setTranslationY;
import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;

/**
 * @author Edmond
 * 
 */
public class ViewAnimations {
    private ViewAnimations() {
        throw new AssertionError("nice try");
    }

    /**
     * Fades in the {@link Bitmap} with the current {@link Drawable}.
     * 
     * @param view
     *            an instance of {@link ImageView}
     * @param bitmap
     *            an instance of {@link Bitmap}
     * @param duration
     *            animation duration as per
     *            {@link TransitionDrawable#startTransition(int)}
     */
    public static void fadeInBitmap(ImageView view, Bitmap bitmap, int duration) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(view.getResources(), bitmap);

        Drawable drawable = view.getDrawable();
        TransitionDrawable transition = new TransitionDrawable(new Drawable[] {drawable, bitmapDrawable});
        transition.setCrossFadeEnabled(true);

        view.setImageDrawable(transition);
        transition.startTransition(duration);
    }

    /**
     * Scales up/down a new {@link Bitmap} into the {@link ImageView}.
     * 
     * @param v
     *            an instance of {@link ImageView}
     * @param b
     *            an instance of {@link Bitmap}
     * @param duration
     *            the animation duration
     */
    public static void scaleToBitmap(final ImageView v, Bitmap b, final long duration) {
        final int oldLeft = v.getLeft();
        final int oldTop = v.getTop();
        final int oldWidth = v.getWidth();
        final int oldHeight = v.getHeight();

        v.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);

                final int leftDelta = oldLeft - v.getLeft();
                final int topDelta = oldTop - v.getTop();
                final float widthScale = (float)oldWidth / v.getWidth();
                final float heightScale = (float)oldHeight / v.getHeight();

                setTranslationX(v, leftDelta);
                setTranslationY(v, topDelta);
                setScaleX(v, widthScale);
                setScaleY(v, heightScale);

                animate(v).setDuration(duration)
                        .scaleX(1)
                        .scaleY(1)
                        .translationX(0)
                        .translationY(0);

                return true;
            }
        });
        v.setImageBitmap(b);
    }
}

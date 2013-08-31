/*
 * Copyright (C), NexusPad LLC
 */
package com.edmondapps.utils.android.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import com.edmondapps.utils.android.R;

/**
 * Helper class to help selecting the right {@code Activity} to launch depending
 * on the screen size.
 * </p>
 * You may override {@link #onCreate7InchTabletIntent()} and/or
 * {@link #onCreate10InchTabletIntent()} to provide a different {@code Intent}.<br/>
 * The default implementation returns null, which falls back to
 * {@link #onCreatePhoneIntent()}.
 * 
 * @see R.bool#ed__has_7_inch
 * @see R.bool#ed__has_10_inch
 * 
 * @author Edmond
 * 
 */
public abstract class ForwardActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();

        Intent activityIntent = null;
        // order is important (10 inch also has 7 inch)
        if (res.getBoolean(R.bool.ed__has_10_inch)) {
            activityIntent = onCreate10InchTabletIntent();
        } else if (res.getBoolean(R.bool.ed__has_7_inch)) {
            activityIntent = onCreate7InchTabletIntent();
        }

        // is on phone/no implementation for tablets
        if (activityIntent == null) {
            activityIntent = onCreatePhoneIntent();
        }

        startActivity(activityIntent);
        finish();
    }

    /**
     * Creates an {@code Activity Intent} that is used for phones.</br>
     * It is also the default Intent if 7-inch or 10-inch implementation are not
     * available.
     * </p>
     * Returning null is an error.
     * 
     * @return a default {@code Activity Intent}
     */
    protected abstract Intent onCreatePhoneIntent();

    /**
     * Returning null indicates falling back to {@link #onCreatePhoneIntent()}.
     * 
     * @return an {@code Activity Intent} that is specialized for 7-inch devices
     */
    protected Intent onCreate7InchTabletIntent() {
        return null;
    }

    /**
     * Returning null indicates falling back to {@link #onCreatePhoneIntent()}.
     * 
     * @return an {@code Activity Intent} that is specialized for 10-inch
     *         devices
     */
    protected Intent onCreate10InchTabletIntent() {
        return null;
    }
}

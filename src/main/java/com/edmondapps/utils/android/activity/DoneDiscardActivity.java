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
import android.view.LayoutInflater;
import android.view.View;
import com.edmondapps.utils.android.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Uses a custom {@code ActionBar} view for done/discard buttons. <br>
 * Override {@link #onDonePressed()} and {@link #onDiscardPressed()} to perform any actions.
 * <p>
 * See the relevant G+ post from the Android Team,<br>
 * https://plus.google.com/+RomanNurik/posts/R49wVvcDoEW
 * 
 * @author Edmond
 * 
 */
public abstract class DoneDiscardActivity extends SinglePaneActivity {
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        if (!isDoneDiscardEnabled()) {
            return;
        }

        ActionBar actionBar = getActionBar();

        LayoutInflater inflater = LayoutInflater.from(actionBar.getThemedContext());
        View view = inflater.inflate(R.layout.ed__ab_custom_view_done_discard, null);
        view.findViewById(R.id.ed__ab_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDonePressed();
            }
        });
        view.findViewById(R.id.ed__ab_discard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDiscardPressed();
            }
        });

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    /**
     * 
     * @return false to completely disable the done/discard function
     */
    protected boolean isDoneDiscardEnabled() {
        return true;
    }

    /**
     * Called when the "Done" button is clicked.
     */
    protected void onDonePressed() {
    }

    /**
     * Called when the "Discard" button is clicked.
     * <p>
     * Calls {@link #finish()} by default.
     */
    protected void onDiscardPressed() {
        finish();
    }
}

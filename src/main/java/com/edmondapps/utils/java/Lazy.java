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
package com.edmondapps.utils.java;

/**
 * Simple class to improve readability of lazy initializations. </p>
 * It can also be used as a return value to move the computation to the caller.
 * 
 * <pre>
 * private final Lazy&lt;String&gt; mString = new Lazy&lt;String&gt;() {
 *     &#064;Override
 *     protected String onCreate() {
 *         return expensiveComputation();
 *     }
 * };
 * </pre>
 * 
 * @author Edmond
 * 
 */
public abstract class Lazy<T> {
    private T mT;
    private boolean mCreated;

    protected abstract T onCreate();

    public final T get() {
        if (!mCreated) {
            mT = onCreate();
            mCreated = true;
        }
        return mT;
    }

    public final boolean isCreated() {
        return mCreated;
    }
}

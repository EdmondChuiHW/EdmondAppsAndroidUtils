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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Edmond
 * 
 */
public class LazyTest {
    Lazy<String> mLazy;

    @Before
    public void setUp() {
        mLazy = new Lazy<String>() {
            @Override
            protected String onCreate() {
                return "Test Passed.";
            }
        };
    }

    @Test
    public void test() {
        assertFalse(mLazy.isCreated());
        assertEquals("Test Passed.", mLazy.get());
        assertTrue(mLazy.isCreated());
    }
}

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Edmond
 * 
 */
public class IdTest {
    private static final long MAX_ID = 10L;
    private Id[] mArray;
    private Set<Id> mSet;

    @Before
    public void setUp() {
        mArray = new Id[] {Id.of(5L), Id.of(4L), Id.of(7L), Id.of(1L), Id.of(3L),
                Id.of(2L), Id.of(6L), Id.of(10L), Id.of(0L), Id.of(9L), Id.of(8L)};

        mSet = Collections.unmodifiableSet(new HashSet<Id>(Arrays.asList(mArray)));
    }

    @Test
    public void testComparator() {
        ArrayList<Id> list = new ArrayList<Id>(mSet);
        Collections.sort(list, IdUtils.ID_COMPARATOR);
        assertTrue(isSorted(list));
    }

    @Test
    public void testComparable() {
        TreeSet<Id> treeSet = new TreeSet<Id>(mSet);
        assertTrue(isSorted(treeSet));
    }

    public boolean isSorted(Iterable<Id> iterable) {
        Iterator<Id> it = iterable.iterator();
        for (long i = 0; i <= MAX_ID; ++i) {
            Id next = it.next();
            if (next.getId() != i) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void listGetId() {
        Id id = IdUtils.searchById(mSet, 5L);
        assertNotNull(id);
        assertEquals(id.getId(), 5L);
    }

    @Test
    public void listNoId() {
        Id id = IdUtils.searchById(mSet, 100L);
        assertNull(id);
    }

    @Test
    public void arrayGetId() {
        Id id = IdUtils.searchById(mArray, 5L);
        assertNotNull(id);
        assertEquals(id.getId(), 5L);
    }

    @Test
    public void arrayNoId() {
        Id id = IdUtils.searchById(mArray, 100L);
        assertNull(id);
    }

    private static class Id implements Identifiable, Comparable<Id> {
        private static Id of(long id) {
            return new Id(id);
        }

        private final long mId;

        private Id(long id) {
            mId = id;
        }

        @Override
        public long getId() {
            return mId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = (prime * result) + (int) (mId ^ (mId >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (! (obj instanceof Id)) {
                return false;
            }
            Id other = (Id)obj;
            if (mId != other.mId) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Id another) {
            long oId = another.mId;
            if (mId > oId) {
                return 1;
            }
            if (mId < oId) {
                return -1;
            }

            return 0;
        }
    }
}

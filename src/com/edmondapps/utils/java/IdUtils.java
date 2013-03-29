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

import java.util.Collection;
import java.util.Comparator;

public final class IdUtils {
	private IdUtils() {
		throw new AssertionError("nice try");
	}

	/**
	 * Performs a linear search of the given id. No id checks are performed on
	 * null elements.
	 * 
	 * @param c
	 *            a type of {@link Iterable}, usually a {@link Collection}
	 * @param id
	 *            a long id to check against {@link Identifiable#getId()}.
	 * @return the first element that has the same given id, or null.
	 */
	public static <T extends Identifiable> T searchById(Iterable<? extends T> c, long id) {
		for (T t : c) {
			if (t == null) {
				continue;
			}
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Array version of {@link #searchById(Iterable, long)}.
	 */
	// Arrays are covariant; A[] is a type of T[] where A extends T.
	public static <T extends Identifiable> T searchById(T[] c, long id) {
		for (T t : c) {
			if (t == null) {
				continue;
			}
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}

	/**
	 * A {@link Comparator} that sorts two {@link Identifiable} instances by
	 * their IDs.
	 */
	public static final Comparator<Identifiable> ID_COMPARATOR = new Comparator<Identifiable>() {
		@Override
		public int compare(Identifiable first, Identifiable second) {
			long id1 = first.getId();
			long id2 = second.getId();
			if (id1 > id2) {
				return 1;
			}
			if (id1 < id2) {
				return -1;
			}
			return 0;
		}
	};
}

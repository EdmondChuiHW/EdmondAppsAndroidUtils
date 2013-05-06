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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * "Casts" a list to a different generic type.
 * </p>
 * This is useful if you know <i>for sure</i> that all the elements in the list
 * is the new type.
 * 
 * @author Edmond
 * 
 */
public class WrapperList<E> implements List<E> {
    @SuppressWarnings("rawtypes")
    private final List mList;

    public WrapperList(List<?> list) {
        mList = list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(int location, E object) {
        mList.add(location, object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E object) {
        return mList.add(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int location, Collection<? extends E> collection) {
        return mList.addAll(location, collection);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection<? extends E> collection) {
        return mList.addAll(collection);
    }

    @Override
    public void clear() {
        mList.clear();
    }

    @Override
    public boolean contains(Object object) {
        return mList.contains(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsAll(Collection<?> collection) {
        return mList.containsAll(collection);
    }

    @Override
    public boolean equals(Object object) {
        return mList.equals(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int location) {
        return (E)mList.get(location);
    }

    @Override
    public int hashCode() {
        return mList.hashCode();
    }

    @Override
    public int indexOf(Object object) {
        return mList.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return mList.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return mList.lastIndexOf(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListIterator<E> listIterator() {
        return mList.listIterator();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListIterator<E> listIterator(int location) {
        return mList.listIterator(location);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int location) {
        return (E)mList.remove(location);
    }

    @Override
    public boolean remove(Object object) {
        return mList.remove(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeAll(Collection<?> collection) {
        return mList.removeAll(collection);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean retainAll(Collection<?> collection) {
        return mList.retainAll(collection);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int location, E object) {
        return (E)mList.set(location, object);
    }

    @Override
    public int size() {
        return mList.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> subList(int start, int end) {
        return mList.subList(start, end);
    }

    @Override
    public Object[] toArray() {
        return mList.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        return (T[])mList.toArray(array);
    }
}

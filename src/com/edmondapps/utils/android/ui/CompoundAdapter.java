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
package com.edmondapps.utils.android.ui;

import java.util.ArrayList;
import java.util.List;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

/**
 * An adapter that combines multiple adapters.
 * </p>
 * Simply pass the adapters in the desired order into one of the static factory
 * methods. They will be displayed from top to bottom. No special
 * requirements/changes in the adapters are needed.
 * </p>
 * In most use cases (like a callback from a {@code ListView}, you will use
 * {@link #getAdapter(int)} to retrieve the adapter
 * that is responsible for the position, and use
 * {@link #getPositionForAdapter(int)} to get the position for the adapter
 * (instead of the position in the {@code CoumpoundAdapter}).
 * <p>
 * You may also use {@link #getAdapterInfo(int)} and
 * {@link #getPositionForAdapter(int, AdapterInfo)} if the situation requires.
 * 
 * @author Edmond
 * 
 */
public class CompoundAdapter extends BaseAdapter {

    /**
     * A class that holds extra info about an adapter.
     * </p>
     * Most of the time you don't have to interact with it, however, a common
     * use case is shown below, <code>
     * <pre>
public Object getItem(int position) {                              
    AdapterInfo info = getAdapterInfo(position);                   
    int positionForAdapter = getPositionForAdapter(position, info);
    return info.getAdapter().getItem(positionForAdapter);          
}                                                                  
     * </pre>
     * </code>
     * 
     * @author Edmond
     * 
     */
    public static final class AdapterInfo {
        private final BaseAdapter mAdapter;

        private int mPosition;
        private int mViewTypeOffset;

        private AdapterInfo(BaseAdapter adapter) {
            if (adapter == null) {
                throw new NullPointerException("adapter can't be null");
            }
            mAdapter = adapter;
        }

        /**
         * 
         * @return the adapter that this {@code AdapterInfo} is about
         */
        public BaseAdapter getAdapter() {
            return mAdapter;
        }

        private int getCount() {
            return getAdapter().getCount();
        }

        private int getViewTypeCount() {
            return getAdapter().getViewTypeCount();
        }

        /**
         * 
         * @return the position of this adapter in the {@code CompoundAdapter}
         */
        public final int getPosition() {
            return mPosition;
        }

        /**
         * 
         * @see ListAdapter#getItemViewType(int)
         * @return the internal view type offset that is used to distinguish
         *         view types
         *         of different adapters
         */
        public final int getViewTypeOffset() {
            return mViewTypeOffset;
        }

        private void setPosition(int position) {
            mPosition = position;
        }

        private void setViewTypeOffset(int viewTypeOffset) {
            mViewTypeOffset = viewTypeOffset;
        }
    }

    private final List<AdapterInfo> mAdapters = new ArrayList<CompoundAdapter.AdapterInfo>(2);

    /**
     * Constructs a {@code CompoundAdapter} that contains a single adapter. You
     * may use {@link #addAdapter(BaseAdapter)} later on.
     * 
     * @param adapter
     *            a non-null instance of a {@code BaseAdapter}
     */
    public CompoundAdapter(BaseAdapter adapter) {
        AdapterInfo info = new AdapterInfo(adapter);
        mAdapters.add(info);

        updateOffset();
    }

    /**
     * Constructs a {@code CompoundAdapter} that contains a 2 adapters. The
     * parameters order determines the order of the list from top to bottom.
     * 
     * @param adapter
     *            the first non-null instance of a {@code BaseAdapter}
     * @param adapter2
     *            the second non-null instance of a {@code BaseAdapter}
     */
    public CompoundAdapter(BaseAdapter adapter, BaseAdapter adapter2) {
        AdapterInfo info = new AdapterInfo(adapter);
        AdapterInfo info2 = new AdapterInfo(adapter2);
        mAdapters.add(info);
        mAdapters.add(info2);

        updateOffset();
    }

    /**
     * Constructs a {@code CompoundAdapter} that contains more than 2 adapters.
     * The parameters order determines the order of the list from top to bottom.
     * 
     * @param adapter
     *            the first non-null instance of a {@code BaseAdapter}
     * @param adapter2
     *            the second non-null instance of a {@code BaseAdapter}
     * @param adapters
     *            the other instances of {@code BaseAdapter}
     */
    public CompoundAdapter(BaseAdapter adapter, BaseAdapter adapter2, BaseAdapter... adapters) {
        AdapterInfo info = new AdapterInfo(adapter);
        AdapterInfo info2 = new AdapterInfo(adapter2);

        mAdapters.add(info);
        mAdapters.add(info2);

        for (BaseAdapter baseAdapter : adapters) {
            mAdapters.add(new AdapterInfo(baseAdapter));
        }

        updateOffset();
    }

    /**
     * 
     * @param position
     *            the position of the {@code CompoundAdapter}
     * @param info
     *            usually retrieved by {@link #getAdapterInfo(int)}
     * @return the position of the adapter contained in the {@code AdapterInfo}
     */
    public final static int getPositionForAdapter(int position, AdapterInfo info) {
        return position - info.getPosition();
    }

    /**
     * a shorthand for
     * {@code getPositionForAdapter(position, getAdapterInfo(position))}
     */
    public final int getPositionForAdapter(int position) {
        return getPositionForAdapter(position, getAdapterInfo(position));
    }

    /**
     * Append an adapter to the end of this {@code CompoundAdapter}.
     * 
     * @param adapter
     *            a non-null instance of a {@code BaseAdapter}
     */
    public void addAdapter(BaseAdapter adapter) {
        mAdapters.add(new AdapterInfo(adapter));
        notifyDataSetChanged();
    }

    private void updateOffset() {
        int count = 0;
        int viewTypeCount = 0;

        for (AdapterInfo adapterInfo : mAdapters) {
            adapterInfo.setPosition(count);
            adapterInfo.setViewTypeOffset(viewTypeCount);

            count += adapterInfo.getCount();
            viewTypeCount += adapterInfo.getViewTypeCount();
        }
    }

    /**
     * 
     * @param position
     *            position of the {@code CompoundAdapter}
     * @return the info about the adapter that is managing this position
     */
    public final AdapterInfo getAdapterInfo(int position) {
        // from bottom to top
        for (int i = mAdapters.size() - 1; i >= 0; --i) {
            AdapterInfo info = mAdapters.get(i);
            if (info.getPosition() <= position) {
                return info;
            }
        }
        throw new AssertionError("the impossible bug does exist");
    }

    /**
     * a shorthand for {@code getAdapterInfo(position).getAdapter()}
     */
    public final BaseAdapter getAdapter(int position) {
        return getAdapterInfo(position).getAdapter();
    }

    @Override
    public boolean hasStableIds() {
        for (AdapterInfo info : mAdapters) {
            if (!info.getAdapter().hasStableIds()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        for (AdapterInfo info : mAdapters) {
            info.getAdapter().registerDataSetObserver(observer);
        }
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        for (AdapterInfo info : mAdapters) {
            info.getAdapter().unregisterDataSetObserver(observer);
        }
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        for (AdapterInfo info : mAdapters) {
            info.getAdapter().notifyDataSetChanged();
        }
        updateOffset();
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        for (AdapterInfo info : mAdapters) {
            info.getAdapter().notifyDataSetInvalidated();
        }
        super.notifyDataSetInvalidated();
    }

    @Override
    public boolean areAllItemsEnabled() {
        for (AdapterInfo info : mAdapters) {
            if (!info.getAdapter().areAllItemsEnabled()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        AdapterInfo info = getAdapterInfo(position);
        return info.getAdapter().isEnabled(getPositionForAdapter(position, info));
    }

    @Override
    public int getItemViewType(int position) {
        AdapterInfo info = getAdapterInfo(position);
        int pos = getPositionForAdapter(position, info);
        return info.getAdapter().getItemViewType(pos) + info.getViewTypeOffset();
    }

    @Override
    public int getViewTypeCount() {
        int count = 0;
        for (AdapterInfo info : mAdapters) {
            count += info.getAdapter().getViewTypeCount();
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        for (AdapterInfo info : mAdapters) {
            if (!info.getAdapter().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getCount() {
        return getCountInternal();
    }

    private int getCountInternal() {
        int count = 0;
        for (AdapterInfo info : mAdapters) {
            count += info.getCount();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        AdapterInfo info = getAdapterInfo(position);
        int positionForAdapter = getPositionForAdapter(position, info);
        return info.getAdapter().getItem(positionForAdapter);
    }

    @Override
    public long getItemId(int position) {
        AdapterInfo info = getAdapterInfo(position);
        return info.getAdapter().getItemId(getPositionForAdapter(position, info));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterInfo info = getAdapterInfo(position);
        return info.getAdapter().getView(getPositionForAdapter(position, info), convertView, parent);
    }
}

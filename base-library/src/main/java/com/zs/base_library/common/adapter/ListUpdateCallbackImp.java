package com.zs.base_library.common.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * des 替换 arr 中 AdapterListUpdateCallback
 *     仅更改 onMoved()实现
 * author zs
 * date 2021/4/12
 */
public class ListUpdateCallbackImp implements androidx.recyclerview.widget.ListUpdateCallback {
    @NonNull
    private final RecyclerView.Adapter mAdapter;

    /**
     * Creates an AdapterListUpdateCallback that will dispatch update events to the given adapter.
     *
     * @param adapter The Adapter to send updates to.
     */
    public ListUpdateCallbackImp(@NonNull RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    /** {@inheritDoc} */
    @Override
    public void onInserted(int position, int count) {
        mAdapter.notifyItemRangeInserted(position, count);
        if (mAdapter instanceof BaseDiffAdapter){
            BaseDiffAdapter adapter = (BaseDiffAdapter) mAdapter;
            if (adapter.getRecyclerView() == null || position != 0){
                return;
            }
            adapter.getRecyclerView().getLayoutManager().scrollToPosition(0);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onRemoved(int position, int count) {
        mAdapter.notifyItemRangeRemoved(position, count);
    }

    /** {@inheritDoc} */
    @Override
    public void onMoved(int fromPosition, int toPosition) {
        mAdapter.notifyDataSetChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void onChanged(int position, int count, Object payload) {
        mAdapter.notifyItemRangeChanged(position, count, payload);
    }
}

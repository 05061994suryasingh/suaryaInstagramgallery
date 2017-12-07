package com.example.admin.instagramgalery.adapter;

/*
 * Created by Guillaume on 17/11/2016.
 */

import android.content.Context;
import android.view.ViewGroup;
import com.example.admin.instagramgalery.galery.GridAdapterListener;
import com.example.admin.instagramgalery.galery.MediaItemView;
import com.example.admin.instagramgalery.galery.MediaItemViewListener;
import java.io.File;
import java.lang.ref.WeakReference;

public class GridAdapter extends RecyclerViewAdapterBase<File, MediaItemView> implements MediaItemViewListener {

    private final Context context;

    public GridAdapter(Context context) {
        this.context = context;
    }

    private WeakReference<GridAdapterListener> mWrListener;

    public void setListener(GridAdapterListener listener) {
        this.mWrListener = new WeakReference<>(listener);
    }

    @Override
    protected MediaItemView onCreateItemView(ViewGroup parent, int viewType) {
        return new MediaItemView(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<MediaItemView> viewHolder, final int position) {
        MediaItemView itemView = viewHolder.getView();
        itemView.setListener(this);
        itemView.bind(mItems.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onClickItem(File file) {
        mWrListener.get().onClickMediaItem(file);
    }
}
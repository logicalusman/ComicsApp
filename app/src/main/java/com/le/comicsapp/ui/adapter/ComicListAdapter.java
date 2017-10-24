package com.le.comicsapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.le.comicsapp.R;
import com.le.comicsapp.ui.viewholder.ComicListViewHolder;
import com.le.comicsapp.viewmodel.data.ComicItem;

import java.util.List;

/**
 * Comic list adapter impl.
 *
 * @author Usman
 */

public class ComicListAdapter extends RecyclerView.Adapter<ComicListViewHolder> {

    private List<ComicItem> mComicItemsList;
    private Context mCtx;
    private ComicListViewHolder.ComicListListener mListener;

    public ComicListAdapter(@NonNull Context ctx, @NonNull List<ComicItem> comicItemsList, @NonNull ComicListViewHolder.ComicListListener listener) {
        mCtx = ctx;
        mComicItemsList = comicItemsList;
        mListener = listener;
    }


    @Override
    public ComicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.comic_item_row, parent, false);
        return new ComicListViewHolder(mCtx, v, mListener);
    }

    @Override
    public void onBindViewHolder(ComicListViewHolder holder, int position) {
        ComicItem item = mComicItemsList.get(position);
        holder.setComicItem(item);
    }

    @Override
    public int getItemCount() {
        if (mComicItemsList == null) {
            return 0;
        } else {
            return mComicItemsList.size();
        }
    }


}

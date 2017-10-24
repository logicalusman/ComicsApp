package com.le.comicsapp.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.le.comicsapp.R;
import com.le.comicsapp.viewmodel.data.ComicItem;

/**
 * ViewHolder impl for comic list
 *
 * @author Usman
 */

public class ComicListViewHolder extends RecyclerView.ViewHolder {

    public interface ComicListListener {
        void onComicItemRowClicked(ComicItem comicItem);
    }

    private TextView mTitleTv;
    private ImageView mThumbnailIv;
    private ConstraintLayout mRootLayoutCl;
    private Context mCtx;
    private ComicItem mComicItem;
    private ComicListListener mListener;

    public ComicListViewHolder(@NonNull Context ctx, @NonNull View v, @NonNull final ComicListListener listener) {
        super(v);
        mCtx = ctx;
        mListener = listener;
        mRootLayoutCl = (ConstraintLayout) v.findViewById(R.id.root_layout_cl);
        mTitleTv = (TextView) v.findViewById(R.id.title_tv);
        mThumbnailIv = (ImageView) v.findViewById(R.id.thumbnail_iv);

        mRootLayoutCl.setOnClickListener((view) -> {
            mListener.onComicItemRowClicked(mComicItem);
        });
    }

    public void setComicItem(@NonNull ComicItem comicItem) {
        mComicItem = comicItem;
        mTitleTv.setText(comicItem.getTitle());
        Glide.with(mCtx).load(comicItem.getThumbnail()).into(mThumbnailIv);

    }

}

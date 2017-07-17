package com.le.comicsapp.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.le.comicsapp.R;
import com.le.comicsapp.util.UiUtils;
import com.le.comicsapp.viewmodel.ComicListViewModel;
import com.le.comicsapp.viewmodel.data.AppError;
import com.le.comicsapp.viewmodel.data.ComicItem;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

public class ComicListActivity extends CommonActivity {

    private RecyclerView mComicList;
    private ComicListViewModel mViewModel;
    private ComicListAdapter mComicListAdapter;
    private Subscription mSubscription;
    private AlertDialog mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        mViewModel = ViewModelProviders.of(this).get(ComicListViewModel.class);
        initComicList();
        mSubscription = mViewModel.getComicItemObservable().subscribe(new ComicListSubscriber());
        showProcessingDialog(getString(R.string.fetching_comic_list));
        mViewModel.getComicListAsync();

    }

    private void initComicList() {
        mComicList = (RecyclerView) findViewById(R.id.comics_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mComicList.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mComicList.getContext(),
                LinearLayoutManager.VERTICAL);
        mComicList.addItemDecoration(dividerItemDecoration);

    }

    private void populateComicList(List<ComicItem> comicItemsList) {
        mComicListAdapter = new ComicListAdapter(comicItemsList);
        mComicList.setAdapter(mComicListAdapter);
    }

    private class ComicListSubscriber extends Subscriber<List<ComicItem>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onNext(List<ComicItem> comicItemsList) {
            dismissProcessingDialog();
            if (comicItemsList == null) {
                showNoItemFoundDialog();
                return;
            }

            populateComicList(comicItemsList);
        }

        @Override
        public void onError(Throwable e) {
            dismissProcessingDialog();
            if (e instanceof AppError) {
                AppError err = (AppError) e;
                if (err.getError() == AppError.ERROR_INTERNET_CONNECTION) {
                    UiUtils.showConnectionAlertErrDialog(ComicListActivity.this);
                } else {
                    UiUtils.showUnknownErrDialog(ComicListActivity.this);
                }
            }
        }
    }

    private class ComicListAdapter extends RecyclerView.Adapter<ComicListAdapter.VH> {

        private List<ComicItem> mComicItemsList;

        public ComicListAdapter(@NonNull List<ComicItem> comicItemsList) {
            mComicItemsList = comicItemsList;
        }


        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ComicListActivity.this).inflate(R.layout.comic_item_row, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            ComicItem item = mComicItemsList.get(position);
            holder.mTitleTv.setText(item.getTitle());
            Glide.with(ComicListActivity.this).load(item.getThumbnail()).into(holder.mThumbnailIv);
        }

        @Override
        public int getItemCount() {
            if (mComicItemsList == null) {
                return 0;
            } else {
                return mComicItemsList.size();
            }
        }

        class VH extends RecyclerView.ViewHolder {

            private TextView mTitleTv;
            private ImageView mThumbnailIv;

            public VH(View v) {
                super(v);
                mTitleTv = (TextView) v.findViewById(R.id.title_tv);
                mThumbnailIv = (ImageView) v.findViewById(R.id.thumbnail_iv);
            }
        }
    }

    private void showNoItemFoundDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false)
                .setMessage(R.string.no_comic_items_found).
                        setPositiveButton(R.string.ok, null);

        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // This will make sure the dialog is dismissed when the activity finishes while
        // the dialog is in progress, e.g. when the screen is rotated.
        dismissProcessingDialog();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}

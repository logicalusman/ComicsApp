package com.le.comicsapp.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.le.comicsapp.R;
import com.le.comicsapp.ui.adapter.ComicListAdapter;
import com.le.comicsapp.ui.viewholder.ComicListViewHolder;
import com.le.comicsapp.util.UiUtils;
import com.le.comicsapp.viewmodel.ComicListViewModel;
import com.le.comicsapp.viewmodel.data.AppError;
import com.le.comicsapp.viewmodel.data.ComicItem;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Displays comic list. The UI for comic list is mostly dumb and is meant to rely on
 * its viewmodel for majority of its work.
 *
 * @author
 */
public class ComicListActivity extends CommonActivity implements ComicListViewHolder.ComicListListener {

    private RecyclerView mComicList;
    private ComicListViewModel mViewModel;
    private ComicListAdapter mComicListAdapter;
    private Subscription mSubscription;
    private AlertDialog mAlertDialog, mErrorDialog;


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
        mComicListAdapter = new ComicListAdapter(this, comicItemsList, this);
        mComicList.setAdapter(mComicListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comic_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_desc) {
            String msg = getString(R.string.total_number_of_comic_pages);
            msg = String.format("%s %d", msg, mViewModel.getNumPages());
            mAlertDialog = UiUtils.showDialog(this, null, msg);
        } else if (id == R.id.action_filter) {
            getBudgetPriceFromUser();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getBudgetPriceFromUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.enter_your_budget).setCancelable(false);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            String value = input.getText().toString();
            try {
                Double budget = Double.parseDouble(value);
                mViewModel.filterComicList(budget);
            } catch (NumberFormatException e) {
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    @Override
    public void onComicItemRowClicked(ComicItem comicItem) {
        launchComicInfoActivity(comicItem);
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
                    mErrorDialog = UiUtils.showConnectionAlertErrDialog(ComicListActivity.this);
                } else {
                    mErrorDialog = UiUtils.showUnknownErrDialog(ComicListActivity.this);
                }
            }
        }
    }

    public void launchComicInfoActivity(@NonNull ComicItem item) {
        Intent i = new Intent(this, ComicInfoActivity.class);
        i.putExtra(ComicInfoActivity.EXTRA_COMIC_ITEM, item);
        startActivity(i);
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
        dismissDialog(mAlertDialog);
        dismissDialog(mErrorDialog);
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    private void dismissDialog(AlertDialog alertDialog) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}

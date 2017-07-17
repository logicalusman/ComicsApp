package com.le.comicsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.le.comicsapp.R;
import com.le.comicsapp.network.ApiService;
import com.le.comicsapp.network.RestAdapter;
import com.le.comicsapp.network.data.ComicAuthor;
import com.le.comicsapp.network.data.ComicData;
import com.le.comicsapp.network.data.GetComicsResponse;
import com.le.comicsapp.util.Utils;
import com.le.comicsapp.viewmodel.data.AppError;
import com.le.comicsapp.viewmodel.data.ComicItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class ComicListViewModel extends AndroidViewModel {

    private String TAG = "ComicListViewModel";
    private PublishSubject<List<ComicItem>> mComicItemsSubject = PublishSubject.create();
    private Subscription mSubscription;
    private boolean mComicsRequested;
    private List<ComicItem> mComicItemsList;

    public ComicListViewModel(Application application) {
        super(application);
    }

    public PublishSubject<List<ComicItem>> getComicItemObservable() {
        return mComicItemsSubject;
    }

    public void getComicListAsync() {

        // If the items were network requested earlier, they must be cached - just use them.
        if (mComicsRequested) {
            mComicItemsSubject.onNext(mComicItemsList);
            return;
        }

        Retrofit retrofit = RestAdapter.createRetrofit(getApplication());
        ApiService apiService = retrofit.create(ApiService.class);
        String apiPubkey = getApplication().getString(R.string.marvel_api_pub_key);
        String apiPriKey = getApplication().getString(R.string.marvel_api_pri_key);
        int limit = getApplication().getResources().getInteger(R.integer.comics_limit);
        /**
         * Strangely, changing timestamp every time (request) is not accepted by the marvell api.
         * TODO:
         * Timestamp hardcoded as of yet. It needs to be resolved later to the following,
         *
         * String ts = String.format("%d", System.currentTimeMillis());
         */
        // hardcoding timestamp, it should be a different value for each request, so that a
        // a different hash can be generated.
        String ts = "1500299975613";
        String hash = Utils.md5HashOf(String.format("%s%s%s", ts, apiPriKey, apiPubkey));

        mSubscription = apiService.getComics(apiPubkey, ts, hash, limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        (response) -> {
                            mComicsRequested = true;
                            // check network error
                            if (!response.isSuccessful()) {
                                mComicItemsSubject.onError((new AppError(AppError.ERROR_INTERNET_CONNECTION)));
                                return;
                            }
                            // check the response data
                            if (response.body() == null) {
                                mComicItemsSubject.onNext(null);
                                return;
                            }
                            // process response data and pass it to the view
                            mComicItemsSubject.onNext(processComicData(response.body()));
                        },

                        throwable -> {
                            Log.e(TAG, throwable.getMessage(), throwable);
                            mComicItemsSubject.onError((new AppError(throwable, AppError.ERROR_UNKNOWN)));
                            mComicsRequested = true;
                        }
                );

    }

    private List<ComicItem> processComicData(@NonNull GetComicsResponse response) {

        List<ComicItem> items = new ArrayList<>(response.responseData.comicDataList.size());
        for (ComicData data : response.responseData.comicDataList) {
            ComicItem ci = new ComicItem();
            ci.setTitle(data.title);
            ci.setDescription(data.description);
            ci.setNumPages(data.pageCount);
            if (data.prices != null && !data.prices.isEmpty()) {
                ci.setPrice(data.prices.get(0).price);
            }
            String thumbnail = String.format("%s.%s", data.thumbnail.path, data.thumbnail.extension);
            ci.setThumbnail(thumbnail);
            if (data.author.numAuthors > 0) {
                for (ComicAuthor.Author author : data.author.authorList) {
                    ci.addAuthor(author.name);
                }
            }

            items.add(ci);
        }
        mComicItemsList = items;
        return items;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}

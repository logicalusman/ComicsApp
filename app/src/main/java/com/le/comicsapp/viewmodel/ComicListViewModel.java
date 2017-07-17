package com.le.comicsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import com.le.comicsapp.R;
import com.le.comicsapp.network.ApiService;
import com.le.comicsapp.network.RestAdapter;
import com.le.comicsapp.util.Utils;

import retrofit2.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ComicListViewModel extends AndroidViewModel {

    private String TAG = "ComicListViewModel";
    private Subscription mSubscription;

    public ComicListViewModel(Application application) {
        super(application);
    }

    public void getComicListAsync() {
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


                        },

                        throwable -> {

                            Log.e(TAG, throwable.getMessage(), throwable);

                        }
                );

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

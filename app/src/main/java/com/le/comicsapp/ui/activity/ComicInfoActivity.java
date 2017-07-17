package com.le.comicsapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.le.comicsapp.R;
import com.le.comicsapp.viewmodel.data.ComicItem;

public class ComicInfoActivity extends AppCompatActivity {

    public static final String EXTRA_COMIC_ITEM = "com.le.comicsapp.extra_comic_item";
    private ComicItem mComicItem;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_info);
        mComicItem = getIntent().getParcelableExtra(EXTRA_COMIC_ITEM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.comic_details);
    }
}

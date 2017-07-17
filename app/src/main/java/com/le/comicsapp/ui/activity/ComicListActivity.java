package com.le.comicsapp.ui.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.le.comicsapp.R;
import com.le.comicsapp.viewmodel.ComicListViewModel;

public class ComicListActivity extends CommonActivity {

    private RecyclerView mComicsList;
    private ComicListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        mComicsList = (RecyclerView) findViewById(R.id.comics_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mViewModel = ViewModelProviders.of(this).get(ComicListViewModel.class);
        mViewModel.getComicListAsync();

    }
}

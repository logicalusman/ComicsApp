package com.le.comicsapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.le.comicsapp.ui.activity.ComicInfoActivity;
import com.le.comicsapp.ui.activity.ComicListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class InstrumentedUITest {
    @Rule
    public ActivityTestRule<ComicListActivity> comicListActivityRule = new ActivityTestRule<>(ComicListActivity.class);

    @Rule
    public ActivityTestRule<ComicInfoActivity> comicInfoActivityRule = new ActivityTestRule<>(ComicInfoActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.le.comicsapp", appContext.getPackageName());
    }

    @Test
    public void testComicListActivity() throws Exception {
        ComicListActivity activity = comicListActivityRule.getActivity();
        View view = activity.findViewById(R.id.comics_rv);
        assertThat(view, notNullValue());
        assertThat(view, instanceOf(RecyclerView.class));
        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertEquals(adapter.getItemCount(), 100);
    }

}

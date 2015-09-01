package com.demo.androiddesignlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;


public class ScrollableActivity1 extends AppCompatActivity
{
    private static final String TAG = ScrollableActivity1.class.getSimpleName();

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar toolbar;
    private Menu menu;
    private SearchBox searchbox;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollable_activity1);

        initToolbar();
        initSearchViews();
        initViews();
    }

    private void initViews()
    {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener()
        {
            @Override
            public void onScrollChanged()
            {
                int scrollY = nestedScrollView.getScrollY();
                Log.d(TAG, "====onScrollChanged===" + scrollY);
                //closeSearch();
            }
        });
    }
    private void initSearchViews()
    {
        searchbox = (SearchBox) findViewById(R.id.searchbox);
        searchbox.enableVoiceRecognition(this);
        searchbox.setLogoText(getString(R.string.search_hint));

        searchbox.setSearchListener(new SearchBox.SearchListener()
        {
            @Override
            public void onSearchOpened()
            {
                Log.d(TAG, "===onSearchOpened===");
            }

            @Override
            public void onSearchClosed()
            {
                Log.d(TAG, "===onSearchClosed===");
                closeSearch();
            }

            @Override
            public void onSearchTermChanged()
            {
                //React to the search term changing
                //Called after it has updated results
                Log.d(TAG, "===onSearchTermChanged===");
            }

            @Override
            public void onSearch(String searchTerm)
            {
                Log.d(TAG, "===onSearch===" + searchTerm);
                setActivityTitle(searchTerm);
            }

            @Override
            public void onSearchCleared()
            {
                //Called when the clear button is clicked
                Log.d(TAG, "===onSearchCleared===");
            }
        });


        searchbox.setMenuListener(new SearchBox.MenuListener()
        {
            @Override
            public void onMenuClick()
            {
                //Hamburger has been clicked
                Log.d(TAG, "===onMenuClick===");
            }
        });
    }

    Handler handler = new Handler();
    private void showHideToolbarMenuIcons(final boolean isSearchViewOpened)
    {
        getSupportActionBar().setHomeButtonEnabled(!isSearchViewOpened);
        getSupportActionBar().setDisplayHomeAsUpEnabled(!isSearchViewOpened);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        if(searchMenuItem != null)
        {
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    searchMenuItem.setVisible(!isSearchViewOpened);
                }
            }, 250);

        }
    }

    /* Prevent memory leaks:
    */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private void initToolbar()
    {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        setActivityTitle(null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setActivityTitle(final String title)
    {
        if(title == null)
        {
            mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        }
        else
        {
            mCollapsingToolbarLayout.setTitle(title);
        }
        mCollapsingToolbarLayout.invalidate();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolable_activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_search)
        {
            openSearch();
            return true;
        }
        else if (id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    protected void closeSearch()
    {
        showHideToolbarMenuIcons(false);
        searchbox.hideCircularly(this);


        if(searchbox.getSearchText().isEmpty())
        {
            setActivityTitle(null);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(searchbox.isShown())
        {
            //showHideToolbarMenuIcons(false);
            searchbox.toggleSearch();
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void openSearch()
    {
        showHideToolbarMenuIcons(true);


        mCollapsingToolbarLayout.setTitle("");
        searchbox.revealFromMenuItem(R.id.action_search, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == Activity.RESULT_OK)
        {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayList<String> singleWord = new ArrayList<>(0);
            singleWord.add(matches.get(0));
            searchbox.populateEditText(singleWord);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

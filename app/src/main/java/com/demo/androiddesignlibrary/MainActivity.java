package com.demo.androiddesignlibrary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener
{
    //http://android-developers.blogspot.in/2015/05/android-design-support-library.html
    //http://antonioleiva.com/floating-action-button/
    //http://android-developers.blogspot.in/2014/10/appcompat-v21-material-design-for-pre.html
    //https://guides.codepath.com/android/Floating-Action-Buttons
    //https://plus.google.com/+AndroidDevelopers/posts/XTtNCPviwpj
    //https://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout
    //https://lab.getbase.com/introduction-to-coordinator-layout-on-android/

    private Toolbar mToolbar;

    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        bindFragment(savedInstanceState);
    }

    private void init()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);

        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_list)
                {
                    startListActivty();
                    return true;
                }
                else if (id == R.id.scrollable_activity1)
                {
                    startScrollableActivty1();
                    return true;
                }

                return false;
            }
        });
    }

    private void bindFragment(Bundle savedInstanceState)
    {
        if(savedInstanceState == null)
        {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transition = manager.beginTransaction();

            fragment = MainFragment.newInstance(null, null);
            transition.add(R.id.ma_rlFragmentContainer, fragment, MainFragment.class.getSimpleName());
            transition.commit();
        }
        else
        {
            fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(MainFragment.class.getSimpleName());
        }
    }


    private void startListActivty()
    {
        Intent mIntent = new Intent(this, ListActivity.class);
        startActivity(mIntent);
    }


    private void startScrollableActivty1()
    {
        Intent mIntent = new Intent(this, ScrollableActivity1.class);
        startActivity(mIntent);
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}

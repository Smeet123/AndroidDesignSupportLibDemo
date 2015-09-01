package com.demo.androiddesignlibrary;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.androiddesignlibrary.adapter.RecycleViewAdapter;


public class ListActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private RecycleViewAdapter adapter;
    private RecyclerView rvToDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setActionbar();
        init();
        setAdapter();
    }

    private void init()
    {
        rvToDoList = (RecyclerView) findViewById(R.id.rvToDoList);
        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter()
    {
        adapter = new RecycleViewAdapter(getStringData());
        rvToDoList.setAdapter(adapter);
    }

    private String[] getStringData()
    {
        String list[] = new String[20];
        for(int i = 0; i < list.length; i++)
        {
            list[i] = "Item : " + (i+1);
        }

        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setActionbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

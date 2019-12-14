package com.example.newsgateway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // I DID THE LANDSCAPE LAYOUT
    private static final String TAG = "MainActivity";

    static final String News_Story = "News_Story";
    static final String Msg_To_Service="Msg_to_Service";
    static final String Story_List="Story_List";
    private HashMap<String, ArrayList<NewsSource>> sourcesMap = new HashMap<>();
    private ArrayList<NewsSource> sources = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<Article> articles = new ArrayList<>();
    private MyPageAdapter pageAdapter;
    private List<Fragment> fragments;
    private ViewPager view_pager;
    private DrawerLayout dLayout;
    private ListView list_view;
    private ActionBarDrawerToggle dToggle;
    private NewsReceiver nReceiver = new NewsReceiver();
    private String categorySelected = "All"; //need this!
    private int sourceSelected = 7;
    private int page; //page for articles
    private Menu main; //added last minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent in = new Intent(this, NewsService.class);
        startService(in);
        IntentFilter filter = new IntentFilter(News_Story);
        registerReceiver(nReceiver, filter);

        dLayout = findViewById(R.id.drawerLayout);
        list_view = findViewById(R.id.leftDrawer);
        list_view.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, sources));
        list_view.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        sourceSelected = position;
                        selectItem(sourceSelected);
                    }
                }
        );
        dToggle = new ActionBarDrawerToggle(this, dLayout, R.string.drawer_open, R.string.drawer_close);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fragments = new ArrayList<>();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        view_pager = findViewById(R.id.viewPager);
        view_pager.setAdapter(pageAdapter);

        if(sources.isEmpty()){
            new News_Source_Downloader(this,categorySelected).execute();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        Intent intent = new Intent(this, NewsService.class);
        stopService(intent);
        unregisterReceiver(nReceiver);
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onPostCreate: ");
        super.onPostCreate(savedInstanceState);
        dToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //gomna change this
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putString("category_selected", categorySelected);
        outState.putString("Action_Bar_Title", (String) getTitle());
        outState.putSerializable("Articles", articles);
        outState.putInt("pagePosition", view_pager.getCurrentItem());

        super.onSaveInstanceState(outState);
    }

    //for the landscape
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);

        setTitle(savedInstanceState.getString("Action_Bar_Title"));

        categorySelected = savedInstanceState.getString("category_selected");
        new News_Source_Downloader(this,categorySelected).execute();

        articles = (ArrayList<Article>) savedInstanceState.getSerializable("Articles");
        page = savedInstanceState.getInt("pagePosition");

        for (int i = 0; i < pageAdapter.getCount(); i++) {
            pageAdapter.notifyChangeInPosition(i);
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        if(!articles.isEmpty()){
            view_pager.setBackground(null);
            Intent intent = new Intent();
            intent.setAction(News_Story);
            intent.putExtra(Story_List, articles);
            intent.putExtra("Page", page);
            sendBroadcast(intent); //broadcast from here, receive in different place
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        main = menu; // <- without this it doesn't make the menu when clicked on the fragment!! :OOO
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }
        categorySelected = item.toString();
        new News_Source_Downloader(this,categorySelected).execute();
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        Log.d(TAG, "selectItem: ");
        view_pager.setBackground(null);
        setTitle(sources.get(position).getName());
        Intent in = new Intent();
        in.setAction(Msg_To_Service);
        in.putExtra("SourceID", sources.get(position).getId());
        sendBroadcast(in);
        dLayout.closeDrawer(list_view);
    }

    public void setSources(ArrayList<NewsSource> listIn, ArrayList<String> catList){
        Log.d(TAG, "setSources: ");
        sourcesMap.clear();
        sources.clear();
        for (NewsSource s : listIn) {
            if (!sourcesMap.containsKey(s.getCategory())) {
                sourcesMap.put(s.getCategory(), new ArrayList<NewsSource>());
            }
            sourcesMap.get(s.getCategory()).add(s);
        }
        sourcesMap.put("All", listIn);
        sources.addAll(listIn);

        ((ArrayAdapter) list_view.getAdapter()).notifyDataSetChanged();

        if(categories.isEmpty()){
            categories = catList;
            categories.add(0,"All");
            if(main == null){
                invalidateOptionsMenu();
                if(main == null){
                    return;
                }
            }
            for(String c: categories){
                main.add(c);
            }
        }
    }

    class NewsReceiver extends BroadcastReceiver {
        private static final String TAG = "NewsReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: inside NEwsReceiver");
            switch(intent.getAction()){
                case News_Story:
                    articles = (ArrayList<Article>) intent.getSerializableExtra(Story_List);
                    page = intent.getIntExtra("Page", 0);
                    reDoFragments(articles, page);
                    break;
            }
        }
        //FIXXX ************** fixed
        public void reDoFragments(ArrayList<Article> a, int p){
            Log.d(TAG, "reDoFragments: inside newsreceiver");
            for (int i = 0; i < pageAdapter.getCount(); i++) {
                pageAdapter.notifyChangeInPosition(i);
            }
            fragments.clear();
            int count = a.size();
            for (int i = 0; i < count; i++) {
                fragments.add(NewsFragment.newInstance(a.get(i),(i+1)+" of "+count));
            }
            pageAdapter.notifyDataSetChanged();
            view_pager.setCurrentItem(p);
        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private static final String TAG = "MyPageAdapter";
        private long id = 0;

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public long getItemId(int position) {
            return id + position;
        }

        public void notifyChangeInPosition(int n) {
            id += getCount() + n;
        }
    } //DONE-- EASY

}

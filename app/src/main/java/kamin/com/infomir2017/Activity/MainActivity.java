package kamin.com.infomir2017.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import kamin.com.infomir2017.Model.DataHolder;
import kamin.com.infomir2017.Model.RSS;
import kamin.com.infomir2017.R;
import kamin.com.infomir2017.adapter.RssListAdapter;
import kamin.com.infomir2017.database.RSSDatabase;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    ImageButton btnAddSite;
    RecyclerView recyclerView;
    RssListAdapter rssListAdapter;
    public final static String TAG = MainActivity.class.getSimpleName();
    String[] sqliteIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent();
        RSSDatabase rssDB = new RSSDatabase(getApplicationContext());
        new loadRSSFeed().execute();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rssListAdapter = new RssListAdapter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rssListAdapter);
        registerForContextMenu(recyclerView);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider));
        recyclerView.addItemDecoration(divider);
    }

    void initContent(){
        initToolbar();

        btnAddSite = (ImageButton) findViewById(R.id.btnAddURL);
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), AddUrlActivity.class);
                startActivityForResult(addIntent, 100);
            }
        });

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    class loadRSSFeed extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RSSDatabase rssDb = new RSSDatabase(getApplicationContext());
            DataHolder.rssList = rssDb.getAllRSS();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(String args) {
            runOnUiThread(new Runnable() {
                public void run() {
                    //progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(), "fetched "+DataHolder.rssList.size(), Toast.LENGTH_LONG).show();
                    if(DataHolder.rssList.size()==0)
                        generateRSS();
                    else
                    rssListAdapter.notifyDataSetChanged();
                }
            });

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = -1;
        try {
            id = rssListAdapter.getID();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case 1:
                Log.d("LOG","i want delete "+item.getTitle()+" position  "+ id);
                RSSDatabase rssDb = new RSSDatabase(getApplicationContext());
                RSS rss = new RSS();
                rss.id = id;
                rssDb.deleteRss(rss);
                new loadRSSFeed().execute();
                rssListAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void goToRssScrean(String url){
        Intent DetailedIntent = new Intent(getApplicationContext(), RSSActivity.class);
        RSSDatabase rssDb = new RSSDatabase(getApplicationContext());
        DetailedIntent.putExtra("url", url);
        startActivity(DetailedIntent);
    }













    void generateRSS(){
        List<String> linkRSS = new ArrayList<String>();
        linkRSS.add("http://apps.shareholder.com/rss/rss.aspx?channels=7196&companyid=ABEA-4CW8X0&sh_auth=4854066111%2E0%2E0%2E42940%2E0764ab2e1be01a410fe234da4b7d514a");
        linkRSS.add("http://www.sciencemag.org/rss/news_current.xml");
        linkRSS.add("http://robotics.sciencemag.org/rss/current.xml");
        linkRSS.add("http://immunology.sciencemag.org/rss/current.xml");
        linkRSS.add("https://www.nasa.gov/rss/dyn/whats_up.rss");
        linkRSS.add("https://www.nasa.gov/rss/solar_system_vodcast.rss");
        linkRSS.add("http://news.liga.net/video/rss.xml");
        linkRSS.add("https://www.rbc.ua/static/rss/ukrnet.strong.rus.rss.xml");
        linkRSS.add("http://k.img.com.ua/rss/ru/all_news2.0.xml");
        linkRSS.add("https://www.nasaspaceflight.com/feed/");

        List<String> titles = new ArrayList<String>();
        titles.add("Tesla Motors - Financial Releases");
        titles.add("Latest News from Science Magazine");
        titles.add("Science Robotics current issue");
        titles.add("Science Immunology current issue");
        titles.add("NASACast: Whats Up? Video Podcasts");
        titles.add("NASACast: Solar System Video");
        titles.add("Настоящие новости Украины : ЛІГА.Новости");
        titles.add("РБК-Украина");
        titles.add("Последние новости на сайте korrespondent.net");
        titles.add("NASASpaceFlight.com");

        List<String> description = new ArrayList<String>();
        description.add("");
        description.add("Editable in modal at Format : RSS Feed | Settings : RSS Description");
        description.add("Science Robotics RSS feed -- current issue");
        description.add("Science Immunology RSS feed -- current issue");
        description.add("Whats Up? -- A monthly video that gives tips for where you can find the moon, planets and stars in the night sky. Also includes amateur astronomy news and updates on NASA missions.");
        description.add("NASA Video podcast episodes on Cassini, Mars missions and other missions exploring our solar system");
        description.add("Ежедневные новости: политика, власть, экономика и финансы, общество, мир, технологии. Фотографии с места событий.");
        description.add("Важные новости");
        description.add("Корреспондент - информационно-новостной портал №1 в Украине. Объективно о событиях по всему миру.");
        description.add("Giving space its place on the web");

        RSS rss = new RSS();
        for(int i= 0; i<linkRSS.size(); i++){
        rss.RSSLink = linkRSS.get(i);
        rss.title = titles.get(i);
        rss.description = description.get(i);
        RSSDatabase rssDb = new RSSDatabase(getApplicationContext());
        rssDb.addrss(rss);
        }

        new loadRSSFeed().execute();
        rssListAdapter.notifyDataSetChanged();
        }
        }

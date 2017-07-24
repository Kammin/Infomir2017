package kamin.com.infomir2017.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import kamin.com.infomir2017.Model.DataHolder;
import kamin.com.infomir2017.R;
import kamin.com.infomir2017.adapter.RssAdapter;
import kamin.com.infomir2017.network.VolleySingleton;
import kamin.com.infomir2017.network.strRequest;

public class RSSActivity extends AppCompatActivity {
    String RSSurl;
    RecyclerView recyclerView;
    RssAdapter rssAdapter;
    public final static String TAG = MainActivity.class.getSimpleName();
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";
    TextView header;
    ProgressBar progressBar;
    RSSActivity rssActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        RSSurl = getIntent().getExtras().getString("url","");
        Log.d(TAG,"RSSurl "+ RSSurl);

        initContent();
        connectTo(RSSurl);
        rssActivity = this;



    }

    void initContent() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        header = (TextView)  findViewById(R.id.lable);
        ImageButton imageButton = (ImageButton) findViewById(R.id.ibBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void connectTo(String url) {
        final String URL = url;
        progressBar.setVisibility(View.VISIBLE);
        final strRequest request = new strRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "len " + response.toString().length());
                if(DataHolder.parseSringRSS(response.toString(),URL)){
                    progressBar.setVisibility(View.GONE);
                    header.setText(DataHolder.rss.title);

                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    Log.d(TAG,"OK OK "+ " getChildCount "+recyclerView. getChildCount());
                    rssAdapter = new RssAdapter(rssActivity);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rssActivity);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(rssAdapter);
                    registerForContextMenu(recyclerView);
                    DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                    divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider));
                    recyclerView.addItemDecoration(divider);
                    rssAdapter.notifyDataSetChanged();

                    Log.d(TAG,"OK OK "+ DataHolder.rss.item.size()+" getChildCount "+recyclerView. getChildCount());
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Wrong URL",Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "ERROR " + error.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                params.put("User-Agent", USER_AGENT);
                return params;
            };
        };
        request.setShouldCache(false);
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }
}

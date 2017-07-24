package kamin.com.infomir2017.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import kamin.com.infomir2017.Model.DataHolder;
import kamin.com.infomir2017.R;
import kamin.com.infomir2017.database.RSSDatabase;
import kamin.com.infomir2017.network.VolleySingleton;
import kamin.com.infomir2017.network.strRequest;


public class AddUrlActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";

    String URL;
    EditText etURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);
        initContent();

    }

    void initContent() {
        initToolbar();
        etURL = (EditText) findViewById(R.id.etURL);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Button btCancel = (Button) findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Button btSubscribe = (Button) findViewById(R.id.btSubscribe);
        btSubscribe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                URL = etURL.getText().toString();
                connectTo(URL);
            }
        });
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(mToolbar);
    }

    void connectTo(String url) {
        final String URL = url;
        progressBar.setVisibility(View.VISIBLE);
        final strRequest request = new strRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "len " + response.toString().length());
                if(DataHolder.parseSringRSS(response.toString(),URL)){
                    new addRSStoDB().execute();
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "url are wrong",Toast.LENGTH_LONG).show();
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
                //params.put("Content-Type", "application/rss+xml; charset=UTF-8");
                params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                params.put("User-Agent", USER_AGENT);
                return params;
            };
        };
        request.setShouldCache(false);
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

    class addRSStoDB extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            RSSDatabase rssDb = new RSSDatabase(getApplicationContext());
            rssDb.addrss(DataHolder.rss);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(String args) {
            runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(), "Rss are added",Toast.LENGTH_LONG).show();
                    Intent i = getIntent();
                    setResult(100, i);
                    finish();
                }
            });

        }

    }

}

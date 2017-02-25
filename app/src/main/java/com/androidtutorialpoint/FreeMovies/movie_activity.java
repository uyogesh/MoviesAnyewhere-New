package com.androidtutorialpoint.FreeMovies;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialpoint.FreeMovies.video.videoplayer;
import com.yogeshupreti.FreeMovies.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;


public class movie_activity extends AppCompatActivity {

    Intent intent;
    TextView tx;

    ListView lst;
    String Url;


    WebView webView;
    Document doc;

    String al[];
    String epCode;
    String[] episods = {"Error", "On the Way"};
    String[] episodId;
    JSInterface api;
    RelativeLayout relativeLayout;
    String epDetail = "http://123movies.is/ajax/v2_get_episodes/";

    Intent myintent = new Intent(Intent.ACTION_VIEW);

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_activity);
        lst = (ListView) findViewById(R.id.List_episode);
        tx = (TextView) findViewById(R.id.we_view);
        relativeLayout=(RelativeLayout)findViewById(R.id.loadingPanel1);
        relativeLayout.setVisibility(View.VISIBLE);
        webView = new WebView(this);

        intent = getIntent();
        Url = intent.getStringExtra("Movie_Link");
        al = Url.split("-");
        int len = al.length;
        epCode = al[len - 1].substring(0, al[len - 1].length() - 1);
        Url += "watching.html";
        epDetail += epCode;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");

        webView.addJavascriptInterface(api, "api");

        webView.loadUrl(Url);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {


                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int url) {

                new populate_List().execute(epDetail);
                if (url == 100) {
                    Log.d("Debug A", "About to Fire JavaScript");



                }
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                relativeLayout.setVisibility(View.VISIBLE);
                webView.loadUrl("javascript:" + episodId[i]);
               webView.setWebChromeClient(new WebChromeClient(){

                   @Override
                   public void onProgressChanged(WebView view, int newProgress) {
                      if(newProgress>=99)

                       getResultforDisplay(webView);
                   }
               });




            }


        });
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Boolean getResultforDisplay(final WebView webView)
    {

        webView.evaluateJavascript("$('video')[0].getAttribute('src')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
               if(s!=null) {
                   String[] split=s.split("\"");
                   relativeLayout.setVisibility(View.GONE);


                   Intent newintent = new Intent(movie_activity.this,videoplayer.class);
                   newintent.putExtra("URL",split[1]);

                   startActivity(newintent);
//                    myintent.setDataAndType(Uri.parse(split[1]), "video/*");
//
//                    startActivity(myintent);

                }
                else{
                   Toast.makeText(movie_activity.this, "Couldn't Load Movie....Please Try again", Toast.LENGTH_SHORT).show();
               }
            }
        });
        return true;
    }



    public class populate_List extends AsyncTask<String, Void, String[]> {

        ArrayAdapter<String> adapter;

        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>) lst.getAdapter();
        }

        @Override
        protected String[] doInBackground(String... strings) {
            try {
                doc = Jsoup.connect(strings[0]).header("User-Agent", "Mozilla").get();
                if (doc != null) {
                    Jsoup_parser parser = new Jsoup_parser(doc, 1);
                    episods = parser.getEpisodes();
                    episodId = parser.getEpisode_req();

                }
                return episods;
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String[] s) {
            if (s != null) {
                relativeLayout.setVisibility(View.GONE);


                CustomList adapter = new CustomList(movie_activity.this, s);
                lst.setAdapter(adapter);
            } else {
            }


        }
    }

    private class JSInterface {

        @JavascriptInterface
        public void getString(String str) {

            myintent.setDataAndType(Uri.parse(str), "video/*");
            startActivity(myintent);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}


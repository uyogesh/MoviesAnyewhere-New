package com.androidtutorialpoint.FreeMovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yogeshupreti.FreeMovies.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static android.R.id.list;


public class MainActivity extends Activity {

    Context mContext;
    GridView grid;
    gridAdapter gridadapter;
    Button button;
    EditText text;
    String[] movie_name;
    String[] target_homepage={"http://www.123movies.to/movie/search/","https://www.123movies.is/movie/search/"};
    String[] web;
    String final_url;
    Jsoup_parser parser;
    String Movie_name;
    String[] image;
    String[] movie_link;
    //RelativeLayout rlayout;
    ProgressDialog progressDialog;
    String msg = "Wait till Movie Loads..";
    @Override
    public void onCreate(Bundle saved)
    {
        super.onCreate(saved);

        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.searchbtn);
        text=(EditText)findViewById(R.id.searchtxt);
        mContext = getApplicationContext();
//        rlayout=(RelativeLayout)findViewById(R.id.loadingPanel);
//        rlayout.setVisibility(View.GONE);


        final Intent intent=new Intent(MainActivity.this,movie_activity.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(MainActivity.this,"Loading...","Wait a moment while we load the Movie.");
                progressDialog.setCancelable(false);
                Movie_name=text.getText().toString();
                movie_name=split(Movie_name);
                final_url=FinalURL(target_homepage[1],movie_name);
               // rlayout.setVisibility(View.VISIBLE);
               new Mytask().execute(final_url);
            }
        });

        grid=(GridView)findViewById(R.id.gridView);

//        ListView ko OnClick Listener ho May be reused Later

//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                intent.putExtra("Movie_Link",movie_link[i]);
//                intent.putExtra("Movie_id",movie_name[i]);
//                startActivity(intent);
//            }
//
//
//        });

        //Grid View ko OnclickListener


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent.putExtra("Movie_Link",movie_link[position]);
                intent.putExtra("Movie_id",movie_name[position]);
                startActivity(intent);
            }

        });





    }

    private String FinalURL(String home,String[] movie_name) {
        int l=movie_name.length;
        int a=0;
        String m=home;
        while(a!=l){

            m+=movie_name[a];
            a++;
            m+="+";
        }

        return m;
    }

    private String[] split(String movie_name) {

        return movie_name.split("\\s+");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class Mytask extends AsyncTask<String,Boolean,Boolean> {
        String[] s=null;
        Document document;
        ArrayAdapter<Fragment> adapter;


        private Boolean tryConnect(String strings,int count)
        {
            int statusCode =0;


            Connection.Response response=null;
            try {
//                Connection.Response name =Jsoup.connect(strings[0]).execute();
                Connection conn =Jsoup.connect(strings);
                conn.timeout(10000);
                conn.validateTLSCertificates(false);
                conn.header("User-Agent","Mozilla/5.0 (Android 4.4; Mobile; rv:41.0) Gecko/41.0 Firefox/41.0");

                response=conn.execute();
                statusCode=response.statusCode();




                if(statusCode==200)
                    document =  response.parse();
                else {
                    if(count<6)
                    tryConnect(strings, count++);
                    else
                        return false;
                }

                parser=new Jsoup_parser(document,0);
                web=parser.getMovie_name();
                movie_name=parser.getMovie_name();
                movie_link=parser.getMovie_link();
                Log.d("AsyncTask", "CheckPoint");
                image = parser.getImg_link();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(tryConnect(strings[0],0))
                return true;
            else
                return false;
        }


        @Override
        protected void onPreExecute() {
            adapter=(ArrayAdapter<Fragment>)grid.getAdapter();
        }

        @Override
        protected void onPostExecute(Boolean s) {
            if(s==true) {
                //rlayout.setVisibility(View.GONE);
                progressDialog.dismiss();
//                CustomList adapter = new CustomList(MainActivity.this, s);
//                list.setAdapter(adapter);
                gridadapter = new gridAdapter(mContext,movie_name,image);
                grid.setAdapter(gridadapter);
//                Toast.makeText(MainActivity.this,image[0],Toast.LENGTH_LONG).show();
                }
            else{
                Toast.makeText(MainActivity.this, "Failed to fetch The Movie, Please try again after some Time.........", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //rlayout.setVisibility(View.GONE);
            }
        }


    }

}
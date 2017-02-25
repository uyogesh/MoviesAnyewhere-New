package com.androidtutorialpoint.FreeMovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yogeshupreti.FreeMovies.R;

import java.io.InputStream;

/**
 * Created by flowing on 2/14/17.
 */

public class gridAdapter extends BaseAdapter {

    Context mContext;
    private String[] text;
    private String[] imag;
    LayoutInflater inflater;

    public gridAdapter(Context c, String[] text, String[] imag){
        this.mContext = c;
        this.text= text;
        this.imag = imag;
    }

    @Override
    public int getCount() {
        return this.text.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//

        View grid = new View(mContext);
        grid = inflater.inflate(R.layout.fragment_movie_name,null);

        TextView tv = (TextView) grid.findViewById(R.id.frag_text);
        ImageView image = (ImageView) grid.findViewById(R.id.frag_image);
        new DownloadImageTask(image).execute(imag[position]);

        tv.setText(this.text[position]);

        return grid;
    }
// show The Image in a ImageView



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

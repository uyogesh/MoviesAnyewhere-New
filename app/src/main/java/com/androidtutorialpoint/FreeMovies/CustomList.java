package com.androidtutorialpoint.FreeMovies;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yogeshupreti.FreeMovies.R;


public class CustomList extends ArrayAdapter<Fragment> {
    private final Activity context;
    private final String[] web;

    public CustomList(Activity context, String[] resource) {

        super(context, R.layout.list_single);
        this.context=context;
        this.web=resource;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_single,null,true);
        TextView txtTitle=(TextView)rowView.findViewById(R.id.txt);
        txtTitle.setText(web[position]);

        return rowView;

    }
}

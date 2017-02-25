package com.androidtutorialpoint.FreeMovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yogeshupreti.FreeMovies.R;

import java.util.List;

/**
 * Created by flowing on 2/14/17.
 */

public class FragmentBuilder extends Fragment{


    String image;
    String text;
    View frag_view;
    ImageView imview;
    TextView textView;
    Uri imageUri;

    public void setImage(String image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imageUri = Uri.parse(image);
        frag_view = inflater.inflate(R.layout.fragment_movie_name,null);
        imview = (ImageView) frag_view.findViewById(R.id.frag_image);
        textView = (TextView) frag_view.findViewById(R.id.frag_text);
        imview.setImageURI(imageUri);
        textView.setText(text);
        return frag_view;
    }

    public Fragment getFragment(){

        return this;
    }


}

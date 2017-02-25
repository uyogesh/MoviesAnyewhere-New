package com.androidtutorialpoint.FreeMovies.video;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.yogeshupreti.FreeMovies.R;

public class videoplayer extends AppCompatActivity {

    VideoView videoView;
    String Url;
    Intent parentIntent;
    Uri uri;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        videoView = (VideoView) findViewById(R.id.videoView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            parentIntent=getIntent();
            Url=parentIntent.getStringExtra("URL");
            uri = Uri.parse(Url);
            progressDialog = ProgressDialog.show(videoplayer.this, "", "Buffering video...", true);
            progressDialog.setCancelable(true);
            PlayVideo();
                    }
        else{
            Toast.makeText(this,"Android Version not Supported, \"JellyBean or Higher\" only",Toast.LENGTH_LONG).show();
            onStop();
        }


    }

    private void PlayVideo()
    {
        try
        {
            getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(videoplayer.this);
            mediaController.setAnchorView(videoView);
            mediaController.addView(new View(this){
                @Override
                public void setBackgroundResource(int resid) {
                    super.setBackgroundResource(resid);
                }
            });
            Uri video = Uri.parse(Url);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {

                public void onPrepared(MediaPlayer mp)
                {

                    progressDialog.dismiss();
                    videoView.start();
                }
            });


        }
        catch(Exception e)
        {
            progressDialog.dismiss();
            System.out.println("Video Play Error :"+e.toString());
            finish();
        }

    }


    @Override
    public void onStop()
    {
        super.onStop();
    }
}

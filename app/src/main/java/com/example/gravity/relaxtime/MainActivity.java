package com.example.gravity.relaxtime;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;


public class MainActivity extends AppCompatActivity {
    Songs_frag songs_frag=new Songs_frag();
    folder_frag folder_frag=new folder_frag();
    ImageView playPause,next,previous;
    TextView finalDuration,RemaingDuration;
    MediaPlayer mediaPlayer;
    MaterialSearchBar materialSearchBar;
    MusicService musicService;

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstFrag();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        materialSearchBar=findViewById(R.id.search_bar);
        search();
        playPause=findViewById(R.id.play_pause);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previos);
        mediaPlayer=new MediaPlayer();
        musicService=new MusicService();
        finalDuration=findViewById(R.id.end_timer);

        if(mediaPlayer!=null&& mediaPlayer.isPlaying()){
            int dur=musicService.getDur();
            Log.v("Tag",""+dur);
            finalDuration.setText(""+dur);
        }
        else{
            finalDuration.setText("00:00");
            Log.v("Tag","Not working");
        }


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.playPrev();
            }
        });

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    musicService.onPause();
                    Toast.makeText(MainActivity.this,"working",Toast.LENGTH_SHORT).show();
                    playPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
                else {
                   mediaPlayer.start();
                    playPause.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }

        });

    }

    private void search() {
    }


    private void firstFrag() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,songs_frag).commit();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_song:
                    fragmentTransaction.replace(R.id.frame_container,songs_frag).commit();
                    return true;

                case R.id.navigation_folder:
                    fragmentTransaction.replace(R.id.frame_container,folder_frag).commit();
                    return true;
            }
            return false;
        }
    };



}

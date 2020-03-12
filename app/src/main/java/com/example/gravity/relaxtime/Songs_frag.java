package com.example.gravity.relaxtime;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;


public class Songs_frag extends Fragment {

    private static boolean supportBigNotifications = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    private static boolean supportSmallNotifications = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    RemoteViews remoteViews;

    private Notification notifyPlayer;
    private NotificationCompat.Builder mBuilder;
    RecyclerView recyclerView;
    View view;
    private ArrayList<model_class_song_fetch> songList;
    MusicService musicService;
    private long thisID;
    long thisDuration=0;
    boolean musicPlaying=false;
    Intent serviceIntent;
    MediaPlayer mediaPlayer;
    ImageView playPuaseButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
        public Songs_frag() { // Required empty public constructor
             }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_songs_frag, container, false);
        recyclerView= view.findViewById(R.id.songs_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        collapsingToolbarLayout=view.findViewById(R.id.song_title);

        songList= new ArrayList<>();
        getSongList();
        sortArray();
        myadaptor myadaptor=new myadaptor();
        recyclerView.setAdapter(myadaptor);
        serviceIntent=new Intent(getActivity(),MusicService.class);

        return view;
        }


    private void sortArray() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Collections.sort(songList, new Comparator<model_class_song_fetch>() {
                    @Override
                    public int compare(model_class_song_fetch a, model_class_song_fetch b) {
                        return a.getTitle().compareTo(b.getTitle());
                    }
                });
            }
        });
    }

        public void getSongList(){
            //retrive song list
            ContentResolver musicResolver=getActivity().getContentResolver();
            Uri musicUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor=musicResolver.query(musicUri,null,null,null,null);

            if(musicCursor!=null&& musicCursor.moveToFirst()){

                do{
                    String thisPath=musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    thisID=musicCursor.getLong(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String thisArtist=musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String thisTitile=musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String thisAlbumn=musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    thisDuration=musicCursor.getLong(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                    songList.add(new model_class_song_fetch(thisID,thisTitile,thisArtist,thisAlbumn,thisDuration,thisPath));
                }
                while (musicCursor.moveToNext());
            }
        }

        class myadaptor extends RecyclerView.Adapter<myadaptor.myholder> {

            @NonNull
            @Override
            public myadaptor.myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View v = getLayoutInflater().inflate(R.layout.custom_song_listview, parent, false);
                myholder m = new myholder(v);
                return m;
            }

            @Override
            public void onBindViewHolder(@NonNull final myadaptor.myholder holder, final int position) {
                final Intent i=new Intent();

                model_class_song_fetch curSong = songList.get(position);
                holder.songName.setText(curSong.getTitle());
                holder.artistName.setText(curSong.getArtist());
                holder.duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) curSong.getDuration()),
                        TimeUnit.MILLISECONDS.toSeconds((long) curSong.getDuration()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) curSong.getDuration()))));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String songName=songList.get(position).getTitle();
                        int songpos=songList.indexOf(position);
                        long dur=songList.get(position).getDuration();
                        if(!musicPlaying){
                            String path=songList.get(position).getPath();

                            //500k of summer
                            Toast.makeText(getActivity(),"Now Playing "+songName,Toast.LENGTH_LONG).show();
                            collapsingToolbarLayout=getActivity().findViewById(R.id.song_title);
                            collapsingToolbarLayout.setTitle(songName);

                            playSong();
                            showNoti(songList.get(position).getTitle(),songList.get(position).getArtist());
                            serviceIntent.putExtra("dur",dur);
                            serviceIntent.putExtra("pos",songpos);
                            serviceIntent.putExtra("po",path);



                            try{
                                getActivity().startService(serviceIntent);
                            }
                            catch (SecurityException e){
                                Toast.makeText(getActivity(),"Error :  "+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            musicPlaying=true;
                        }
                        else {musicPlaying=false;}
                   }
                });
            }
            @Override
            public int getItemCount() {
                return songList.size();
            }

            class myholder extends RecyclerView.ViewHolder {
                ImageView i,play_pause;
                TextView songName, artistName, duration;


                public myholder(View itemView) {
                    super(itemView);

                    play_pause=itemView.findViewById(R.id.play_pause_button);
                    i = itemView.findViewById(R.id.custom_album_view);
                    songName = itemView.findViewById(R.id.custom_songname_view);
                    songName.setSelected(true);
                    artistName = itemView.findViewById(R.id.custom_artist_view);
                    artistName.setSelected(true);
                    duration = itemView.findViewById(R.id.custom_duration_view);
                }
            }
        }

    private void showNoti(String title,String artist) {

            String CHANNEL_ID="1";
           NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(getActivity())
                   .setSmallIcon(R.drawable.ic_round_icon)
                   .setContentTitle("Now Playing "+title )
                   .setContentText(artist);

           NotificationManager notificationManager= (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
           notificationManager.notify(001,mBuilder.build());
    }

    private void playSong() {
        playPuaseButton=getActivity().findViewById(R.id.play_pause);
        playPuaseButton.setImageResource(R.drawable.ic_pause_black_24dp);
    }
}
//inheritance single nd interface
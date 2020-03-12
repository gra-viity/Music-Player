package com.example.gravity.relaxtime;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener,
MediaPlayer.OnPreparedListener,MediaPlayer.OnSeekCompleteListener
{
    private MediaPlayer mp;
    String link;
    private int SongPosn;

    Intent i;

    public MusicService() {

        i=new Intent();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");}

    @Override
    public void onCreate() {
        super.onCreate();
        mp=new MediaPlayer();
        mp.setOnCompletionListener(this);
        mp.setOnPreparedListener(this);
        mp.setOnErrorListener(this);
        mp.setOnSeekCompleteListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        link=intent.getStringExtra("po");
        playSong();
        return START_STICKY;
    }


    public void onPause(){
        mp.pause();
    }


    public  void playSong(){
        mp.reset();
        if(!mp.isPlaying()){
            try {
                mp.setDataSource(link);
                mp.prepareAsync();
            } catch (Exception e) {
//                Toast.makeText(this,"Error:"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }}
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mp!=null){

            if(mp.isPlaying()){
                mp.stop();
            }
            mp.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
//        if(mp.isPlaying()){
//            mp.stop();
//        }
//        stopSelf();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what){
                case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK :
                Toast.makeText(this,"MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK"
                ,Toast.LENGTH_LONG).show();
                     break;
                case MediaPlayer.MEDIA_INFO_AUDIO_NOT_PLAYING:
                    Toast.makeText(this,"MEDIA_INFO_AUDIO_NOT_PLAYING"
                            ,Toast.LENGTH_LONG).show();
                     break;
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(!mp.isPlaying()){
            mp.start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    public int getPosn(){return mp.getCurrentPosition();}

    public int getDur(){
        return mp.getDuration();
    }

    public boolean isPng(){
        return mp.isPlaying();
    }


    public void seek(int posn){
        mp.seekTo(posn);
    }

    public void go(){
        mp.start();
    }

    public boolean isPlaying(){
        return mp.isPlaying();
    }

    public void playPrev(){
        SongPosn =i.getIntExtra("pos",1);
        SongPosn--;
     //   if (SongPosn==0)
        //    SongPosn=songs.size()-1;
        mp.start();
        Log.v("service","Wroking");
//        if(songPosn&lt;0) songPosn=songs.size()-1;
//        playSong();
    }
}

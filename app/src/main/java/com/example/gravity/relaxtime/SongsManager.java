package com.example.gravity.relaxtime;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {

    final String MEDIA_PATH=new String("/sdcard/");

    private ArrayList<HashMap<String,String>> songsList=new ArrayList<HashMap<String,String>>();
    public SongsManager(){

    }

    public ArrayList<HashMap<String, String>> getPlayList() {
        File home=new File(MEDIA_PATH);

        if(home.listFiles(new FileExtentionFilter()).length>0){
            for(File file:home.listFiles(new FileExtentionFilter())){
                HashMap<String,String> song=new HashMap<String, String>();
                song.put("songTitle",file.getName().substring(0,(file.getName().length()-4)));
                song.put("songPath",file.getPath());
                songsList.add(song);
            }
        }
        return songsList;
    }

    private class FileExtentionFilter implements FilenameFilter{


        @Override
        public boolean accept(File dir, String name) {

            return (name.endsWith(".mp3")||name.endsWith(".MP3"));
        }
    }
}

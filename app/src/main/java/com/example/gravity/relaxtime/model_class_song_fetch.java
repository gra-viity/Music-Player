package com.example.gravity.relaxtime;

class model_class_song_fetch {
    private long id;
    private String title;
    private String artist;
    private long duration;
    private String album;
    private String path;
    //private  int albumArt;

    public model_class_song_fetch(long songID, String songTitle, String songArtist, String songAlbum, long songDuration,String songPath){
        id=songID;
        title=songTitle;
        artist=songArtist;
        album=songAlbum;
        duration=songDuration;
        path=songPath;
        // albumArt=songArt;
    }

    public String getPath(){ return path;}

    public String getAlbum(){ return album;}

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public long getDuration()
    {

        return duration;
    }

}

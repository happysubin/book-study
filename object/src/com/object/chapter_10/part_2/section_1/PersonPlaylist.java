package com.object.chapter_10.part_2.section_1;

public class PersonPlaylist extends Playlist {

    public void remove(Song song){
        getTracks().remove(song);
    }
}


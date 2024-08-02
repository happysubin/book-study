package com.object.book.chapter_10.part_2.section_1;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private List<Song> tracks = new ArrayList<>();

    public void append(Song song){
        getTracks().add(song);
    }

    public List<Song> getTracks() {
        return tracks;
    }
}


package com.object.chapter_10.part_2.section_2;

import com.object.chapter_10.part_2.section_1.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Playlist {

    private List<Song> tracks = new ArrayList<>();
    private Map<String, String> singers = new HashMap<>();


    //요구사항이 추가되서 해당 메서드가 변경됨.
    public void append(Song song){
        tracks.add(song);
        singers.put(song.getSinger(), song.getTitle());;
    }

    public List<Song> getTracks() {
        return tracks;
    }

    public Map<String, String> getSingers() {
        return singers;
    }
}


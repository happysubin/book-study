package com.object.chapter_10.part_2.section_2;

import com.object.chapter_10.part_2.section_1.Song;

public class PersonPlaylist extends Playlist {

    public void remove(Song song){
        getTracks().remove(song);
        getSingers().remove(song.getSinger()); //부모 클래스가 변경되면 이 부분이 추가되어야한다.
    }
}


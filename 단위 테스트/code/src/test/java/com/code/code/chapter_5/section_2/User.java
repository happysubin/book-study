package com.code.code.chapter_5.section_2;

/**
 * API가 잘 설계된 User 클래스
 */

public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = normalizeName(name);
    }

    private String normalizeName(String name) {
        String result = name.trim();
        if(result.length() > 50){
            return result.substring(0, 50);
        }
        return result;
    }
}

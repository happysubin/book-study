package com.code.code.chapter_7.v4.domainevent;

public class EmailChangedEvent {

    private int userId;
    private String newEmail;

    public EmailChangedEvent(int userId, String newEmail) {
        this.userId = userId;
        this.newEmail = newEmail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}

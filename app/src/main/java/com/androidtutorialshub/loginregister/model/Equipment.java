package com.androidtutorialshub.loginregister.model;

/**
 * Created by Madalina on 17.04.2019.
 */

public class Equipment {

    private int id;
    private String name;
    private String room;

    public Equipment(String name, String room) {
        this.name = name;
        this.room = room;
    }

    public Equipment(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}

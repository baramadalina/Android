package com.androidtutorialshub.loginregister.model;

/**
 * Created by Madalina on 17.04.2019.
 */

public class Inventory {

    private int id;
    private String name;
    private String room;


    public Inventory(String s, String s1){}

    public Inventory(int id,String name,String room){
        this.id=id;
        this.name=name;
        this.room=room;
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

package com.example.viikko9;

public class Theatre {
    int theater_id;
    String theater_name;
    public Theatre (String name, String id){
        theater_name = name;
        theater_id = Integer.parseInt(id);
    }
    public int getID(String name){
        if (name == theater_name){
            return theater_id;
        }else{
            return 0;
        }
    }
}

package com.phone.moran.model;

import java.util.ArrayList;

/**
 * Created by Stone on 2018/1/1.
 */

public class LocalMoods extends BaseModel{

    public static final int SIZE = 8;

    private ArrayList<Mood> moods = new ArrayList<>();


    public ArrayList<Mood> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<Mood> moods) {
        this.moods = moods;
    }

    public Mood getMoodById(int id) {
        for(Mood item : moods) {
            if(item.getMood_id() == id) {
                return item;
            }
        }

        return new Mood();
    }

    public Mood getMoodByName(String name) {
        for(Mood item : moods) {
            if(item.getMood_name().equals(name)) {
                return item;
            }
        }

        return new Mood();
    }
}

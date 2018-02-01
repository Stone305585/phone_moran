package com.phone.moran.model;

import java.util.ArrayList;

/**
 * Created by Stone on 2018/1/1.
 */

public class LocalPaints extends BaseModel{

    private ArrayList<Paint> paints = new ArrayList<>();

    public ArrayList<Paint> getPaints() {
        return paints;
    }

    public void setPaints(ArrayList<Paint> paints) {
        this.paints = paints;
    }

    public Paint getPaintByTitle(String title) {
        if(paints.size() == 0) {
            return null;
        }
        for(int i = 0; i < paints.size(); i++) {
            if(paints.get(i).getPaint_title().equals(title)) {
                return paints.get(i);
            }
        }

        return null;
    }

    public Paint getPaintById(int id) {
        if(paints.size() == 0) {
            return null;
        }
        for(int i = 0; i < paints.size(); i++) {
            if(paints.get(i).getPaint_id() == id) {
                return paints.get(i);
            }
        }

        return null;
    }
}

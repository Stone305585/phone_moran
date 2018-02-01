package com.phone.moran.model;

/**
 * Created by Stone on 2017/12/2.
 */

public class FilterCategory {

    private String type_name;
    private String type_id;
    private boolean selected;


    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

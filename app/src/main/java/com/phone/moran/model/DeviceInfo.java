package com.phone.moran.model;

/**
 * Created by Stone on 2017/12/5.
 */

public class DeviceInfo {

    private String device_name;
    private String device_id;
    private int flag;//是否是管理员，1：是 2：不是

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}

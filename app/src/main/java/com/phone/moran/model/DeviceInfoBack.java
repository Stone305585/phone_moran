package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 *
 */
public class DeviceInfoBack extends BaseModel{

    private int ret;
    private String err;
    private List<DeviceInfo> device_infos;

    public List<DeviceInfo> getDevice_infos() {
        return device_infos;
    }

    public void setDevice_infos(List<DeviceInfo> device_infos) {
        this.device_infos = device_infos;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}

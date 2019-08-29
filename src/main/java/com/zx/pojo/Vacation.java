package com.zx.pojo;

import lombok.Data;

@Data
public class Vacation {
    private int vacationId;
    private String vstime;
    private String vetime;
    private String vname;
    public String time;
    private String descripe;
    private int state;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getVstime() {
        return vstime;
    }

    public void setVstime(String vstime) {
        this.vstime = vstime.substring(0,19);
    }

    public String getVetime() {
        return vetime;
    }

    public void setVetime(String vetime) {
        this.vetime = vetime.substring(0,19);
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
    }
}

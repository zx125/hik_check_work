package com.zx.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class Evection {
    private int evectionId;
    private String Estart;
    private String Eend;
    private int Eaudit;
    private String Edescribe;
    private int satffId;
    private String name;
    private String dName;
    private String stateName;

    public void setEstart(String estart) {
        this.Estart = estart.substring(0,19);
    }

    public void setEend(String eend) {
        this.Eend = eend.substring(0,19);
    }
}

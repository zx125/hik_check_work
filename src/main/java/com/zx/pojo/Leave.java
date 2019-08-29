package com.zx.pojo;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class Leave {
    private Integer leaveId;
    private String lstart;
    private String Lend;
    private String Ldescribe;
    private String dName;
    private String name;
    private Integer Laudit;
    private Integer staffId;
    private String stateName;
    private String lType;

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public String getLstart() {
        return lstart;
    }

    public void setLstart(String lstart) {
        this.lstart = lstart.substring(0,19);
    }

    public String getLend() {
        return Lend;
    }

    public void setLend(String lend) {
        this.Lend = lend.substring(0,19);
    }

    public String getLdescribe() {
        return Ldescribe;
    }

    public void setLdescribe(String ldescribe) {
        this.Ldescribe = ldescribe;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLaudit() {
        return Laudit;
    }

    public void setLaudit(Integer laudit) {
        this.Laudit = laudit;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}

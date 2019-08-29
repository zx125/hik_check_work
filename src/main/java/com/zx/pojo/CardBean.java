package com.zx.pojo;

import lombok.Data;

@Data
public class CardBean {
    private String page;
    private String limit;
    private String name;
    private String cardNumber;
    private String dname;
    private Integer cardDeviceId;
}

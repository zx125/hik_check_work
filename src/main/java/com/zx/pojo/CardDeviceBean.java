package com.zx.pojo;

import lombok.Data;

import java.util.List;
@Data
public class CardDeviceBean {
    private List<CardVo> cards;
    private List<DeviceVo> devices;
    @Data
    public static class CardVo{
        private String cardId;
        private String value;
        private String title;
        private String cardNumber;
        private String departmentName;
    }
    @Data
    public static class DeviceVo{
        private String value;
        private String title;
        private String departmentName;
    }
}

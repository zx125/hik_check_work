package com.zx.hik;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class run {
    public static void main(String[] args) throws UnsupportedEncodingException {
        init init=new init();
        init.init_hCNetSDK();
        init.init_device("192.168.0.88","admin","abcd1234");
//        init.SetCardInfo();
    }
}

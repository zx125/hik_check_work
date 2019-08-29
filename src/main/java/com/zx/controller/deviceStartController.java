package com.zx.controller;

import com.zx.hik.init;
import com.zx.service.DeviceService;
import com.zx.service.DeviceStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class deviceStartController {
    @Autowired
    private init init;
    @Autowired
    private DeviceStartService deviceStartService;
    @RequestMapping("godevice")
    public String godevice(){
        deviceStartService.init_device();
        return "deviceManage";
    }
}

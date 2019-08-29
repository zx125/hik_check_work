package com.zx.controller;

import com.zx.pojo.CardDeviceBean;
import com.zx.service.ManageCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
public class manageCardController {
    @Autowired
    public ManageCardService manageCardService;
    @RequestMapping("goManageCard")
    public String goManageCard(){
        return "manageCard";
    }
    @RequestMapping("addCardDevice")
    @ResponseBody
    public String addCardDevice(@RequestBody CardDeviceBean cardDeviceBean) throws UnsupportedEncodingException {
        manageCardService.addCardDevice(cardDeviceBean);
        return "success";
    }
}

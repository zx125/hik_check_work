package com.zx.controller;

import com.zx.pojo.*;
import com.zx.service.CardService;
import com.zx.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class cardController {
    @Autowired
    public CardService cardService;
    @RequestMapping("goCard")
    public String goCard(){
        return "card";
    }
    @RequestMapping("selectAllCard")
    @ResponseBody
    public Layui selectAllCard(@RequestParam Map map){
        return cardService.selectAllCard(map);
    }
}

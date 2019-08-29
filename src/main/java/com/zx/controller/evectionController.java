package com.zx.controller;

import com.zx.pojo.Crew;
import com.zx.pojo.Evection;
import com.zx.pojo.Layui;
import com.zx.pojo.PageBean;
import com.zx.service.CrewService;
import com.zx.service.EvectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class evectionController {
    @Autowired
    private EvectionService evectionService;
    @RequestMapping("goEvection")
    public String goEvection(){
        return "evection";
    }
    @RequestMapping("egetAll")
    @ResponseBody
    public Layui egetAll(HttpServletRequest request){
        //获取分页数据
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        PageBean<Evection> pageBean=evectionService.selectEvection(page,limit);
        return Layui.data(pageBean.getTotalCount(),pageBean.getLists());
    }
}

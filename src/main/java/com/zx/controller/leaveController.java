package com.zx.controller;

import com.zx.pojo.Evection;
import com.zx.pojo.Layui;
import com.zx.pojo.Leave;
import com.zx.pojo.PageBean;
import com.zx.service.EvectionService;
import com.zx.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class leaveController {
    @Autowired
    private LeaveService leaveService;
    @RequestMapping("goLeave")
    public String goLeave(){
        return "leave";
    }
    @RequestMapping("lgetAll")
    @ResponseBody
    public Layui lgetAll(HttpServletRequest request){
        //获取分页数据
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        PageBean<Leave> pageBean=leaveService.selectLeave(page,limit);
//        System.out.println(pageBean.getLists().get(0).getLstart().substring(0,10));
//        System.out.println(pageBean.getLists().get(0).getLstart().length());
        return Layui.data(pageBean.getTotalCount(),pageBean.getLists());
    }
}

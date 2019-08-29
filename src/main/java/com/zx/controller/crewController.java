package com.zx.controller;

import com.zx.pojo.Crew;
import com.zx.pojo.Layui;
import com.zx.pojo.PageBean;
import com.zx.service.CrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class crewController {
    @Autowired
    private CrewService crewService;
    @RequestMapping("crew")
    public String goCrew(){
        return "crew";
    }
    @RequestMapping("cgetAll")
    @ResponseBody
    public Layui cgetAll(HttpServletRequest request){
        //获取分页数据
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        PageBean<Crew> pageBean=crewService.selectCrew(page,limit);
        return Layui.data(pageBean.getTotalCount(),pageBean.getLists());
    }
}

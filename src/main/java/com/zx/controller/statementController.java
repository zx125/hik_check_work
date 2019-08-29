package com.zx.controller;

import com.zx.pojo.*;
import com.zx.service.DepartmentService;
import com.zx.service.StaffService;
import com.zx.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class statementController {
    @Autowired
    private StatementService statementService;
    @Autowired
    private DepartmentService departmentService;
    @RequestMapping("gostatement")
    public ModelAndView gostatement(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("statement");
        List<Department> names = departmentService.selectDepartmentName();
        modelAndView.addObject("names", names);
        return modelAndView;
    }
    @RequestMapping("/getstatment")
    @ResponseBody
    public Layui getstatment(StatementBean statementBean){
        //获取分页数据
        PageBean<StatementDetail> pageBean=statementService.selectStatementDetail(statementBean);
        return Layui.data(pageBean.getTotalCount(),pageBean.getLists());
    }
}

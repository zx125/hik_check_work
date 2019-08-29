package com.zx.controller;

import com.zx.pojo.Layui;
import com.zx.pojo.PageBean;
import com.zx.pojo.Staff;
import com.zx.pojo.Vacation;
import com.zx.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class vacationController {
    @Autowired
    private VacationService vacationService;
    @RequestMapping("govacation")
    public ModelAndView selectStaffDetailAll() {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("vacation");
        return modelAndView;
    }
    @RequestMapping("selectVacationAll")
    @ResponseBody
    public Layui selectVacationAll(HttpServletRequest request){
        //获取分页数据
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String state = request.getParameter("state");
        PageBean<Vacation> pageBean = vacationService.selectVacationAll(page, limit,state);
        return Layui.data(pageBean.getTotalCount(), pageBean.getLists());
    }
    @RequestMapping("addVacationView")
    public String addVacationView(){
        return "addVacation";
    }
    @RequestMapping("addVacation")
    @ResponseBody
    public String addVacation(@RequestBody Vacation vacation){
        String[] parts = vacation.getTime().split(" - ");
        vacation.setVstime(parts[0]);
        vacation.setVetime(parts[1]);
        vacationService.addVacation(vacation);
        return "success";
    }
    @RequestMapping("delVacationById")
    @ResponseBody
    public String delVacationById(@RequestParam("vacationId") int vacationId){
        vacationService.delVacationById(vacationId);
        return "";
    }
}

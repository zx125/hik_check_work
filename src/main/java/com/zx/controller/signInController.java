package com.zx.controller;

import com.zx.dao.StaffMapper;
import com.zx.pojo.Layui;
import com.zx.pojo.PageBean;
import com.zx.pojo.SignInDetail;
import com.zx.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class signInController {
    @Autowired
    private SignInService signInService;
    @Autowired
    private StaffMapper staffMapper;
    @RequestMapping("getAll")
    @ResponseBody
    public Layui getAllSignIn(HttpServletRequest request){
        //获取分页数据
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        String day=request.getParameter("day");
        String day2=request.getParameter("day2");
        String state=request.getParameter("state");
        PageBean<SignInDetail> pageBean=signInService.selectSignDetail(null,page,limit,day,state,day2);
        return Layui.data(pageBean.getTotalCount(),pageBean.getLists());
    }
    @RequestMapping("delById")
    @ResponseBody
    public String delById(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("id"));
        signInService.delById(id);
        return "success";
    }
    @RequestMapping("countDayState")
    @ResponseBody
    public int notDayIn(HttpServletRequest request){
        String day=request.getParameter("day");
        String day2=request.getParameter("day2");
        String state=request.getParameter("state");
        int count=signInService.countDayState(day,day2,state);
        return count;
    }
    @RequestMapping("zy")
    public ModelAndView goZy(){
        ModelAndView modelAndView=new ModelAndView();
        SimpleDateFormat aDate=new SimpleDateFormat("yyyy-MM-dd");
        String toDay=aDate.format(new Date());
        int count1=signInService.countDayState(toDay,null,"2");
        int count2=signInService.countDayState(toDay,null,"4");
        int count3=signInService.countDayState(toDay,null,"3");
        modelAndView.addObject("count1",count1);
        modelAndView.addObject("count2",count2);
        modelAndView.addObject("count3",count3);
        modelAndView.addObject("today",toDay);
        modelAndView.setViewName("zx");
        return modelAndView;
    }
    @RequestMapping("timeLine")
    public ModelAndView goTimeLine(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        String signInId=request.getParameter("signInId");
        String day=request.getParameter("day");
        modelAndView.addObject("day",day);
        int staffId=signInService.selectstaffIdbysId(Integer.parseInt(signInId));
        List<SignInDetail> signInDetails=signInService.selectSignDetail(staffId,null,null,day,null,null).getLists();
        try {
            SignInDetail signInDetail=staffMapper.selectStaffDetail(staffId);
            String zx=day.substring(0,7);
            int count1=signInService.countMouthState(zx,"2",staffId);
            int count2=signInService.countMouthState(zx,"4",staffId);
            int count3=signInService.countMouthState(zx,"3",staffId);
            modelAndView.addObject("count1",count1);
            modelAndView.addObject("count2",count2);
            modelAndView.addObject("count3",count3);
            modelAndView.addObject("signInDetail",signInDetail);
            modelAndView.addObject("signInDetails",signInDetails);
        }
        catch (Exception e){
        }
        modelAndView.setViewName("timeLine");
        return modelAndView;
    }
    @RequestMapping("addSignIn")
    @ResponseBody
    public String addSignIn(){
        signInService.addSignIn("2019-05-28 14:18:16","888888","192.168.0.88");
        return "addSignIn";
    }

}

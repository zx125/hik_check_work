package com.zx.controller;

import com.alibaba.fastjson.JSONObject;
import com.zx.pojo.*;
import com.zx.service.CrewService;
import com.zx.service.DepartmentService;
import com.zx.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class staffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CrewService crewService;

    @RequestMapping("selectStaffDetailAll")
    @ResponseBody
    public Layui selectStaffDetailAll(HttpServletRequest request) {
        //获取分页数据
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        PageBean<StaffDetail> pageBean = staffService.selectStaffDetailAll(page, limit);
        return Layui.data(pageBean.getTotalCount(), pageBean.getLists());
    }
    @RequestMapping("selectStaffNameByDepartmentId")
    @ResponseBody
    public List<String> selectStaffNameByDepartmentId(int departmentId){
        return staffService.selectStaffNameByDepartmentId(departmentId);
    }
    @RequestMapping("selectStaffNameByDepartmentIdCard")
    @ResponseBody
    public List<manageCardBean> selectStaffNameByDepartmentIdCard(int departmentId){
        return staffService.selectStaffNameByDepartmentIdCard(departmentId);
    }
    @RequestMapping("goStaff")
    public String goStaff() {
        return "staff";
    }

    @RequestMapping("addStaffView")
    public ModelAndView addStaffView() {
        ModelAndView modelAndView = new ModelAndView("addStaff");
        List<Department> names = departmentService.selectDepartmentName();
        List<Crew> cdes=crewService.selectCrewAll();
        modelAndView.addObject("crew",cdes);
        modelAndView.addObject("names", names);
        return modelAndView;
    }

    @RequestMapping("addStaff")
    public String addStaff(@RequestBody Staff staff) {
        staffService.addStaff(staff);
        return "staff";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public JSONObject  upload(@RequestParam("file") MultipartFile multipartFile) {
        JSONObject resObj = new JSONObject();
        resObj.put("msg", "error");
        if (!StringUtils.isEmpty(multipartFile) && multipartFile.getSize() > 0) {
            String filename = multipartFile.getOriginalFilename();
            if (filename.endsWith("jpg") || filename.endsWith("png")) {
                String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                String realPath ="C:\\Users\\Administrator\\Desktop\\img\\"+uuid+".jpg";
                File newfile = new File(realPath);
                resObj.put("msg", "ok");
                HashMap<String,String> src=new HashMap();
                src.put("src",realPath);
                resObj.put("data",src);
                try {
                    multipartFile.transferTo(newfile);
                } catch (IOException e)
                {
                    resObj.put("msg", "error");
                    e.printStackTrace();
                }
                return resObj;
            }
        }
        return resObj;
    }
}
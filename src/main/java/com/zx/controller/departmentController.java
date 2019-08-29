package com.zx.controller;

import com.zx.pojo.Department;
import com.zx.pojo.DepartmentTree;
import com.zx.service.DepartmentService;
import com.zx.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class departmentController {
    @Autowired
    private DepartmentService departmentService;
    @RequestMapping("goDepartment")
    public String goDepartment(){
        return "department";
    }
    @RequestMapping("selectDepartmentName")
    @ResponseBody
    public List<Department> selectDepartmentName(){
        return departmentService.selectDepartmentName();
    }
    @RequestMapping("selectCascadeDepartmentName")
    @ResponseBody
    public HashMap<Integer,DepartmentTree> selectCascadeDepartmentName(){
        return departmentService.selectCascadeDepartmentName();
    }
    @RequestMapping("DepartmentText")
    @ResponseBody
    public List<DepartmentTree> DepartmentText(){
        return departmentService.getDepartmetTree();
    }
    @RequestMapping("addDepartment")
    public void addDepartment(@RequestBody DepartmentTree departmentTree){
        departmentService.addDepartment(departmentTree);
    }

}

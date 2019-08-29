package com.zx.controller;

import com.zx.pojo.Device;
import com.zx.pojo.Layui;
import com.zx.pojo.PageBean;
import com.zx.pojo.manageDeviceBean;
import com.zx.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class deviceController {
    @Autowired
    private DeviceService deviceService;
    @RequestMapping("goMachine")
    public String goMachine(){
        return "device";
    }
    @RequestMapping("goAddDevice")
    public String goAddMachine(){
        return "addDevice";
    }
    @RequestMapping("addDevice")
    public void addDevice(@RequestBody Device device){
        deviceService.addDevice(device);
    }
    @RequestMapping("selectDeviceAll")
    @ResponseBody
    public Layui selectDeviceAll(HttpServletRequest request){
        //获取分页数据
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        PageBean<Device> pageBean = deviceService.selectDeviceAll(page, limit);
        return Layui.data(pageBean.getTotalCount(), pageBean.getLists());
    }
    @RequestMapping("delDevice")
    @ResponseBody
    public String delDevice(Integer id){
        deviceService.delDevice(id);
        return "success";
    }
    @RequestMapping("delSomeDevice")
    @ResponseBody
    public String delSomeDevice(@RequestBody List<Device> devices){
        deviceService.delSomeDevice(devices);
        return "success";
    }
    @RequestMapping("selectDeviceByDepartmentIdCard")
    @ResponseBody
    public List<manageDeviceBean> selectDeviceByDepartmentIdCard(int departmentId){
        return deviceService.selectDeviceByDepartmentIdCard(departmentId);
    }
}

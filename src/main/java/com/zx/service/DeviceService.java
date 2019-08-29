package com.zx.service;
import com.zx.dao.DeviceMapper;
import com.zx.dao.LeaveMapper;
import com.zx.pojo.Device;
import com.zx.pojo.Leave;
import com.zx.pojo.PageBean;
import com.zx.pojo.manageDeviceBean;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    public PageBean<Device> selectDeviceAll(String page, String limit){
        PageBean<Device> pageBean=new PageBean();
        pageBean.setLists(deviceMapper.selectDeviceAll(PageUtill.PageMap(page,limit,null)));
        pageBean.setTotalCount(deviceMapper.selectCount());
        return pageBean;
    }

    public void addDevice(Device device) {
        deviceMapper.addDevice(device);
    }

    public void delDevice(Integer id) {
        deviceMapper.delDevice(id);
    }
    public void delSomeDevice(List<Device> devices){
        for (Device device:devices) {
            deviceMapper.delDevice(device.getDeviceId());
        }
    }

    public List<manageDeviceBean> selectDeviceByDepartmentIdCard(int departmentId) {
        return deviceMapper.selectDeviceByDepartmentIdCard(departmentId);
    }
}

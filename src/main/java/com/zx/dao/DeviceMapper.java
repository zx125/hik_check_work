package com.zx.dao;

import com.zx.pojo.Device;
import com.zx.pojo.Leave;
import com.zx.pojo.manageDeviceBean;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface DeviceMapper {
    List<Device> selectDeviceAll(HashMap<String, Object> map);

    int selectCount();

    void addDevice(Device device);

    void delDevice(Integer id);

    List<manageDeviceBean> selectDeviceByDepartmentIdCard(int departmentId);

    Integer queryDeviceIdByDip(String Dip);

    String queryDeviceIpById(String id);
}
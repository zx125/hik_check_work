package com.zx.service;

import com.sun.jna.NativeLong;
import com.zx.dao.DeviceMapper;
import com.zx.hik.init;
import com.zx.pojo.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DeviceStartService {
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private init init;
    public void init_device(){
        List<Device> devices=deviceMapper.selectDeviceAll(null);
        init.init_hCNetSDK();
        for (Device device:devices) {
            HashMap<String,NativeLong> de=init.init_device(device.getDip(),device.getDuser(),device.getDpassWord());
            System.out.println(device.getDip());
            init.SetupAlarmChan(de.get(device.getDip()));
        }
    }
}

package com.zx.service;

import com.zx.dao.CardMapper;
import com.zx.dao.DeviceMapper;
import com.zx.dao.SignInMapper;
import com.zx.pojo.PageBean;
import com.zx.pojo.SignInDetail;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SignInService {
    @Autowired
    private SignInMapper signInMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    public PageBean<SignInDetail> selectSignDetail(Integer staffId, String page, String limit, String day, String state, String day2){
        PageBean<SignInDetail> pageBean=new PageBean();
        HashMap<String,Object> map = PageUtill.PageMap(page,limit,null);
        map.put("day",day);
        map.put("staffId",staffId);
        map.put("day2",day2);
        map.put("state",state);
        pageBean.setLists(signInMapper.selectSignInDetail(map));
        pageBean.setTotalCount(signInMapper.getCount(map));
        return pageBean;
    }
    public int delById(int id) {
        int isDel=signInMapper.deleteByPrimaryKey(id);
        return isDel;
    }
    public int selectstaffIdbysId(int signInId){
        int staffId=signInMapper.selectstaffIdbysId(signInId);
        return staffId;
    }
    public int countDayState(String day,String day2,String state) {
        HashMap<String, Object> map=new HashMap();
        map.put("day",day);
        map.put("day2",day2);
        map.put("state",state);
        int count=signInMapper.countDayState(map);
        return count;
    }
    public int countMouthState(String mouth,String state,int staffid) {
        HashMap<String, Object> map=new HashMap();
        map.put("mouth",mouth);
        map.put("state",state);
        map.put("staffid",staffid);
        int count=signInMapper.countMouthState(map);
        return count;
    }
    public void addSignIn(String date, String card, String ip) {
        String staffId=cardMapper.queryStaffIdByCardNumber(card);
        Integer deviceId=deviceMapper.queryDeviceIdByDip(ip);
        signInMapper.addSignIn(date,staffId,deviceId);
    }
}

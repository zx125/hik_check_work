package com.zx.service;

import com.zx.dao.EvectionMapper;
import com.zx.dao.LeaveMapper;
import com.zx.pojo.Evection;
import com.zx.pojo.Leave;
import com.zx.pojo.PageBean;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    @Autowired
    private LeaveMapper leaveMapper;
    public PageBean<Leave> selectLeave(String page, String limit){
        PageBean<Leave> pageBean=new PageBean();
        pageBean.setLists(leaveMapper.selectLeave(PageUtill.PageMap(page,limit,null)));
        pageBean.setTotalCount(leaveMapper.selectCount());
        return pageBean;
    }
}

package com.zx.service;

import com.zx.dao.CrewMapper;
import com.zx.dao.StaffMapper;
import com.zx.pojo.Crew;
import com.zx.pojo.PageBean;
import com.zx.pojo.SignInDetail;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CrewService {
    @Autowired
    private CrewMapper crewMapper;
    public PageBean<Crew> selectCrew(String page,String limit){
        PageBean<Crew> pageBean=new PageBean();
        pageBean.setLists(crewMapper.selectCrew(PageUtill.PageMap(page,limit,null)));
        pageBean.setTotalCount(crewMapper.selectCount());
        return pageBean;
    }
    public List<Crew> selectCrewAll(){
        return crewMapper.selectCrewAll();
    }
}

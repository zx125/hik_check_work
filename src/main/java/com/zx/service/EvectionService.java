package com.zx.service;

import com.zx.dao.CrewMapper;
import com.zx.dao.EvectionMapper;
import com.zx.pojo.Crew;
import com.zx.pojo.Evection;
import com.zx.pojo.PageBean;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvectionService {
    @Autowired
    private EvectionMapper evectionMapper;
    public PageBean<Evection> selectEvection(String page, String limit){
        PageBean<Evection> pageBean=new PageBean();
        pageBean.setLists(evectionMapper.selectEvection(PageUtill.PageMap(page,limit,null)));
        pageBean.setTotalCount(evectionMapper.selectCount());
        return pageBean;
    }
}

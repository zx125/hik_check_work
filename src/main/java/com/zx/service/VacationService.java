package com.zx.service;

import com.zx.dao.VacationMapper;
import com.zx.pojo.PageBean;
import com.zx.pojo.Vacation;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationService {
    @Autowired
    private VacationMapper vacationMapper;
    public PageBean<Vacation> selectVacationAll(String page, String limit,String state1){
        PageBean<Vacation> pageBean=new PageBean();
        pageBean.setLists(vacationMapper.selectVacationAll(PageUtill.PageMap(page,limit,state1)));
        int state=Integer.parseInt(state1);
        pageBean.setTotalCount(vacationMapper.selectCount(state));
        return pageBean;
    }
    public String addVacation(Vacation vacation){
        vacationMapper.addVacation(vacation);
        return "";
    }
    public String delVacationById(int id){
        vacationMapper.delVacationById(id);
        return "";
    }
}

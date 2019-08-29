package com.zx.service;

import com.zx.dao.StaffMapper;
import com.zx.pojo.*;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffMapper staffMapper;
    public SignInDetail selectStaffDetail(int id){
        SignInDetail signInDetail=staffMapper.selectStaffDetail(id);
        return signInDetail;
    }

    public PageBean<StaffDetail> selectStaffDetailAll(String page, String limit) {
        PageBean<StaffDetail> pageBean=new PageBean();
        HashMap<String,Object> map = PageUtill.PageMap(page,limit,null);
        pageBean.setLists(staffMapper.selectStaffDetailAll(map));
        pageBean.setTotalCount(staffMapper.selectCount(null));
        return pageBean;
    }

    public void addStaff(Staff staff) {
        staffMapper.addStaff(staff);
    }

    public List<String> selectStaffNameByDepartmentId(int departmentId) {
        return staffMapper.selectStaffNameByDepartmentId(departmentId);
    }

    public List<StatementDetail> getStaffSome(StatementBean statementBean) {
        return staffMapper.getStaffSome(statementBean);
    }

    public List<manageCardBean> selectStaffNameByDepartmentIdCard(int departmentId) {
        return staffMapper.selectStaffNameByDepartmentIdCard(departmentId);
    }
}

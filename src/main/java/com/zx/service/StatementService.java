package com.zx.service;

import com.zx.dao.SignInMapper;
import com.zx.dao.StaffMapper;
import com.zx.pojo.PageBean;
import com.zx.pojo.StatementBean;
import com.zx.pojo.StatementDetail;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StatementService {
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private SignInMapper signInMapper;
    public PageBean<StatementDetail> selectStatementDetail(StatementBean statementBean){
        PageBean<StatementDetail> pageBean=new PageBean();
        HashMap<String,Object> map = PageUtill.PageMap(statementBean.getPage(),statementBean.getLimit(),null);
        statementBean.setStart((int)map.get("start"));
        statementBean.setSize((int)map.get("size"));
        System.out.println(statementBean);
        System.out.println(staffMapper.getStaffSome(statementBean).size());

        try {
            System.out.println(statementBean.getName().length());
            if(statementBean.getName().isEmpty()){
                statementBean.setName(null);
            }
        }catch (Exception e){
        }
        List<StatementDetail> StatementDetails = staffMapper.getStaffSome(statementBean);
        //早退迟到数据获取
        for (StatementDetail statement:StatementDetails) {
            statementBean.setStaffId(statement.getStaffId());
            //迟到次数
            statementBean.setState(2);
            statement.setLateCount(signInMapper.selectCountByStatement(statementBean));
            //早退次数
            statementBean.setState(3);
            statement.setLeaveCount(signInMapper.selectCountByStatement(statementBean));
            //计算加班时间
        }
        pageBean.setLists(StatementDetails);
        pageBean.setTotalCount(staffMapper.selectCount(statementBean));
        return pageBean;
    }
}

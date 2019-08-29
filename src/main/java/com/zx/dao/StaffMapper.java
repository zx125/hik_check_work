package com.zx.dao;

import com.zx.pojo.*;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffMapper {
    int countByExample(StaffExample example);

    int deleteByExample(StaffExample example);

    int deleteByPrimaryKey(Integer id);


    int insertSelective(Staff record);

    SignInDetail selectStaffDetail(int id);

    int selectCount(StatementBean statementBean);

    List<StaffDetail> selectStaffDetailAll(HashMap<String,Object> map);

    List<Staff> selectByExample(StaffExample example);

    Staff selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Staff record, @Param("example") StaffExample example);

    int updateByExample(@Param("record") Staff record, @Param("example") StaffExample example);

    int updateByPrimaryKeySelective(Staff record);

    int updateByPrimaryKey(Staff record);

    void addStaff(Staff staff);

    List<String> selectStaffNameByDepartmentId(int departmentId);

    List<StatementDetail> getStaffSome(StatementBean statementBean);

    List<manageCardBean> selectStaffNameByDepartmentIdCard(int departmentId);
}
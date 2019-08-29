package com.zx.dao;

import com.zx.pojo.SignIn;
import com.zx.pojo.SignInDetail;
import com.zx.pojo.SignInExample;

import java.util.HashMap;
import java.util.List;

import com.zx.pojo.StatementBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SignInMapper {
    int countByExample(SignInExample example);

    int deleteByExample(SignInExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(SignIn record);

    int selectstaffIdbysId(int signInId);

    int countDayState(HashMap<String, Object> map);

    int countMouthState(HashMap<String, Object> map);

    List<SignInDetail> selectSignInDetail(HashMap<String, Object> map);

    int getCount(HashMap<String, Object> map);

    int insertSelective(SignIn record);

    List<SignIn> selectByExample(SignInExample example);

    SignIn selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") SignIn record, @Param("example") SignInExample example);

    int updateByExample(@Param("record") SignIn record, @Param("example") SignInExample example);

    int updateByPrimaryKeySelective(SignIn record);

    int updateByPrimaryKey(SignIn record);

    int selectCountByStatement(StatementBean statementBean);

    void addSignIn(String date, String staffId, Integer deviceId);
}
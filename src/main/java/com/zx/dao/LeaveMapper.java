package com.zx.dao;

import com.zx.pojo.Evection;
import com.zx.pojo.Leave;
import com.zx.pojo.StatementBean;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface LeaveMapper {
    List<Leave> selectLeave(HashMap<String, Object> map);
    int selectCount();
}
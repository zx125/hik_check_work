package com.zx.dao;

import com.zx.pojo.Crew;
import com.zx.pojo.Department;
import com.zx.pojo.DepartmentExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface CrewMapper {
    List<Crew> selectCrew(HashMap<String, Object> map);
    List<Crew> selectCrewAll();
    int selectCount();
}
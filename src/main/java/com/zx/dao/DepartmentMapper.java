package com.zx.dao;

import com.zx.pojo.Department;
import com.zx.pojo.DepartmentExample;
import java.util.List;

import com.zx.pojo.DepartmentTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentMapper {
    int countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Integer departmentid);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectDepartmentName();

    List<DepartmentTree> selectCascadeDepartmentName();

    List<DepartmentTree> selectRootDepartmentTree();

    List<DepartmentTree> selectDepartmentTreeByParentId(Integer parentId);


    List<Department> selectByExample(DepartmentExample example);

    Department selectByPrimaryKey(Integer departmentid);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    void addDepartment(Integer rootId);
}
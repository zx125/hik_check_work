package com.zx.service;

import com.zx.dao.CrewMapper;
import com.zx.dao.DepartmentMapper;
import com.zx.pojo.Crew;
import com.zx.pojo.Department;
import com.zx.pojo.DepartmentTree;
import com.zx.pojo.PageBean;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> selectDepartmentName(){
        return departmentMapper.selectDepartmentName();
    }
    public List<DepartmentTree> getDepartmetTree(){
        List<DepartmentTree> departmentTrees=departmentMapper.selectRootDepartmentTree();
        DepartmentTree tree=new DepartmentTree();
        tree.setTitle("所有部门");
        tree.setChildren(allTree(departmentTrees));
        List<DepartmentTree> trees=new ArrayList();
        trees.add(tree);
        return trees;
    }
    public void addDepartment(DepartmentTree departmentTree){
        Integer rootId=departmentTree.getRootId();
        departmentMapper.addDepartment(rootId);
    }
    public HashMap<Integer,DepartmentTree> selectCascadeDepartmentName(){
        List<DepartmentTree> departmentTrees=departmentMapper.selectRootDepartmentTree();
        HashMap<Integer,DepartmentTree> departmentNames=new HashMap();
        return allTreename(departmentTrees,departmentNames);
    }
    public List<DepartmentTree> getTree(DepartmentTree departmentTree){
        List<DepartmentTree> child=departmentMapper.selectDepartmentTreeByParentId(departmentTree.getRootId());
        return child;
    }
    public List<DepartmentTree> allTree(List<DepartmentTree> departmentTrees){
        for (DepartmentTree departmentTree:departmentTrees) {
                List<DepartmentTree> trees=getTree(departmentTree);
                departmentTree.setChildren(trees);
                if (trees!=null)
                allTree(trees);
        }
        return departmentTrees;
    }
    public HashMap<Integer,DepartmentTree> allTreename(List<DepartmentTree> departmentTrees,HashMap<Integer,DepartmentTree> departmentNames){
        for (DepartmentTree departmentTree:departmentTrees) {
            if (departmentTree.getParentId()!=null && !departmentTree.getParentId().equals("")){
                String title=departmentNames.get(departmentTree.getParentId()).getTitle()+departmentTree.getTitle();
                departmentTree.setTitle(title);
                departmentNames.put(departmentTree.getRootId(),departmentTree);
            }
            else {
                departmentNames.put(departmentTree.getRootId(),departmentTree);
            }
                List<DepartmentTree> trees=getTree(departmentTree);
                if (trees!=null)
                    allTreename(trees,departmentNames);
        }
        return departmentNames;
    }
}
//      [{
//        title: '远方科技中心' //一级菜单
//          ,children: [{
//              title: '远方大厅门口' //二级菜单
//              },{
//              title: '19楼' //二级菜单
//                  ,children: [{
//                      title: '1909大门' //三级菜单
//                      //…… //以此类推，可无限层级
//                  },{
//                  title: '1908大门' //三级菜单
//                  //…… //以此类推，可无限层级
//                  },{
//                  title: '1907大门' //三级菜单
//                  //…… //以此类推，可无限层级
//                  }]
//              }]
//        },{
//        title: '陕西' //一级菜单
//            ,children: [{
//              title: '西安' //二级菜单
//                  ,children: [{
//                      title: '高新区' //三级菜单
//                      //…… //以此类推，可无限层级
//                      }]
//              }]
//        }]

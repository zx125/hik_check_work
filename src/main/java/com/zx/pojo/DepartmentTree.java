package com.zx.pojo;

import lombok.Data;

import java.util.List;
@Data
public class DepartmentTree {
    private Integer parentId;
    private Integer rootId;
    private List<DepartmentTree> children;
    private String title;
}
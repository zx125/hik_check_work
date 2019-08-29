package com.zx.dao;

import com.zx.pojo.Rule;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface RuleMapper {
     List<Rule> getAllRule();
     void updateRule(int key,String val);
}

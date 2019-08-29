package com.zx.service;

import com.zx.dao.RuleMapper;
import com.zx.pojo.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class RuleService {
    @Autowired
    private RuleMapper ruleMapper;

    public List<Rule> getAllRule() {
        return ruleMapper.getAllRule();
    }

    public void updateRule(List<Rule> rules) {
        for (Rule rule : rules) {
            ruleMapper.updateRule(rule.getName(),rule.getValue());
        }
    }
}

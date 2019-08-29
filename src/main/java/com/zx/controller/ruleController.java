package com.zx.controller;

import com.zx.pojo.Rule;
import com.zx.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ruleController {
    @Autowired
    private RuleService ruleService;
    @RequestMapping("gorule")
    public ModelAndView selectStaffDetailAll() {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("checkRule");
        List<Rule> rules=ruleService.getAllRule();
        modelAndView.addObject("rules",rules);
        return modelAndView;
    }
    @RequestMapping("updateRule")
    @ResponseBody
    public String updateRule(@RequestBody List<Rule> rules ){
        ruleService.updateRule(rules);
        return "success";
    }
}

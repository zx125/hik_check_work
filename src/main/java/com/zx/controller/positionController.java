package com.zx.controller;

import com.zx.pojo.Position;
import com.zx.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class positionController {
    @Autowired
    private PositionService positionService;
    @RequestMapping("selectPosition")
    @ResponseBody
    public List<Position> selectPosition(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("id"));
        return positionService.selectPosition(id);
    }
}

package com.zx.service;

import com.zx.dao.PositionMapper;
import com.zx.pojo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PositionService {
    @Autowired
    private PositionMapper positionMapper;
    public List<Position> selectPosition(int id){
        return positionMapper.selectPosition(id);
    }
}

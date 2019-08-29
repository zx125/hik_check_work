package com.zx.dao;

import com.zx.pojo.Crew;
import com.zx.pojo.Position;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PositionMapper {
    List<Position> selectPosition(int id);
}
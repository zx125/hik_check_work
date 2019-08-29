package com.zx.dao;

import com.zx.pojo.Evection;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
public interface EvectionMapper {
    List<Evection> selectEvection(HashMap<String, Object> map);
    int selectCount();
}
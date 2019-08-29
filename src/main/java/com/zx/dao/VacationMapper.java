package com.zx.dao;

import com.zx.pojo.Vacation;

import java.util.HashMap;
import java.util.List;

public interface VacationMapper {
    List<Vacation> selectVacationAll(HashMap<String, Object> map);
    int selectCount(int state);
    void delVacationById(int id);

    void addVacation(Vacation vacation);

}

package com.zx.dao;

import com.zx.pojo.Card;
import com.zx.pojo.CardBean;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface CardMapper {
    public List<CardBean> selectAllCard(HashMap<String,Object> map);
    public int selectCount();
    public String queryStaffIdByCardNumber(String staffId);
    public String queryCardNumberByCardId(String cardId);
}
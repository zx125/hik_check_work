package com.zx.dao;

import com.zx.pojo.CardBean;
import com.zx.pojo.CardDeviceBean;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ManageCardMapper {
    void addCardDevice(String s, String cardId, String value);
}
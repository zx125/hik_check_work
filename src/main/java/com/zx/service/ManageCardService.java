package com.zx.service;

import com.zx.dao.CardMapper;
import com.zx.dao.DeviceMapper;
import com.zx.dao.ManageCardMapper;
import com.zx.hik.init;
import com.zx.pojo.CardBean;
import com.zx.pojo.CardDeviceBean;
import com.zx.pojo.Layui;
import com.zx.pojo.PageBean;
import com.zx.utill.PageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ManageCardService {
    @Autowired
    private ManageCardMapper manageCardMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired CardMapper cardMapper;
    @Autowired
    private init init;
    public void addCardDevice(CardDeviceBean cardDeviceBean) throws UnsupportedEncodingException {
        HashMap<String,String> des=new HashMap<>();
        for (CardDeviceBean.DeviceVo deviceVo:cardDeviceBean.getDevices()){
            des.put(deviceVo.getValue(),deviceMapper.queryDeviceIpById(deviceVo.getValue()));
        }
        for (CardDeviceBean.CardVo cardVo:cardDeviceBean.getCards()) {
            cardVo.setCardNumber(cardMapper.queryCardNumberByCardId(cardVo.getCardId()));
        }
        init.SetCardInfo(des,cardDeviceBean.getCards());
        for (CardDeviceBean.CardVo cardvo:cardDeviceBean.getCards()) {
            for (CardDeviceBean.DeviceVo deviceVo:cardDeviceBean.getDevices()) {
                manageCardMapper.addCardDevice(cardvo.getCardId()+deviceVo.getValue(),cardvo.getCardId(),deviceVo.getValue());
            }
        }
    }
}

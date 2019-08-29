package com.zx.hik;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

public class alarmData {
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    public static class FMSGCallBack implements HCNetSDK.FMSGCallBack{
        @Override
        public void invoke(NativeLong lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
            System.out.println("ZYF alarm type:" + lCommand);
            if (lCommand.intValue()==hCNetSDK.COMM_ALARM_ACS){
                HCNetSDK.NET_DVR_ACS_ALARM_INFO acsAlarmInfo=new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
                acsAlarmInfo.read();
                System.out.println("acsAlarmInfo"+acsAlarmInfo.struAcsEventInfo.byCardNo);
            }
            else if (lCommand.intValue()==hCNetSDK.COMM_ID_INFO_ALARM){
                HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM idCardInfoAlarm=new HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM();
                idCardInfoAlarm.read();
                System.out.println("idCardInfoAlarm"+idCardInfoAlarm);
            }
            else if (lCommand.intValue()==hCNetSDK.COMM_PASSNUM_INFO_ALARM){
                HCNetSDK.NET_DVR_PASSNUM_INFO_ALARM passnumInfoAlarm=new HCNetSDK.NET_DVR_PASSNUM_INFO_ALARM();
                passnumInfoAlarm.read();
                System.out.println("passnumInfoAlarm"+passnumInfoAlarm);
            }
        }
    }
}

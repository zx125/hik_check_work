package com.zx.hik;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.zx.pojo.CardDeviceBean;
import com.zx.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class init {
    @Autowired
    private SignInService signInService;
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    NativeLong lAlarmHandle = new NativeLong(-1);//报警布防句柄
    FMSGCallBack_V31 fMSFCallBack_V31;//报警回调函数实现
    String m_sDeviceIP;//已登录设备的IP地址
    boolean bRealPlay;//是否在预览.
    HashMap<String, NativeLong> devices = new HashMap<>();
    NativeLong lUserID = new NativeLong(-1);//用户句柄
    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    FRemoteCfgCallBackCardSet fRemoteCfgCallBackCardSet = null;
    int i = 0;

    //创建一个窗口
    public void CreateJFrame() {
        JFrame jf = new JFrame("zx");
        Container container = jf.getContentPane();
        JLabel jl = new JLabel("这是一个窗口");
        container.add(jl);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setSize(200, 150);
    }

    //初始化SDK
    public void init_hCNetSDK() {
        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true) {
            System.out.println("初始化失败！");
        } else {
            System.out.println("初始化成功");
        }
        //断线重连
        hCNetSDK.NET_DVR_SetConnectTime(2000, 1);
        hCNetSDK.NET_DVR_SetReconnect(10000, true);
    }

    //注册设备
    public HashMap<String, NativeLong> init_device(String ip, String user, String password) {
        //注册之前先注销已注册的用户,预览情况下不可注销
        if (bRealPlay) {
            System.out.println("注册新用户请先停止当前预览");
        }
        if (lUserID.longValue() > -1) {
            System.out.println("注销设备");
            //先注销
//            hCNetSDK.NET_DVR_Logout_V30(lUserID);
            lUserID = new NativeLong(-1);
        }
        m_sDeviceIP = ip;//设备ip地址
        m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        int iPort = Integer.parseInt("8000");
        lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,
                (short) iPort, user, password, m_strDeviceInfo);
        long userID = lUserID.longValue();
        if (userID == -1) {
            m_sDeviceIP = "";//登录未成功,IP置为空
            System.out.println("注册失败");
            System.out.println(hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("注册成功");
            devices.put(ip, lUserID);
            lUserID = new NativeLong(-1);
        }
        return devices;
    }

    //注销设备
    public void out_login() {
        hCNetSDK.NET_DVR_Logout(lUserID);
        System.out.println("注销设备");
        hCNetSDK.NET_DVR_Cleanup();
        System.out.println("释放SDK");
    }

    //布防
    public void SetupAlarmChan(NativeLong lUserID) {

        if (lUserID.intValue() == -1) {
            JOptionPane.showMessageDialog(null, "请先注册");
            return;
        }
        if (lAlarmHandle.intValue() < 0)//尚未布防,需要布防
        {
            System.out.println("zx");
            if (fMSFCallBack_V31 == null) {
                fMSFCallBack_V31 = new FMSGCallBack_V31();
                Pointer pUser = null;
                if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31, pUser)) {
                    System.out.println("设置回调函数失败!");
                }
            }
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            m_strAlarmInfo.dwSize = m_strAlarmInfo.size();
            m_strAlarmInfo.byLevel = 1;
            m_strAlarmInfo.byAlarmInfoType = 1;
            m_strAlarmInfo.write();
            lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, m_strAlarmInfo);
            System.out.println(hCNetSDK.NET_DVR_GetLastError());
            if (lAlarmHandle.intValue() == -1) {
                System.out.println("布防失败");
//                out_login();
            } else {
                lAlarmHandle.setValue(-1);
                System.out.println("布防成功");
            }
        }
    }

    //撤防
    public void CloseAlarmChan() {
        //报警撤防
        if (lAlarmHandle.intValue() > -1) {
            if (hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle)) {
                JOptionPane.showMessageDialog(null, "撤防成功");
                lAlarmHandle = new NativeLong(-1);
            }
        }
    }

    public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {
        public boolean invoke(NativeLong lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
            AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
            return true;
        }
    }

    public void AlarmDataHandle(NativeLong lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        String sAlarmType = new String();
        String[] newRow = new String[3];
        //报警时间
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] sIP = new String[2];

        sAlarmType = new String("lCommand=") + lCommand.intValue();
        //lCommand是传的报警类型
        switch (lCommand.intValue()) {
            case HCNetSDK.COMM_ALARM_V30:
                HCNetSDK.NET_DVR_ALARMINFO_V30 strAlarmInfoV30 = new HCNetSDK.NET_DVR_ALARMINFO_V30();
                strAlarmInfoV30.write();
                Pointer pInfoV30 = strAlarmInfoV30.getPointer();
                pInfoV30.write(0, pAlarmInfo.getByteArray(0, strAlarmInfoV30.size()), 0, strAlarmInfoV30.size());
                strAlarmInfoV30.read();
                switch (strAlarmInfoV30.dwAlarmType) {
                    case 0:
                        sAlarmType = sAlarmType + new String("：信号量报警") + "，" + "报警输入口：" + (strAlarmInfoV30.dwAlarmInputNumber + 1);
                        break;
                    case 1:
                        sAlarmType = sAlarmType + new String("：硬盘满");
                        break;
                    case 2:
                        sAlarmType = sAlarmType + new String("：信号丢失");
                        break;
                    case 3:
                        sAlarmType = sAlarmType + new String("：移动侦测") + "，" + "报警通道：";
                        for (int i = 0; i < 64; i++) {
                            if (strAlarmInfoV30.byChannel[i] == 1) {
                                sAlarmType = sAlarmType + "ch" + (i + 1) + " ";
                            }
                        }
                        break;
                    case 4:
                        sAlarmType = sAlarmType + new String("：硬盘未格式化");
                        break;
                    case 5:
                        sAlarmType = sAlarmType + new String("：读写硬盘出错");
                        break;
                    case 6:
                        sAlarmType = sAlarmType + new String("：遮挡报警");
                        break;
                    case 7:
                        sAlarmType = sAlarmType + new String("：制式不匹配");
                        break;
                    case 8:
                        sAlarmType = sAlarmType + new String("：非法访问");
                        break;
                }
                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("1:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);
                break;
            case HCNetSDK.COMM_ALARM_RULE:
                HCNetSDK.NET_VCA_RULE_ALARM strVcaAlarm = new HCNetSDK.NET_VCA_RULE_ALARM();
                strVcaAlarm.write();
                Pointer pVcaInfo = strVcaAlarm.getPointer();
                pVcaInfo.write(0, pAlarmInfo.getByteArray(0, strVcaAlarm.size()), 0, strVcaAlarm.size());
                strVcaAlarm.read();

                switch (strVcaAlarm.struRuleInfo.wEventTypeEx) {
                    case 1:
                        sAlarmType = sAlarmType + new String("：穿越警戒面") + "，" +
                                "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                        break;
                    case 2:
                        sAlarmType = sAlarmType + new String("：目标进入区域") + "，" +
                                "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                        break;
                    case 3:
                        sAlarmType = sAlarmType + new String("：目标离开区域") + "，" +
                                "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                        break;
                    default:
                        sAlarmType = sAlarmType + new String("：其他行为分析报警") + "，" +
                                "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                        break;
                }
                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("2:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);

                if (strVcaAlarm.dwPicDataLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        fout = new FileOutputStream(newName + "_VCA.jpg");
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strVcaAlarm.pImage.getPointer().getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                        byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_UPLOAD_PLATE_RESULT:
                HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
                strPlateResult.write();
                Pointer pPlateInfo = strPlateResult.getPointer();
                pPlateInfo.write(0, pAlarmInfo.getByteArray(0, strPlateResult.size()), 0, strPlateResult.size());
                strPlateResult.read();
                try {
                    String srt3 = new String(strPlateResult.struPlateInfo.sLicense, "GBK");
                    sAlarmType = sAlarmType + "：交通抓拍上传，车牌：" + srt3;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("3:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);

                if (strPlateResult.dwPicLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        fout = new FileOutputStream(newName + "_PlateResult.jpg");
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strPlateResult.pBuffer1.getByteBuffer(offset, strPlateResult.dwPicLen);
                        byte[] bytes = new byte[strPlateResult.dwPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ITS_PLATE_RESULT:
                HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
                strItsPlateResult.write();
                Pointer pItsPlateInfo = strItsPlateResult.getPointer();
                pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
                strItsPlateResult.read();
                try {
                    String srt3 = new String(strItsPlateResult.struPlateInfo.sLicense, "GBK");
                    sAlarmType = sAlarmType + ",车辆类型：" + strItsPlateResult.byVehicleType + ",交通抓拍上传，车牌：" + srt3;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("4:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);

                for (int i = 0; i < strItsPlateResult.dwPicNum; i++) {
                    if (strItsPlateResult.struPicInfo[i].dwDataLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = newName + "_ITSPlateResult_type" + strItsPlateResult.struPicInfo[i].byType + ".jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                            byte[] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case HCNetSDK.COMM_ALARM_PDC:
                HCNetSDK.NET_DVR_PDC_ALRAM_INFO strPDCResult = new HCNetSDK.NET_DVR_PDC_ALRAM_INFO();
                strPDCResult.write();
                Pointer pPDCInfo = strPDCResult.getPointer();
                pPDCInfo.write(0, pAlarmInfo.getByteArray(0, strPDCResult.size()), 0, strPDCResult.size());
                strPDCResult.read();

                sAlarmType = sAlarmType + "：客流量统计，进入人数：" + strPDCResult.dwEnterNum + "，离开人数：" + strPDCResult.dwLeaveNum;

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(strPDCResult.struDevInfo.struDevIP.sIpV4).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("5:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);
                break;

            case HCNetSDK.COMM_ITS_PARK_VEHICLE:
                HCNetSDK.NET_ITS_PARK_VEHICLE strItsParkVehicle = new HCNetSDK.NET_ITS_PARK_VEHICLE();
                strItsParkVehicle.write();
                Pointer pItsParkVehicle = strItsParkVehicle.getPointer();
                pItsParkVehicle.write(0, pAlarmInfo.getByteArray(0, strItsParkVehicle.size()), 0, strItsParkVehicle.size());
                strItsParkVehicle.read();
                try {
                    String srtParkingNo = new String(strItsParkVehicle.byParkingNo).trim(); //车位编号
                    String srtPlate = new String(strItsParkVehicle.struPlateInfo.sLicense, "GBK").trim(); //车牌号码
                    sAlarmType = sAlarmType + ",停产场数据,车位编号：" + srtParkingNo + ",车位状态："
                            + strItsParkVehicle.byLocationStatus + ",车牌：" + srtPlate;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("6:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);

                for (int i = 0; i < strItsParkVehicle.dwPicNum; i++) {
                    if (strItsParkVehicle.struPicInfo[i].dwDataLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = newName + "_ITSPark_type" + strItsParkVehicle.struPicInfo[i].byType + ".jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strItsParkVehicle.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsParkVehicle.struPicInfo[i].dwDataLen);
                            byte[] bytes = new byte[strItsParkVehicle.struPicInfo[i].dwDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case HCNetSDK.COMM_ALARM_ACS: //门禁主机报警信息
                HCNetSDK.NET_DVR_ACS_ALARM_INFO strACSInfo = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
                strACSInfo.write();
                Pointer pACSInfo = strACSInfo.getPointer();
                pACSInfo.write(0, pAlarmInfo.getByteArray(0, strACSInfo.size()), 0, strACSInfo.size());
                strACSInfo.read();
                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                switch (strACSInfo.dwMajor) {
                    case 0x5:
                        switch (strACSInfo.dwMinor) {
                            case 0x15:
                                System.out.println(newRow[0] + "报警主类型:0x5,报警次类型0x15-门锁打开" + "-" + newRow[2]);
                                break;
                            case 0x16:
                                System.out.println(newRow[0] + "报警主类型:0x5,报警次类型0x16-门锁关闭" + "-" + newRow[2]);
                                break;
                            case 0x4b:
                                if (strACSInfo.dwPicDataLen > 0) {
                                    System.out.println(newRow[0] + "报警主类型:0x5,报警次类型0x4b-卡类型:" + strACSInfo.struAcsEventInfo.byCardType + "卡号:" +
                                            new String(strACSInfo.struAcsEventInfo.byCardNo).trim() + "-" + newRow[2]);
                                    try {
                                        signInService.addSignIn(newRow[0], new String(strACSInfo.struAcsEventInfo.byCardNo).trim(), newRow[2]);
                                    } catch (Exception e) {
                                        System.out.println(e.toString());
                                    }
                                }
                                break;
                            case 0x4c:
                                System.out.println(newRow[0] + "报警主类型:0x5,报警次类型0x4b-人脸认证失败");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
//                    System.out.println("7:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);
                //抓拍图片
                if (strACSInfo.dwPicDataLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = newName + "_ACS_card_" + new String(strACSInfo.struAcsEventInfo.byCardNo).trim() + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strACSInfo.pPicData.getByteBuffer(offset, strACSInfo.dwPicDataLen);
                        byte[] bytes = new byte[strACSInfo.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ID_INFO_ALARM: //身份证信息
                HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM strIDCardInfo = new HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM();
                strIDCardInfo.write();
                Pointer pIDCardInfo = strIDCardInfo.getPointer();
                pIDCardInfo.write(0, pAlarmInfo.getByteArray(0, strIDCardInfo.size()), 0, strIDCardInfo.size());
                strIDCardInfo.read();

                sAlarmType = sAlarmType + "：门禁身份证刷卡信息，身份证号码：" + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() + "，姓名：" +
                        new String(strIDCardInfo.struIDCardCfg.byName).trim() + "，报警主类型：" + strIDCardInfo.dwMajor + "，报警次类型：" + strIDCardInfo.dwMinor;

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("8:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);
                break;
            default:
                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println("9:" + newRow[0] + "/" + newRow[1] + "/" + newRow[2]);
                break;
        }
    }

    public void SetCardInfo(HashMap<String,String> des,List<CardDeviceBean.CardVo> cards) throws UnsupportedEncodingException {
        int iErr = 0;

        //设置卡参数
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParamSet = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParamSet.read();
        m_struCardInputParamSet.dwSize = m_struCardInputParamSet.size();
        m_struCardInputParamSet.dwCardNum = 1;
        m_struCardInputParamSet.byCheckCardNo = 1;

        Pointer lpInBuffer = m_struCardInputParamSet.getPointer();
        m_struCardInputParamSet.write();

        Pointer pUserData = null;
        fRemoteCfgCallBackCardSet = new FRemoteCfgCallBackCardSet();
        for(String key : des.keySet()){
            devices.get(des.get(key));

            NativeLong lHandle = hCNetSDK.NET_DVR_StartRemoteConfig(devices.get(des.get(key)), HCNetSDK.NET_DVR_SET_CARD_CFG_V50, lpInBuffer, m_struCardInputParamSet.size(), fRemoteCfgCallBackCardSet, pUserData);
            if (lHandle.intValue() < 0) {
                iErr = hCNetSDK.NET_DVR_GetLastError();
                System.out.println("建立长连接失败，错误号："+iErr);
                return;
            }
            System.out.println("建立设置卡参数长连接成功!");
            for (CardDeviceBean.CardVo cardVo :cards) {
                HCNetSDK.NET_DVR_CARD_CFG_V50 struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50(); //卡参数
                struCardInfo.read();
                struCardInfo.dwSize = struCardInfo.size();
                struCardInfo.dwModifyParamType = 0x00000001 + 0x00000002 + 0x00000004 + 0x00000008 +
                        0x00000010 + 0x00000020 + 0x00000080 + 0x00000100 + 0x00000200 + 0x00000400 + 0x00000800;
                /***
                 * #define CARD_PARAM_CARD_VALID       0x00000001  //卡是否有效参数
                 * #define CARD_PARAM_VALID            0x00000002  //有效期参数
                 * #define CARD_PARAM_CARD_TYPE        0x00000004  //卡类型参数
                 * #define CARD_PARAM_DOOR_RIGHT       0x00000008  //门权限参数
                 * #define CARD_PARAM_LEADER_CARD      0x00000010  //首卡参数
                 * #define CARD_PARAM_SWIPE_NUM        0x00000020  //最大刷卡次数参数
                 * #define CARD_PARAM_GROUP            0x00000040  //所属群组参数
                 * #define CARD_PARAM_PASSWORD         0x00000080  //卡密码参数
                 * #define CARD_PARAM_RIGHT_PLAN       0x00000100  //卡权限计划参数
                 * #define CARD_PARAM_SWIPED_NUM       0x00000200  //已刷卡次数
                 * #define CARD_PARAM_EMPLOYEE_NO      0x00000400  //工号
                 * #define CARD_PARAM_NAME             0x00000800  //姓名
                 */

                String strCardNo = cardVo.getCardId();
                //清空
                for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
                    struCardInfo.byCardNo[i] = 0;
                }
                for (int i = 0; i < strCardNo.length(); i++) {
                    struCardInfo.byCardNo[i] = strCardNo.getBytes()[i];
                }


                struCardInfo.byCardValid = 1;
                struCardInfo.byCardType = 1;
                struCardInfo.byLeaderCard = 0;
                struCardInfo.byDoorRight[0] = 1; //门1有权限
                struCardInfo.wCardRightPlan[0].wRightPlan[0] = 1; //门1关联卡参数计划模板1

                //卡有效期
                struCardInfo.struValid.byEnable = 1;
                struCardInfo.struValid.struBeginTime.wYear = 2017;
                struCardInfo.struValid.struBeginTime.byMonth = 12;
                struCardInfo.struValid.struBeginTime.byDay = 1;
                struCardInfo.struValid.struBeginTime.byHour = 0;
                struCardInfo.struValid.struBeginTime.byMinute = 0;
                struCardInfo.struValid.struBeginTime.bySecond = 0;
                struCardInfo.struValid.struEndTime.wYear = 2018;
                struCardInfo.struValid.struEndTime.byMonth = 12;
                struCardInfo.struValid.struEndTime.byDay = 1;
                struCardInfo.struValid.struEndTime.byHour = 0;
                struCardInfo.struValid.struEndTime.byMinute = 0;
                struCardInfo.struValid.struEndTime.bySecond = 0;

                struCardInfo.dwMaxSwipeTime = 0; //无次数限制
                struCardInfo.dwSwipeTime = 0;
                struCardInfo.byCardPassword = cardVo.getCardNumber().getBytes();
                struCardInfo.dwEmployeeNo = Integer.parseInt(cardVo.getCardId());

                byte[] strCardName = cardVo.getTitle().getBytes("GBK");
                for (int i = 0; i < HCNetSDK.NAME_LEN; i++) {
                    struCardInfo.byName[i] = 0;
                }
                for (int i = 0; i < strCardName.length; i++) {
                    struCardInfo.byName[i] = strCardName[i];
                }

                struCardInfo.write();
                Pointer pSendBufSet = struCardInfo.getPointer();
                System.out.println("准备下发");
                if (!hCNetSDK.NET_DVR_SendRemoteConfig(lHandle, 0x3, pSendBufSet, struCardInfo.size())) {
                    iErr = hCNetSDK.NET_DVR_GetLastError();
                    System.out.println("ENUM_ACS_SEND_DATA失败，错误号：" + iErr);
                    return;
                }
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (!hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
                iErr = hCNetSDK.NET_DVR_GetLastError();
                System.out.println("断开长连接失败，错误号：" + iErr);
                return;
            }
            System.out.println("断开长连接成功");
        }
    }

    public class FRemoteCfgCallBackCardSet implements HCNetSDK.FRemoteConfigCallback {
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
            System.out.println("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType) {
                case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCardStatus = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCardStatus.write();
                    Pointer pInfoV30 = struCardStatus.getPointer();
                    pInfoV30.write(0, lpBuffer.getByteArray(0, struCardStatus.size()), 0, struCardStatus.size());
                    struCardStatus.read();
                    int iStatus = 0;
                    for (int i = 0; i < 4; i++) {
                        int ioffset = i * 8;
                        int iByte = struCardStatus.byStatus[i] & 0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }
                    switch (iStatus) {
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            System.out.println("下发卡参数成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            System.out.println("正在下发卡参数中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            int iErrorCode = 0;
                            for (int i = 0; i < 4; i++) {
                                int ioffset = i * 8;
                                int iByte = struCardStatus.byErrorCode[i] & 0xff;
                                iErrorCode = iErrorCode + (iByte << ioffset);
                            }
                            System.out.println("下发卡参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
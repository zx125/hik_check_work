import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.zx.hik.HCNetSDK;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class dllTest {
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

    private javax.swing.JPanel jPanelRealplayArea;
    private java.awt.Panel panelRealplay;
    boolean bRealPlay;//是否在预览.
    String m_sDeviceIP;//已登录设备的IP地址
    NativeLong lUserID = new NativeLong(-1);//用户句柄
    NativeLong lPreviewHandle = new NativeLong(-1);//预览句柄
    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//用户参数
    HCNetSDK.FMSGCallBack msgCallBack_v31;
    FRemoteCfgCallBackCardGet fRemoteCfgCallBackCardGet;
    int i=0;
    public static void main(String[] args) {
        dllTest zy=new dllTest();
        zy.CreateJFrame();
        zy.init_hCNetSDK();
        zy.init_device();
        zy.init_long();
    }
    //创建一个窗口
    public void CreateJFrame(){
        JFrame jf=new JFrame("zx");
        Container container=jf.getContentPane();
        JLabel jl=new JLabel("这是一个窗口");
        container.add(jl);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setSize(200,150);

    }
    //初始化SDK
    public void init_hCNetSDK() {
        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true) {
            System.out.println("初始化失败！");
        } else {
            System.out.println("初始化成功");
        }
    }

    //注册设备
    public void init_device() {
        //注册之前先注销已注册的用户,预览情况下不可注销
        if (bRealPlay) {
            System.out.println("注册新用户请先停止当前预览");
            return;
        }
        if (lUserID.longValue() > -1) {
            System.out.println("注销设备");
            //先注销
            hCNetSDK.NET_DVR_Logout_V30(lUserID);
            lUserID = new NativeLong(-1);
        }
        m_sDeviceIP = "192.168.0.96";//设备ip地址
        m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        int iPort = Integer.parseInt("8000");
        lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,
                (short) iPort, "admin", "abcd1234", m_strDeviceInfo);
        long userID = lUserID.longValue();
        if (userID == -1) {
            m_sDeviceIP = "";//登录未成功,IP置为空
            System.out.println("注册失败");
        } else {
            System.out.println("注册成功");
        }
    }
    //发起长连接
    public void init_long(){
        int iErr = 0;
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParam = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParam.dwSize = m_struCardInputParam.size();
        m_struCardInputParam.dwCardNum = 0xffffffff; //查找全部
        m_struCardInputParam.byCheckCardNo = 1;
        m_struCardInputParam.write();

        HCNetSDK.MY_USER_DATA userData = new HCNetSDK.MY_USER_DATA();
        userData.dwSize = userData.size();
        userData.byteData = "1234567".getBytes();
        Pointer pUserData = userData.getPointer();
        userData.write();
        Pointer lpInBuffer = m_struCardInputParam.getPointer();
        fRemoteCfgCallBackCardGet = new FRemoteCfgCallBackCardGet();
        m_struCardInputParam.write();

        NativeLong lHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_GET_CARD_CFG_V50, lpInBuffer, m_struCardInputParam.size(), fRemoteCfgCallBackCardGet, pUserData);
        if (lHandle.intValue() < 0) {
            iErr = hCNetSDK.NET_DVR_GetLastError();
            JOptionPane.showMessageDialog(null, "建立长连接失败，错误号：" + iErr);
            return;
        }
        JOptionPane.showMessageDialog(null, "建立获取卡参数长连接成功!");

    }
    public class FRemoteCfgCallBackCardGet implements HCNetSDK.FRemoteConfigCallback {
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
            HCNetSDK.MY_USER_DATA m_userData = new HCNetSDK.MY_USER_DATA();
            m_userData.write();
            Pointer pUserVData = m_userData.getPointer();
            pUserVData.write(0, pUserData.getByteArray(0, m_userData.size()), 0, m_userData.size());
            m_userData.read();

            System.out.println("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType) {
                case 0: //NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCfgStatus.write();
                    Pointer pCfgStatus = struCfgStatus.getPointer();
                    pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0, struCfgStatus.size());
                    struCfgStatus.read();

                    int iStatus = 0;
                    for (int i = 0; i < 4; i++) {
                        int ioffset = i * 8;
                        int iByte = struCfgStatus.byStatus[i] & 0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }

                    switch (iStatus) {
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            System.out.println("查询卡参数成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            System.out.println("正在查询卡参数中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            int iErrorCode = 0;
                            for (int i = 0; i < 4; i++) {
                                int ioffset = i * 8;
                                int iByte = struCfgStatus.byErrorCode[i] & 0xff;
                                iErrorCode = iErrorCode + (iByte << ioffset);
                            }
                            System.out.println("查询卡参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
                            break;
                    }
                    break;
                case 2: //NET_SDK_CALLBACK_TYPE_DATA
                    HCNetSDK.NET_DVR_CARD_CFG_V50 m_struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50();
                    m_struCardInfo.write();
                    Pointer pInfoV30 = m_struCardInfo.getPointer();
                    pInfoV30.write(0, lpBuffer.getByteArray(0, m_struCardInfo.size()), 0, m_struCardInfo.size());
                    m_struCardInfo.read();

                    String str = new String(m_struCardInfo.byCardNo);


                    try {
                        String srtName = new String(m_struCardInfo.byName, "GBK").trim(); //姓名
                        System.out.println("查询到的卡号,getCardNo:" + str + "姓名:" + srtName);
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}

package com.cepri.dev;

import android.os.Build;

import com.sgcc.pda.hardware.util.Constant;
import com.sgcc.pda.sdk.utils.FileUtils;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * Created by xuzl on 2016/10/31.
 */
public class Serialport {
    static {

        /**
         * 优化后 最终加载SO 的代码
         */
        if (FileUtils.fileIsExists("system/lib/libcepri.so")) {
            //优先加载掌机中SO
            LogUtil.d("TL", "掌机中SO库存在");
            System.loadLibrary("cepri");
        } else {
            //掌机中没有 从程序中加载
            String deviceTypte = Build.MODEL; //获取掌机设备型号
            LogUtil.d("TL", "掌机中SO库不存在" + deviceTypte);
            System.load(Constant.ASSETS_URL + deviceTypte + ".so");
//            System.loadLibrary("cepri_"+deviceTypte);
        }
    }


    /**
     * 串口初始化，包括打开电源和通信端口
     *
     * @return 0:成功 其它：错误号
     */
    public native static int init();

    /**
     * 串口通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public native static int deInit();

    /**
     * 清空串口的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int clearSendCache();

    /**
     * 清空串口的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int clearRevCache();

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位  0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位  0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public native static int config(int baudrate, int databits, int parity, int stopbits);

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public native static int sendData(byte[] data, int offset, int length);

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public native static int recvData(byte[] data, int offset);

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public native static int setTimeOut(int direction, int timeout);

}
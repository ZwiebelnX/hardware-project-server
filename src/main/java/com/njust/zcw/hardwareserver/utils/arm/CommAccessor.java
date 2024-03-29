package com.njust.zcw.hardwareserver.utils.arm;

import com.njust.zcw.hardwareserver.module.bo.ArmPosition;
import com.njust.zcw.hardwareserver.module.bo.ArmPositionInfoBO;
import com.njust.zcw.hardwareserver.module.bo.CommConfigBO;
import gnu.io.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>Title: 串口通讯相关，依赖RXTX，参考自2015级学长</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Component
public class CommAccessor implements SerialPortEventListener {

    public final List<ArmPositionInfoBO> positionHistoryList = new ArrayList<>();

    public final int[] angleList = new int[6];

    private boolean loggingAngle = false;

    public StringBuilder stringBuilder;

    // RS232串口
    private SerialPort serialPort;

    public CommAccessor(){
        CommConfigBO com1 = new CommConfigBO();
        com1.setSerialNumber("COM1");
        com1.setBaudRate(115200);
        com1.setCheckoutBit(0);
        com1.setDataBit(8);
        com1.setStopBit(1);

        init(com1);
    }

    private void init(CommConfigBO commConfigBO){
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        boolean portExist = false;
        while(portList.hasMoreElements()){
            CommPortIdentifier commPortId = portList.nextElement();
            System.out.println(commPortId.getName());
            // 判断是否是串口
            if(commPortId.getPortType() == CommPortIdentifier.PORT_SERIAL){
                // 比较串口名称是否是指定串口
                if(commConfigBO.getSerialNumber().equals(commPortId.getName())){
                    portExist = true;
                    // 打开串口
                    try{
                        // open:（应用程序名【随意命名】，阻塞时等待的毫秒数）
                        serialPort = (SerialPort) commPortId
                                .open(Object.class.getSimpleName(), 2000);
                        // 设置串口监听
                        serialPort.addEventListener(this);
                        // 设置串口数据时间有效(可监听)
                        serialPort.notifyOnDataAvailable(true);
                        // 设置串口通讯参数:波特率，数据位，停止位,校验方式
                        serialPort.setSerialPortParams(commConfigBO.getBaudRate(),
                                                       commConfigBO.getDataBit(),
                                                       commConfigBO.getStopBit(),
                                                       commConfigBO.getCheckoutBit());
                    } catch(PortInUseException e){
                        System.out.println("端口被占用");
                        e.printStackTrace();
                    } catch(TooManyListenersException e){
                        System.out.println("监听器过多");
                        e.printStackTrace();
                    } catch(UnsupportedCommOperationException e){
                        System.out.println("不支持的COMM端口操作异常");
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        if(!portExist){
            System.out.println("不存在该串口！");
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){
        switch(serialPortEvent.getEventType()){
            case SerialPortEvent.BI:    // 通讯中断
            case SerialPortEvent.OE:    // 溢位错误
            case SerialPortEvent.FE:    // 帧错误
            case SerialPortEvent.PE:    // 奇偶校验错误
            case SerialPortEvent.CD:    // 载波检测
            case SerialPortEvent.CTS:   // 清除发送
            case SerialPortEvent.DSR:   // 数据设备准备好
            case SerialPortEvent.RI:    // 响铃侦测
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
            default:
                System.out.println("error event: " + serialPortEvent.getEventType());
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 有数据到达
                readComm();
                break;
        }
    }

    /**
     * Chen Sicong & 2015级学长
     * Description: 读取串口数据
     */
    private void readComm(){
        try{
            // 输入流
            InputStream inputStream = serialPort.getInputStream();
            byte[] readBuffer = new byte[inputStream.available()];
            int len;
            if((len = inputStream.read(readBuffer)) != -1){
                // 直接获取到的数据
                // 保存串口返回信息
                String data = new String(readBuffer, 0, len).trim();
                // 转为十六进制数据
                // 保存串口返回信息十六进制
                String dataHex = bytesToHexString(readBuffer);
                System.out.println("data: " + data);
                System.out.println("dataHex: " + dataHex);

                // 处理接受数据
                if(data.length() == 1){
                    boolean positionFlag = false;
                    for(ArmPosition value : ArmPosition.values()){
                        if(data.equals(value.toString())){
                            positionFlag = true;
                            ArmPositionInfoBO armPositionInfoBO = new ArmPositionInfoBO();
                            armPositionInfoBO
                                    .setTimestamp(new Timestamp(System.currentTimeMillis()));
                            armPositionInfoBO.setDescription(value.getDescription());
                            positionHistoryList.add(armPositionInfoBO);

                            while(positionHistoryList.size() > 5){
                                positionHistoryList.remove(0);
                            }
                            break;
                        }
                    }
                    if(!positionFlag){
                        // 启动角度信息记录
                        if((data.equals("$") || data.startsWith("$")) && !loggingAngle){
                            loggingAngle = true;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(data);
                        }
                    }
                } else if(loggingAngle){
                    stringBuilder.append(data);
                    if(data.endsWith("@")){
                        loggingAngle = false;
                        String angleString = stringBuilder.toString();
                        angleString = angleString.substring(1, angleString.length() - 2);
                        System.out.println(angleString);
                        String[] angleStringList = angleString.split(",");
                        for(int i = 0; i < angleStringList.length; i++){
                            angleList[i] = (int) (Integer.parseInt(angleStringList[i]) * 0.3);
                        }


                    }
                }
                inputStream.close();
            }
        } catch(IOException e){
            System.out.println("读取串口数据时发生IO异常");
            e.printStackTrace();
        }
    }

    /**
     * Chen Sicong & 2015级学长
     * Description: 向串口发送数据
     *
     * @param data the data
     */
    public void sendComm(String data){
        byte[] writerBuffer;
        try{
            writerBuffer = hexToByteArray(data);
            System.out.println("准备发送：" + Arrays.toString(writerBuffer));
            // 输出流
            OutputStream outputStream = serialPort.getOutputStream();
            outputStream.write(writerBuffer);
            outputStream.flush();
            System.out.println("发送成功");
        } catch(NumberFormatException e){
            System.out.println("命令格式错误！");
            e.printStackTrace();
        } catch(NullPointerException e){
            System.out.println("找不到串口");
            e.printStackTrace();
        } catch(IOException e){
            System.out.println("发送信息到串口时发生IO异常");
            e.printStackTrace();
        }
    }

    /**
     * 2015级学长
     * Description:
     *
     * @param inHex the in hex
     * @return the byte [ ]
     */
    private byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if(hexlen % 2 == 1){
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else{
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for(int i = 0; i < hexlen; i += 2){
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }


    /**
     * 2015级学长
     * Description: 字节转16位 用于接收
     *
     * @param bArray the b array
     * @return the string
     */
    private String bytesToHexString(byte[] bArray){
        StringBuilder stringBuilder = new StringBuilder(bArray.length);
        String sTemp;
        for(byte b : bArray){
            sTemp = Integer.toHexString(0xFF & b);
            if(sTemp.length() < 2){
                stringBuilder.append(0);
            }
            stringBuilder.append(sTemp.toUpperCase());
        }
        return stringBuilder.toString();
    }

    /**
     * 2015级学长
     * Description: 16转字节 用于发送
     *
     * @param inHex the in hex
     * @return the byte
     */
    private byte hexToByte(String inHex){
        return (byte) Integer.parseInt(inHex, 16);
    }
}

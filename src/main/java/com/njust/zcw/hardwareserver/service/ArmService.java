package com.njust.zcw.hardwareserver.service;

import com.njust.zcw.hardwareserver.module.bo.ArmPositionInfoBO;
import com.njust.zcw.hardwareserver.module.dto.AxisControlDTO;
import com.njust.zcw.hardwareserver.module.dto.DigitalControlDTO;
import com.njust.zcw.hardwareserver.module.dto.RemoteControlDTO;
import com.njust.zcw.hardwareserver.module.dto.SendCharDTO;
import com.njust.zcw.hardwareserver.utils.arm.CommAccessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ArmService</p>
 * <p>Description: 机械臂控制相关逻辑处理，参考自2015学长</p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Service
public class ArmService {
    private final CommAccessor commAccessor;

    /**
     * Instantiates a new Arm service.
     *
     * @param commAccessor the comm accessor
     */
    public ArmService(CommAccessor commAccessor){
        this.commAccessor = commAccessor;
    }

    /**
     * Author: Chen Sicong & 2015学长
     * Description: 控制六轴
     *
     * @param axisControlDTO the axis control dto
     */
    public void axisControl(AxisControlDTO axisControlDTO){
        switch(axisControlDTO.getAxisNum()){
            case 1:
                if(axisControlDTO.isClockwise()){
                    commAccessor.sendComm("51");
                } else{
                    commAccessor.sendComm("57");
                }
                break;
            case 2:
                if(axisControlDTO.isClockwise()){
                    commAccessor.sendComm("41");
                } else{
                    commAccessor.sendComm("53");
                }
                break;
            case 3:
                if(axisControlDTO.isClockwise()){
                    commAccessor.sendComm("5a");
                } else{
                    commAccessor.sendComm("58");
                }
                break;
            case 4:
                if(axisControlDTO.isClockwise()){
                    commAccessor.sendComm("52");
                } else{
                    commAccessor.sendComm("45");
                }
                break;
            case 5:
                if(axisControlDTO.isClockwise()){
                    commAccessor.sendComm("46");
                } else{
                    commAccessor.sendComm("44");
                }
                break;
            case 6:
                if(axisControlDTO.isClockwise()){
                    commAccessor.sendComm("56");
                } else{
                    commAccessor.sendComm("43");
                }
                break;
            default:
                break;
        }
    }

    /**
     * Author:  Chen Sicong & 2015学长
     * Description: 控制机械臂连续动作
     *
     * @param remoteControlDTO the remote control dto
     */
    public void remoteControl(RemoteControlDTO remoteControlDTO){
        switch(remoteControlDTO.getControlType()){
            case "单次抓取":
                commAccessor.sendComm("64");
                System.out.println("==== 单次抓取 ====");
                break;
            case "连续抓取":
                commAccessor.sendComm("67");
                System.out.println("==== 连续抓取 ====");
                break;
            case "开传送带":
                commAccessor.sendComm("4c");
                System.out.println("==== 开传送带 ====");
                break;
            case "关传送带":
                commAccessor.sendComm("4b");
                System.out.println("==== 关传送带 ====");
                break;
            case "切换控制":
                commAccessor.sendComm("7c");
                System.out.println("==== 切换 ====");
                break;
            case "开始/暂停":
                commAccessor.sendComm("20");
                System.out.println("==== 开始/暂停 ====");
                break;
            default:
            case "复位":
                commAccessor.sendComm("66");
                System.out.println("==== 复位 ====");
                break;
        }
    }

    /**
     * Author:  Chen Sicong & 2015学长
     * Description: 控制传送带数据显示
     *
     * @param digitalControlDTO the digital control dto
     */
    public void showDigital(DigitalControlDTO digitalControlDTO){
        switch(digitalControlDTO.getShowNumber()){
            case 0:
                commAccessor.sendComm("10");
                break;
            case 1:
                commAccessor.sendComm("11");
                break;
            case 2:
                commAccessor.sendComm("12");
                break;
            case 3:
                commAccessor.sendComm("13");
                break;
            case 4:
                commAccessor.sendComm("14");
                break;
            case 5:
                commAccessor.sendComm("15");
                break;
            case 6:
                commAccessor.sendComm("16");
                break;
            case 7:
                commAccessor.sendComm("17");
                break;
            case 8:
                commAccessor.sendComm("18");
                break;
            case 9:
            default:
                commAccessor.sendComm("19");
                break;

        }
    }

    /**
     * Author: Chen Sicong
     * Description:
     *
     * @return the list
     */
    public List<ArmPositionInfoBO> getArmPositionHistory(){
        return commAccessor.positionHistoryList;
    }

    /**
     * Author: Chen Sicong
     * Description: 获取六轴位置信息
     *
     * @return the list
     */
    public List<Integer> getAngles(){
        /*
        本处存在bug
        本次请求的位置信息，只有到下一次请求才会更新至前端
        由于本请求接口采用http请求，并且此处逻辑没有采用阻塞，故请求信息会有不同步
        在CommAccessor中改为阻塞等待数据到达后可解决此问题
         */
        commAccessor.sendComm("68");
        List<Integer> list = new ArrayList<>();
        for(int i : commAccessor.angleList){
            list.add(i);
        }
        return list;
    }

    /**
     * Author: Chen Sicong
     * Description: 调试接口
     *
     * @param sendCharDTO 需要发送的信号
     */
    public void sendChar(SendCharDTO sendCharDTO){
        commAccessor.sendComm(sendCharDTO.getString());
    }
}

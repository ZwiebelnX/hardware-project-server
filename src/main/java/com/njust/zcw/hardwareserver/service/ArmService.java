package com.njust.zcw.hardwareserver.service;

import com.njust.zcw.hardwareserver.module.bo.ArmPositionInfoBO;
import com.njust.zcw.hardwareserver.module.dto.AxisControlDTO;
import com.njust.zcw.hardwareserver.module.dto.DigitalControlDTO;
import com.njust.zcw.hardwareserver.module.dto.RemoteControlDTO;
import com.njust.zcw.hardwareserver.module.dto.SendCharDTO;
import com.njust.zcw.hardwareserver.utils.arm.CommAccessor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: Service</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Service
public class ArmService {
    private final CommAccessor commAccessor;

    public ArmService(CommAccessor commAccessor){
        this.commAccessor = commAccessor;
    }

    /**
     * Author: Chen Sicong
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
     * Author: Chen Sicong
     * Description: 控制机械臂连续动作
     *
     * @param remoteControlDTO the remote control dto
     */
    public void remoteControl(RemoteControlDTO remoteControlDTO){
        switch(remoteControlDTO.getControlType()){
            case "单次抓取":
                commAccessor.sendComm("64");
                System.out.println("单次抓取");
                break;
            case "连续抓取":
                commAccessor.sendComm("67");
                System.out.println("连续抓取");
                break;
            case "开传送带":
                commAccessor.sendComm("4b");
                System.out.println("开传送带");
                break;
            case "关传送带":
                commAccessor.sendComm("4c");
                System.out.println("关传送带");
                break;
            case "切换控制":
                commAccessor.sendComm("7c");
                System.out.println("切换");
                break;
            case "开始/暂停":
                commAccessor.sendComm("20");
                System.out.println("开始/暂停");
                break;
            default:
            case "复位":
                commAccessor.sendComm("66");
                System.out.println("复位");
                break;
        }
    }

    /**
     * Author: Chen Sicong
     * Description: 控制传送带数据显示
     *
     * @param digitalControlDTO the digital control dto
     */
    public void showDigital(DigitalControlDTO digitalControlDTO){

    }

    public List<ArmPositionInfoBO> getArmPositionHistory(){
        return commAccessor.positionList;
    }

    /**
     * Author: Chen Sicong
     * Description: 调试接口
     *
     * @param sendCharDTO the send char dto
     */
    public void sendChar(SendCharDTO sendCharDTO){
        commAccessor.sendComm(sendCharDTO.getString());
    }
}

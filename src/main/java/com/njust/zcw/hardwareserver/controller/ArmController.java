package com.njust.zcw.hardwareserver.controller;

import com.njust.zcw.hardwareserver.module.bo.ArmPositionInfoBO;
import com.njust.zcw.hardwareserver.module.dto.AxisControlDTO;
import com.njust.zcw.hardwareserver.module.dto.DigitalControlDTO;
import com.njust.zcw.hardwareserver.module.dto.RemoteControlDTO;
import com.njust.zcw.hardwareserver.module.dto.SendCharDTO;
import com.njust.zcw.hardwareserver.service.ArmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title: ArmController</p>
 * <p>Description: http请求转发控制器</p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@RestController
@RequestMapping("/api/armControl")
public class ArmController {
    private final ArmService armService;

    /**
     * Instantiates a new Arm controller.
     *
     * @param armService the arm service
     */
    public ArmController(ArmService armService){
        this.armService = armService;
    }

    /**
     * Author: Chen Sicong
     * Description: 控制六轴
     *
     * @param axisControlDTO the axis control dto
     */
    @PostMapping("/axisControl")
    public void axisControl(@RequestBody AxisControlDTO axisControlDTO){
        armService.axisControl(axisControlDTO);
    }

    /**
     * Author: Chen Sicong
     * Description: 控制机械臂连续动作
     *
     * @param remoteControlDTO the remote control dto
     */
    @PostMapping("/remoteControl")
    public void remoteControl(@RequestBody RemoteControlDTO remoteControlDTO){
        armService.remoteControl(remoteControlDTO);
    }

    /**
     * Author: Chen Sicong
     * Description: 控制传送带的数字显示
     *
     * @param digitalControlDTO the digital control dto
     */
    @PostMapping("/digitalControl")
    public void digitalControl(@RequestBody DigitalControlDTO digitalControlDTO){
        armService.showDigital(digitalControlDTO);
    }

    /**
     * Author: Chen Sicong
     * Description: 前端获取机械臂位置历史信息（保留五项）
     *
     * @return 位置历史
     */
    @GetMapping("/positionHistory")
    public List<ArmPositionInfoBO> getArmPositionHistory(){
        return armService.getArmPositionHistory();
    }

    /**
     * Author: Chen Sicong
     * Description: 前端获取机械臂六轴角度信息（存在一些bug）
     *
     * @return 轴角度
     */
    @GetMapping("/angle")
    public List<Integer> getAngle(){
        return armService.getAngles();
    }


    /**
     * Author: Chen Sicong
     * Description: 调试用，用于发送多种控制信号
     *
     * @param sendCharDTO 直接向串口发送的信号
     */
    @PostMapping("/sendChar")
    public void sendChar(@RequestBody SendCharDTO sendCharDTO){
        armService.sendChar(sendCharDTO);
    }
}

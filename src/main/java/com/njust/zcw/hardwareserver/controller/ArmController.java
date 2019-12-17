package com.njust.zcw.hardwareserver.controller;

import com.njust.zcw.hardwareserver.module.dto.SendCharDTO;
import com.njust.zcw.hardwareserver.service.ArmService;
import com.njust.zcw.hardwareserver.module.dto.AxisControlDTO;
import com.njust.zcw.hardwareserver.module.dto.RemoteControlDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: Controller</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@RestController
@RequestMapping("/api/armControl")
public class ArmController{
    private final ArmService armService;

    public ArmController(ArmService armService){
        this.armService = armService;
    }

    @PostMapping("/axisControl")
    public void axisControl(@RequestBody AxisControlDTO axisControlDTO){
        armService.axisControl(axisControlDTO);
    }

    @PostMapping("/remoteControl")
    public void remoteControl(@RequestBody RemoteControlDTO remoteControlDTO){
        armService.remoteControl(remoteControlDTO);
    }

    @PostMapping("/sendChar")
    public void sendChar(@RequestBody SendCharDTO sendCharDTO) {
        armService.sendChar(sendCharDTO);
    }
}

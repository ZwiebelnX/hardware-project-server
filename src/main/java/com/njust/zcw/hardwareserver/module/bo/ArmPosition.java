package com.njust.zcw.hardwareserver.module.bo;

/**
 * <p>Title: ArmPosition</p>
 * <p>Description: 机械臂位置枚举</p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
public enum ArmPosition {
    B("进入蓝色箱子上空"), N("离开蓝色箱子上空"), A("进入传送带上空"),
    S("离开传送带上空"), I("贴近传蓝色箱子表面"), O("离开蓝色箱子表面"),
    Z("贴近传送带表面"), X("离开传送带表面");

    ArmPosition(String description){
        this.description = description;
    }

    private String description;

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}

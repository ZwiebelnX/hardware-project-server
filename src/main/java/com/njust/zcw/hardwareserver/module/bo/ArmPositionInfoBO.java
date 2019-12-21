package com.njust.zcw.hardwareserver.module.bo;

import lombok.Data;

import java.sql.Timestamp;


/**
 * <p>Title: ArmPositionInfoBO</p>
 * <p>Description: 机械臂位置BO</p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Data
public class ArmPositionInfoBO {
    private Timestamp timestamp;

    private String description;
}

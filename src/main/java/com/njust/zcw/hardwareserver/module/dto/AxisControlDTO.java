package com.njust.zcw.hardwareserver.module.dto;

import lombok.Data;

/**
 * <p>Title: AxisControlInfo</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Data
public class AxisControlDTO{
    private int axisNum;

    private boolean clockwise;
}

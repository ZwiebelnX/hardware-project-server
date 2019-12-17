package com.njust.zcw.hardwareserver.utils.arm;

import lombok.Data;

/**
 * <p>Title: ComConfig</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Data
public class CommConfig{
    private String serialNumber;

    private int baudRate;

    private int checkoutBit;

    private int dataBit;

    private int stopBit;
}

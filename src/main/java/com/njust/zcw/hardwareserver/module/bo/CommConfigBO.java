package com.njust.zcw.hardwareserver.module.bo;

import lombok.Data;

/**
 * <p>Title: CommConfigBO</p>
 * <p>Description: 串口初始化设置相关BO</p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Data
public class CommConfigBO {
    private String serialNumber;

    private int baudRate;

    private int checkoutBit;

    private int dataBit;

    private int stopBit;
}

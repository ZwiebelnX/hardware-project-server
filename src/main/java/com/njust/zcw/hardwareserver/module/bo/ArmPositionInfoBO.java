package com.njust.zcw.hardwareserver.module.bo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ArmPositionInfoBO {
    private Timestamp timestamp;

    private String description;
}

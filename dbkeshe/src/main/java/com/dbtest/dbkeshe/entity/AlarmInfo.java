package com.dbtest.dbkeshe.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AlarmInfo {
    private Long alarmid;
    private Long id;
    private Date datetime;
    private String type;
    private String picURL;
    private Kitchen kitchen;
}

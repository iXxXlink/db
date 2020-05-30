package com.dbtest.dbkeshe.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Alarm {
    private Long alarmid;
    private Long id;
    private Date datetime;
    private String type;
    private String picURL;
}

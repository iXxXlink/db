package com.dbtest.dbkeshe.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HistoryVideo implements Serializable {
    private Long videoId;
    private Long id;
    private String videoURL;
    private Date date;

}

package com.dbtest.dbkeshe.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryVideo {
    private Long videoId;
    private Long id;
    private String videoURL;
    private Date date;
}

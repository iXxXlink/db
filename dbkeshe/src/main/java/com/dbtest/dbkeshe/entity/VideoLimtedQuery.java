package com.dbtest.dbkeshe.entity;

import lombok.Data;

import java.util.Date;


@Data
public class VideoLimtedQuery {
    public int id;
    public Date dateFrom;
    public Date dateTo;
}

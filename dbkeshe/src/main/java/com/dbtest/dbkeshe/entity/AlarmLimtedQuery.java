package com.dbtest.dbkeshe.entity;


import lombok.Data;

import java.util.Date;

public class AlarmLimtedQuery {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int id;
    public Date dateFrom;
    public Date dateTo;

}

package com.dbtest.dbkeshe.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Logs {
    Date currentTime;
    String username;
    String details;
}

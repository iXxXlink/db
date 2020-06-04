package com.dbtest.dbkeshe.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Kitchen implements Serializable {
    private Long id;
    private String name;
    private String location;
}

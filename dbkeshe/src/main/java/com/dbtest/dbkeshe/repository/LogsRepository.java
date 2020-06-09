package com.dbtest.dbkeshe.repository;

import com.dbtest.dbkeshe.entity.Logs;

import java.util.List;

public interface LogsRepository {
    public void save(Logs logs);
    public List<Logs> findByName(String username);
    public List<Logs> findAll();
}

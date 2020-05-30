package com.dbtest.dbkeshe.repository;

import com.dbtest.dbkeshe.entity.Kitchen;

import java.util.List;

public interface KitchenRepository {
    public List<Kitchen> findAll();
    public Kitchen findById(Long id);
    public Kitchen findByName(String name);
    public void save(Kitchen kitchen);
    public void update(Kitchen kitchen);
    public void deleteById(Long id);
    public List<Kitchen> findAllAlarm();
    public List<Kitchen> findAllNoAlarm();
}

package com.dbtest.dbkeshe.repository;
import com.dbtest.dbkeshe.entity.Alarm;
import com.dbtest.dbkeshe.entity.AlarmInfo;
import com.dbtest.dbkeshe.entity.AlarmLimtedQuery;

import java.util.Date;
import java.util.List;

public interface AlarmRepository {
//    public List<Alarm> findAll();
    public List<AlarmInfo> findAll();
    public List<Alarm> findByKitchen(Long id);
    public List<Alarm> findByKitchenAndDate(AlarmLimtedQuery alarmLimtedQuery);
    public Alarm findById(Long alarmid);
    public void save(Alarm alarm);
    public void update(Alarm alarm);
    public void deleteById(Long alarmid);
}

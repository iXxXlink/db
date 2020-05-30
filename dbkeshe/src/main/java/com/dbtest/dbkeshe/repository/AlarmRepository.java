package com.dbtest.dbkeshe.repository;
import com.dbtest.dbkeshe.entity.Alarm;
import java.util.List;

public interface AlarmRepository {
    public List<Alarm> findAll();
    public Alarm findById(Long alarmid);
    public void save(Alarm alarm);
    public void update(Alarm alarm);
    public void deleteById(Long alarmid);
}

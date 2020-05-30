package com.dbtest.dbkeshe.controller;

import com.dbtest.dbkeshe.entity.Alarm;

import com.dbtest.dbkeshe.repository.AlarmRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/alarm")
public class AlarmHandler {

    @Autowired
    private AlarmRepository alarmRepository;

    @GetMapping("/findAll")
    public List<Alarm> findAll(){
        return alarmRepository.findAll();
    }

    @GetMapping("/findById/{alarmid}")
    public Alarm findById(@PathVariable("alarmid") Long alarmid){
        return alarmRepository.findById(alarmid);
    }


    @PostMapping("/save")
    public void save(@RequestBody Alarm alarm){
        alarmRepository.save(alarm);
    }

    @PutMapping("/update")
    public void update(@RequestBody Alarm alarm){
        alarmRepository.update(alarm);
    }

    @DeleteMapping("/deleteById/{alarmid}")
    public void deleteById(@PathVariable("alarmid") Long alarmid){
        alarmRepository.deleteById(alarmid);
    }





}



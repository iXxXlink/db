package com.dbtest.dbkeshe.controller;

import com.dbtest.dbkeshe.entity.Alarm;

import com.dbtest.dbkeshe.entity.AlarmInfo;
import com.dbtest.dbkeshe.entity.AlarmLimtedQuery;
import com.dbtest.dbkeshe.repository.AlarmRepository;


import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


//@RestController
@Controller
@RequestMapping("/alarm")
public class AlarmHandler {




    @Autowired
    private AlarmRepository alarmRepository;

    @GetMapping("/findAll")
    @ResponseBody
    public List<AlarmInfo> findAll(@PathParam(value = "pageIndex") int pageIndex, @PathParam(value = "pageSize") int pageSize){
        PageHelper.startPage(pageIndex,pageSize);
        return alarmRepository.findAll();
    }

    @GetMapping("/findById/{alarmid}")
    public Alarm findById(@PathVariable("alarmid") Long alarmid){
        return alarmRepository.findById(alarmid);
    }

    @GetMapping("count")
    @ResponseBody
    public int count(){
        List<AlarmInfo> list =alarmRepository.findAll();
        System.out.println(list.size());
        return list.size();
    }

    @PostMapping("/save")
    @ResponseBody
    public int save(@RequestBody Map map) throws ParseException {
        Long id=new Long((String) map.get("id"));
        String picURL= (String) map.get("picURL");
        String type = (String) map.get("type");
        String date= (String) map.get("datetime");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datetime= simpleDateFormat.parse(date);
        Alarm alarm = new Alarm();
        alarm.setId(id);
        alarm.setType(type);
        alarm.setDatetime(datetime);
        alarm.setPicURL(picURL);
        try {
            alarmRepository.save(alarm);
            return 1;
        }
       catch (Exception e){
            e.printStackTrace();
            return 0;
       }
    }

    @PutMapping("/update")
    public void update(@RequestBody Alarm alarm){
        alarmRepository.update(alarm);
    }

    @DeleteMapping("/deleteById/{alarmid}")
    public void deleteById(@PathVariable("alarmid") Long alarmid){
        alarmRepository.deleteById(alarmid);
    }

    @GetMapping("/findByKitchen")
    @ResponseBody
    public List<Alarm>  findByKitchen(@PathParam(value = "id") Long id){

        return (List<Alarm>) alarmRepository.findByKitchen(id);
    }

    @PostMapping("/findByKitchenAndDate")
    @ResponseBody
    public List<Alarm> findByKitchenAndDate(@RequestBody Map map) throws ParseException {
        AlarmLimtedQuery alarmLimtedQuery = new AlarmLimtedQuery();
        alarmLimtedQuery.setId((Integer) map.get("id"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse((String) map.get("dateFrom"));
        Date date2 = simpleDateFormat.parse((String) map.get("dateTo"));
        alarmLimtedQuery.setDateFrom(date1);
        alarmLimtedQuery.setDateTo(date2);
        return  alarmRepository.findByKitchenAndDate(alarmLimtedQuery);
    }

    @GetMapping("/date")
    public void TestDate(@PathParam(value = "dateFrom") String dateFrom,@PathParam(value = "dateTo") String dateTo) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse(dateFrom);
        Date date2 = simpleDateFormat.parse(dateTo);
    }
}



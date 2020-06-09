package com.dbtest.dbkeshe.controller;

import com.dbtest.dbkeshe.entity.HistoryVideo;
import com.dbtest.dbkeshe.entity.Kitchen;
import com.dbtest.dbkeshe.entity.Logs;
import com.dbtest.dbkeshe.repository.HistoryVideoRepository;
import com.dbtest.dbkeshe.repository.KitchenRepository;
import com.dbtest.dbkeshe.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RestController
@RequestMapping("/logs")
@Controller
public class LogsHandler{

    @Autowired
    private LogsRepository logsRepository;

    @PostMapping("/findByName")
    public List<Logs> findByName(@RequestParam(value = "username",required = false) String username){
        return logsRepository.findByName(username);
    }

    @PostMapping("/save")
    @ResponseBody
    public int save(@RequestBody Logs logs){
        try{
            logsRepository.save(logs);
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @GetMapping("/findAll")
    @ResponseBody
    public List<Logs> findAll(){
        List<Logs> logs = logsRepository.findAll();
        return logs;
    }





}



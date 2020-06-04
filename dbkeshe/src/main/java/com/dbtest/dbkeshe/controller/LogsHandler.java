package com.dbtest.dbkeshe.controller;

import com.dbtest.dbkeshe.entity.HistoryVideo;
import com.dbtest.dbkeshe.entity.Kitchen;
import com.dbtest.dbkeshe.entity.Logs;
import com.dbtest.dbkeshe.repository.HistoryVideoRepository;
import com.dbtest.dbkeshe.repository.KitchenRepository;
import com.dbtest.dbkeshe.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RestController
@RequestMapping("/logs")
public class LogsHandler{

    @Autowired
    private LogsRepository logsRepository;

    @PostMapping("/findByName")
    public List<Logs> findByName(@RequestParam(value = "username",required = false) String username){
        return logsRepository.findByName(username);
    }





}



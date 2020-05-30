package com.dbtest.dbkeshe.controller;

import com.dbtest.dbkeshe.entity.HistoryVideo;
import com.dbtest.dbkeshe.entity.Kitchen;
import com.dbtest.dbkeshe.repository.HistoryVideoRepository;
import com.dbtest.dbkeshe.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/historyvideo")
public class HistoryVideoHandler{

    @Autowired
    private HistoryVideoRepository historyVideoRepository;
    @Autowired
    private KitchenRepository kitchenRepository;


    @GetMapping("/findAll")
    public List<HistoryVideo> findAll(){
        return historyVideoRepository.findAll();
    }

    @GetMapping("/findById//{id}")
    public HistoryVideo findByVideoId(@PathVariable("id") Long id){
        return historyVideoRepository.findByVideoId(id);
    }
    @PostMapping("/findByName")
    public HistoryVideo findByName(@RequestParam(value = "name",required = false) String name){
        Kitchen tmp=kitchenRepository.findByName(name);
        return historyVideoRepository.findById(tmp.getId());
    }








}



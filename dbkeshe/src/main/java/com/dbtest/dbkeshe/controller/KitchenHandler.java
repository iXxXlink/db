package com.dbtest.dbkeshe.controller;

import com.dbtest.dbkeshe.entity.Kitchen;
import com.dbtest.dbkeshe.repository.KitchenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/kitchen")
public class KitchenHandler {

    @Autowired
    private KitchenRepository kitchenRepository;

    @GetMapping("/findAll")
    public List<Kitchen> findAll(){
        return kitchenRepository.findAll();
    }

    @GetMapping("/findById/{id}")
    public Kitchen findById(@PathVariable("id") Long id){
        return kitchenRepository.findById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Kitchen kitchen){
        kitchenRepository.save(kitchen);
    }

    @PutMapping("/update")
    public void update(@RequestBody Kitchen kitchen){
        kitchenRepository.update(kitchen);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id){
        kitchenRepository.deleteById(id);
    }







}



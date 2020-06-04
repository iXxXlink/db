package com.dbtest.dbkeshe.controller;


import com.dbtest.dbkeshe.entity.User;


import com.dbtest.dbkeshe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RestController
@Controller
@RequestMapping("/user")
public class UserHandler{

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user){
        User flag=userRepository.findByName(user.getUsername());
        if(flag==null){
            userRepository.register(user);
            return "注册成功!";
        }
        else {
            return"用户名已存在,注册失败!";
        }

    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody User user){
        User flag=userRepository.check(user);
        if(flag==null){
            return "登录失败!";
        }
        else{
            return "登录成功!";
        }
    }








}



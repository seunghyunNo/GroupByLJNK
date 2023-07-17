package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {


//
//
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id,Model model){
//        model.addAttribute()
        return "board/detail";
    }
//    @GetMapping("/write")
//    public void write(){}


//
//    @GetMapping("/update/{id}")
//    public String update(){
//        return "board/update";
//    }
//
//    @PostMapping("/delete")
//    public String deleteOk(){
//
//        return "board/deleteOk";
//    }


}

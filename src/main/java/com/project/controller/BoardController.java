package com.project.controller;

import com.project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;



//    @GetMapping("/detail/{id}")
//    public String detail(){}
//
//
//
//    @GetMapping("/write")
//    public void write(){}
//
//    @GetMapping("/update/{id}")
//    public String update(){}


}

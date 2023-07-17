package com.project.controller;

import com.project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class boardController {

    @Autowired
    private BoardService boardService;


    @GetMapping("/write")
    public void write(){}

//    @PostMapping("/write")
//    public String writeOk(
//            @ModelAttribute("board")
//            @Valid
//            Board board
//
//    )


    //
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id,Model model){
//        model.addAttribute()
        return "board/detail";
    }

    @GetMapping("/list")
    public void list(Integer page, Model model){
        model.addAttribute("list",boardService.list(page,model));
    }

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
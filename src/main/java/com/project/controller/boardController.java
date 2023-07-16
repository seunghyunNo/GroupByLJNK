package com.project.controller;

import com.project.domain.Board;
import com.project.service.BoardService;
import jakarta.validation.Valid;
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

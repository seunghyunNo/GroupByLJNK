package com.project.controller;

import com.project.domain.Board;
import com.project.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {


    @Autowired
    private BoardService boardService;
    //
//
    @GetMapping("/write")
    public void write(){}

    @PostMapping("/write")
    public String writeOk(
            @ModelAttribute("board")
            @Valid
            Board board,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("title", board.getTitle());
            redirectAttributes.addFlashAttribute("content", board.getContent());

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError error : errorList){
                redirectAttributes.addFlashAttribute("error_"+ error.getField(),error.getCode());
            }
        return "redirect:/board/write";
        }
        int write = boardService.write(board);
        model.addAttribute("result",write);

        return "board/writeOk";

        }

    @GetMapping("/list")
    public void list(Integer page,Model model){
        model.addAttribute("list",boardService.list(page,model));
    }



    @GetMapping("/detail/{id}")
    public String detail(@PathVariable long id,Model model){
        model.addAttribute("board",boardService.detail(id));
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

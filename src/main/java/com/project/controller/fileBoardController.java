package com.project.controller;

import com.project.domain.Board;
import com.project.service.AttachmentService;
import com.project.service.fileBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/fileboard")
public class fileBoardController {

    @Autowired
    private fileBoardService fileBoardService;

    @GetMapping("/write")
    public void write(){}

    @PostMapping("/write")
    public String writeCheck(Map<String, MultipartFile> files, Board board, Model model){

        int result =fileBoardService.write(board,files);
        model.addAttribute("result",result);

        return "fileboard/writeCheck";
    }
}

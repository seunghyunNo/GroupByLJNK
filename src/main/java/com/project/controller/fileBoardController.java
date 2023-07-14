package com.project.controller;

import com.project.service.AttachmentService;
import com.project.service.fileBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fileboard")
public class fileBoardController {

    @Autowired
    private fileBoardService fileBoardService;

    @GetMapping("/write")
    public void write(){}
}

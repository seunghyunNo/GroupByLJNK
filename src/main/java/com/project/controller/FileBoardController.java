package com.project.controller;

import com.project.domain.Board;
import com.project.service.FileBoardService;
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
public class FileBoardController {

    @Autowired
    private FileBoardService fileBoardService;

    @GetMapping("/write")
    public void write(){}

    @PostMapping("/write")
    public String writeCheck(Map<String, MultipartFile> files, Board board, Model model){

        int result =fileBoardService.write(board,files);
        model.addAttribute("result",result);

        return "fileboard/writeCheck";
    }

    @GetMapping("/list/{appId}")
    public void list(Integer page,Model model)
    {
        model.addAttribute("list",fileBoardService.list(model,page));
    }

    @GetMapping("/update")
    public String update(Long id, Model model)
    {
        model.addAttribute("fileboard",fileBoardService.findById(id));
        return "fileboard/update";
    }

    @PostMapping("/update")
    public String updateCheck(Map<String,MultipartFile> files,Board board,Long[] deleteFile,Model model)
    {
        model.addAttribute("result",fileBoardService.update(files,board,deleteFile));
        return "fileboard/updateCheck";
    }


}

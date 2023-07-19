package com.project.controller;

import com.project.domain.Board;
import com.project.service.ApiServiceImpl;
import com.project.service.FileBoardService;
import com.project.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/fileboard")
public class FileBoardController {

    @Autowired
    private FileBoardService fileBoardService;

    @Autowired
    private ApiServiceImpl apiService;


    @GetMapping("/write")
    public void write(){}

    @PostMapping("/write")
    public String writeCheck(
            Map<String, MultipartFile> files,
            Board board, Model model){

        int write =fileBoardService.write(board,files);
        model.addAttribute("result",write);

        return "fileboard/writeCheck";
    }

    @GetMapping("/list/{appId}")
    public String list(Integer page,Model model,@PathVariable String appId)
    {
        model.addAttribute("appId", appId);

        var serviceResult = apiService.getData(appId);
        LinkedHashMap<?,?> body = (LinkedHashMap) ((LinkedHashMap) serviceResult.getBody()).get(appId);

        model.addAttribute("list",fileBoardService.list(model,page));

        return "fileboard/list";
    }

    @GetMapping("/update/{id}")
    public String update(Long id, Model model)
    {
        model.addAttribute("board",fileBoardService.findById(id));
        return "fileboard/update";
    }

    @PostMapping("/update")
    public String updateCheck(Map<String,MultipartFile> files,Board board,Long[] deleteFile,Model model)
    {
        model.addAttribute("result",fileBoardService.update(files,board,deleteFile));
        return "fileboard/updateCheck";
    }

    @PostMapping("/pageRows")
    public String pageRows(Integer page,Integer pageRows)
    {
        Util.getSession().setAttribute("pageRows",pageRows);
        return "redirect:/fileboard/list?page="+page;
    }


}

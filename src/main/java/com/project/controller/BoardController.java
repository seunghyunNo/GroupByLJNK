package com.project.controller;

import com.project.domain.Board;
import com.project.domain.BoardValidator;
import com.project.service.ApiService;
import com.project.service.BoardService;
import com.project.util.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {


    @Autowired
    private BoardService boardService;

    @Autowired
    private ApiService apiService;
//
    @GetMapping("/write/{appId}")
    public String write(@PathVariable String appId
                        ,Model model){
        model.addAttribute("appId",appId);
        return "board/write";
    }


    @PostMapping("/write")
    public String writeOk(
            @ModelAttribute("board")
            @Valid
            Board board
            ,Model model
            ,RedirectAttributes redirectAttributes

    ){
        board.setIs_file(false);
        int write = boardService.write(board);
        model.addAttribute("result",write);
        return "board/writeOk";
    }

//    @PostMapping("/write")
//    public String writeOk(
//            @ModelAttribute("board")
//            @Valid
//            Board board,
//            BindingResult result,
//            Model model,
//            RedirectAttributes redirectAttributes
//    ) {
//        if (result.hasErrors()) {
//            redirectAttributes.addFlashAttribute("title", board.getTitle());
//            redirectAttributes.addFlashAttribute("content", board.getContent());
//
//            List<FieldError> errorList = result.getFieldErrors();
//            for(FieldError error : errorList){
//                redirectAttributes.addFlashAttribute("error_"+ error.getField(),error.getCode());
//            }
//        return "redirect:/board/write";
//        }
//        int write = boardService.write(board);
//        model.addAttribute("result",write);
//
//        return "board/writeOk";
//
//        }

    @GetMapping("/list/{appId}")
    public String list(Integer page,Model model,@PathVariable String appId){
        model.addAttribute("appId",appId);

        var serviceResult = apiService.getData(appId);
        LinkedHashMap<?,?> body = (LinkedHashMap) ((LinkedHashMap) serviceResult.getBody()).get(appId);
        String status = serviceResult.getStatusCode().toString();

        model.addAttribute("status", status);
        if (body.get("success").toString().equals("true")) {
            model.addAttribute("data", body.get("data"));
        }

        model.addAttribute("list",boardService.list(page, model));

        return "board/list";
    }



//    @GetMapping("/detail/{id}")
//    public String detail(
//            @PathVariable Long id,
//            @PathVariable String appId,Model model){
//        model.addAttribute("appId",appId);
//
//        var serviceResult = apiService.getData(appId);
//        LinkedHashMap<?,?> body = (LinkedHashMap) ((LinkedHashMap) serviceResult.getBody()).get(appId);
//        String status = serviceResult.getStatusCode().toString();
//
//        model.addAttribute("status", status);
//        if (body.get("success").toString().equals("true")) {
//            model.addAttribute("data", body.get("data"));
//        }
//
//        model.addAttribute("board",boardService.detail(id));
//
//        return "board/detail";
//    }

    @GetMapping("/detail/{appId}/{id}")
    public String detail(@PathVariable String appId,@PathVariable Long id,Model model){
        model.addAttribute("appId", appId);
        model.addAttribute("board", boardService.detail(id));
        return "board/detail";
    }




    @GetMapping("/update/{appId}/{id}")
    public String update(@PathVariable String appId, @PathVariable Long id, Model model){
        model.addAttribute("appId",appId);
        model.addAttribute("board",boardService.selectById(id));
        return "board/update";
    }

    @PostMapping("/update/{appId}")
    public String updateOk(
            @PathVariable String appId,
            @Valid Board board
            ,BindingResult result
            ,Model model
            ,RedirectAttributes redirectAttributes){
//        if(result.hasErrors()){
//            redirectAttributes.addFlashAttribute("title",board.getTitle());
//            redirectAttributes.addFlashAttribute("content",board.getContent());
//            List<FieldError> errorList = result.getFieldErrors();
//            for(FieldError error : errorList){
//                System.out.println(error.getField()+":"+error.getCode());
//                redirectAttributes.addAttribute("error_"+error.getField(),error.getCode());
//            }
//            return "redirect:/board/update/{appId}"+board.getId();
//        }
        model.addAttribute("appId",appId);
        model.addAttribute("result",boardService.update(board));
        return "board/updateOk";
    }

    @PostMapping("/delete")
    public String deleteOk(
            Long id,Model model){
        model.addAttribute("result",boardService.deleteById(id));
        return "board/deleteOk";
    }

    @PostMapping("/pageRows")
    public String pageRows(Integer page,Integer pageRows){
        Util.getSession().setAttribute("pageRows",pageRows);
        return "redirect:/board/list?page="+page;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new BoardValidator());
    }
}

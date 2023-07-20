package com.project.controller;

import com.project.domain.Board;
import com.project.domain.BoardValidator;
import com.project.service.ApiServiceImpl;
import com.project.service.FileBoardService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fileboard")
public class FileBoardController {

    @Autowired
    private FileBoardService fileBoardService;

    @Autowired
    private ApiServiceImpl apiService;

    @GetMapping("/write")
    public void write(){

    }

    @PostMapping("/write")
    public String writeCheck(
            @RequestParam Map<String, MultipartFile> files,
            @ModelAttribute("board")
            @Valid
            Board board
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
    ){

//        if(result.hasErrors())
//        {
//            redirectAttributes.addFlashAttribute("content",board.getContent());
//            List<FieldError> errList = result.getFieldErrors();
//            for(FieldError err : errList){
//                System.out.println(err.getField() + " : " + err.getCode());
//                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
//            }
//
//            return "redirect:/fileboard/write";
//        }

        int write =fileBoardService.write(board,files);
        model.addAttribute("result",write);

        return "fileboard/writeCheck";
    }

    @GetMapping("/list/{appId}")
    public String list(Integer page,Model model,@PathVariable String appId)
    {

        model.addAttribute("appId", appId);

        model.addAttribute("list",fileBoardService.list(model,page));

        return "fileboard/list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model)
    {
        model.addAttribute("board",fileBoardService.findById(id));
        return "fileboard/update";
    }


    @PostMapping("/update")
    public String updateCheck(
            @RequestParam Map<String,MultipartFile> files,
            @Valid Board board,
            Long[] deleteFile,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors()){
            // redirect 시 기좀에 입력했던 값들은 보이게 하기
            redirectAttributes.addFlashAttribute("content", board.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList){
                System.out.println(err.getField() + " : " + err.getCode());
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/fileboard/update/" + board.getId();   // GET
        }


        model.addAttribute("result",fileBoardService.update(files,board,deleteFile));
        return "fileboard/updateCheck";
    }

    @PostMapping("/pageRows")
    public String pageRows(Integer page,Integer pageRows)
    {
        Util.getSession().setAttribute("pageRows",pageRows);
        return "redirect:/fileboard/list?page="+page;
    }

    @InitBinder  // 이 컨트롤러 클래스의 handler 에서 폼 데이터를 바인딩 할때 검증하는 Validator 객체 지정
    public void initBinder(WebDataBinder binder){
        System.out.println("initBinder() 호출");
        binder.setValidator(new BoardValidator());
    }


}

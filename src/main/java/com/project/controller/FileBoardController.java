package com.project.controller;

import com.project.domain.FileBoard;
import com.project.domain.FileBoardValidator;
import com.project.service.ApiServiceImpl;
import com.project.service.FileBoardService;
import com.project.service.UserService;
import com.project.util.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/fileboard")
public class FileBoardController {

    @Autowired
    private FileBoardService fileBoardService;

    @Autowired
    private UserService userService;


    @Autowired
    private ApiServiceImpl apiService;

    @GetMapping("/write/{appId}")
    public String write(@PathVariable String appId, Model model){
        model.addAttribute("appId", appId);
        return "fileboard/write";
    }

    @PostMapping("/write/{appId}")
    public String writeCheck(
            @RequestParam Map<String, MultipartFile> files,
            @ModelAttribute("fileBoard")
            @Valid
            FileBoard fileBoard
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
            ,@PathVariable String appId
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

        int write =fileBoardService.write(fileBoard,files,appId);
        model.addAttribute("result",write);
        model.addAttribute("appId", appId);

        return "fileboard/writeCheck";
    }

    @GetMapping("/list/{appId}")
    public String list(Integer page,Model model,@PathVariable String appId)
    {

        model.addAttribute("appId", appId);
        var serviceResult = apiService.getData(appId);
        LinkedHashMap<?,?> body = (LinkedHashMap) ((LinkedHashMap) serviceResult.getBody()).get(appId);
        String status = serviceResult.getStatusCode().toString();

        model.addAttribute("status", status);
        if (body.get("success").toString().equals("true")) {
            model.addAttribute("data", body.get("data"));
        }

        model.addAttribute("list",fileBoardService.list(model,page,appId));

        return "fileboard/list";
    }
    @PostMapping("/list/recommend")
    public @ResponseBody int recommend(@RequestParam Long userId,@RequestParam String boardId,@RequestParam String count)
    {
        System.out.println(userId);
        System.out.println(boardId);
        System.out.println(count);
//        Long user_Id = Long.parseLong(userId);
        Long board_id = Long.parseLong(boardId);
        Long cnt = Long.parseLong(count);
        return fileBoardService.countCheck(userId,board_id,cnt);
    }


    @GetMapping("/update/{appId}/{id}")
    public String update(@PathVariable String appId,@PathVariable Long id, Model model)
    {
        model.addAttribute("appId", appId);
        model.addAttribute("fileBoard",fileBoardService.findById(id));
        return "fileboard/update";
    }


    @PostMapping("/update/{appId}")
    public String updateCheck(
            @Valid FileBoard fileBoard,
            @RequestParam Map<String,MultipartFile> files,
            Long[] deleteFile,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @PathVariable String appId
    )
    {
            if(result.hasErrors()){
            // redirect 시 기좀에 입력했던 값들은 보이게 하기
            redirectAttributes.addFlashAttribute("content", fileBoard.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList){
                System.out.println(err.getField() + " : " + err.getCode());
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }
            return "redirect:/fileboard/update/" + fileBoard.getId();   // GET


            }

        model.addAttribute("appId", appId);
        model.addAttribute("result",fileBoardService.update(files, fileBoard, deleteFile));
        return "fileboard/updateCheck";
    }

    @PostMapping("/delete")
    public String deleteOk(Long id, Model model){
        model.addAttribute("result", fileBoardService.deleteById(id));
        return "fileboard/deleteCheck";
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
        binder.setValidator(new FileBoardValidator());
    }


}

package com.project.controller;

import com.project.config.PrincipalDetails;
import com.project.domain.FileBoard;
import com.project.domain.FileBoardValidator;
import com.project.domain.User;
import com.project.service.ApiServiceImpl;
import com.project.service.FileBoardService;
import com.project.service.RecommendService;
import com.project.service.UserService;
import com.project.util.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private RecommendService recommendService;


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
            @Valid FileBoard fileBoard
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
            , @PathVariable String appId
    ){

        if(result.hasErrors())
        {
            redirectAttributes.addFlashAttribute("content",fileBoard.getContent());
            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList){
                System.out.println(err.getField() + " : " + err.getCode());
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/fileboard/write/"+appId;
        }

        int write =fileBoardService.write(fileBoard,files,appId);
        model.addAttribute("result",write);
        model.addAttribute("appId", appId);

        return "fileboard/writeCheck";
    }

    @GetMapping("/list/{appId}")
    public String list(Integer page,Model model,@PathVariable String appId)
    {
        model.addAttribute("src","https://cdn.akamai.steamstatic.com/steam/apps/" + appId + "/header.jpg");

        model.addAttribute("appId", appId);
        var serviceResult = apiService.getData(appId);
        LinkedHashMap<?,?> body = (LinkedHashMap) ((LinkedHashMap) serviceResult.getBody()).get(appId);
        String status = serviceResult.getStatusCode().toString();

        model.addAttribute("status", status);
        if (body.get("success").toString().equals("true")) {
            model.addAttribute("data", body.get("data"));
        }

        model.addAttribute("list",fileBoardService.list(model,page,appId));

        try{
            PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDetails.getUser();
            Long id = user.getId();
            model.addAttribute("logged_id", id);
        } catch (Exception e){
            model.addAttribute("logged_id", null);
        }

        return "fileboard/list";
    }

    @PostMapping("/list/downCount")
    public @ResponseBody void downCount(Long id)
    {
       fileBoardService.donwloadCount(id);
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
            redirectAttributes.addFlashAttribute("fileList",fileBoard.getFileList());
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
    public String pageRows(Integer page,Integer pageRows,String appId)
    {
        Util.getSession().setAttribute("pageRows",pageRows);
        return "redirect:/fileboard/list/"+appId+ "?page="+page;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        System.out.println("FileBoard initBinder() 호출");
        binder.setValidator(new FileBoardValidator());
    }



}

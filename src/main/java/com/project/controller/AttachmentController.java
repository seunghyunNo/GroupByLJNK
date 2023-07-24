//package com.project.controller;
//
//import com.project.domain.Attachment;
//import com.project.service.AttachmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//public class AttachmentController {
//    @Value("{app.upload.path}")
//    private String uploadDirectory;
//
//    private AttachmentService attachmentService;
//
//    @Autowired
//    public void setAttachmentService(AttachmentService attachmentService) {
//        this.attachmentService = attachmentService;
//    }
//
//    @RequestMapping("/fileboard/download")
//    public ResponseEntity<Object> fileDownload(Long id)
//    {
//        if(id == null){
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//
//        Attachment file = attachmentService.findByFileId(id);
//
//        if(file == null)
//        {
//            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//        }
//
//        String sourceName = file.getSourcename();
//        String fileName = file.getFilename();
//
//
//        String path = new File(uploadDirectory+ File.separator+fileName).getAbsolutePath();
//
//        try {
//            String type = Files.probeContentType(Paths.get(path));
//
//            if(type == null)
//            {
//                type = "application/octet-stream";
//            }
//
//            Path filePath = Paths.get(path);
//            Resource resource = new InputStreamResource(Files.newInputStream(filePath));
//
//            HttpHeaders header = new HttpHeaders();
//
//            header.setContentType(MediaType.parseMediaType(type));
//            header.setCacheControl("no-cache");
//            header.setContentDisposition(ContentDisposition.builder("attachment").filename(URLEncoder.encode(sourceName,"utf-8")).build());
//
//            return new ResponseEntity<>(resource,header,HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
//        }
//    }
//
//
//}

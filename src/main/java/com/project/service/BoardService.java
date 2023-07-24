//package com.project.service;
//
//import com.project.domain.FileBoard;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//
//import java.util.List;
//
//public interface BoardService {
//    int write(FileBoard fileBoard);
//
//
//
//    @Transactional
//    FileBoard detail(Long id);
//
//
//    List<FileBoard> list();
//
//    List<FileBoard> list(Integer page, Model model);
//
//    FileBoard selectById(Long id);
//
//    int update(FileBoard fileBoard);
//
//    int deleteById(Long id);
//}
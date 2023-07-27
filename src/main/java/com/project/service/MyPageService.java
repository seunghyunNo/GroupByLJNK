package com.project.service;

import com.project.domain.Board;
import com.project.domain.FileBoard;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public interface MyPageService {

    public List<FileBoard> fileList(Model model, Integer page, long id);

    Object boardList(Model model, Integer page, Long id);
}

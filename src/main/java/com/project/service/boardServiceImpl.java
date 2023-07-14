package com.project.service;

import com.project.domain.Board;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class boardServiceImpl implements boardService {


    @Override
    public int write(Board board) {
        return 0;
    }

    @Override
    public int write(Board board, Map<String, MultipartFile> files) {

        return 0;
    }

    @Override
    public Board detail(Long id) {
        return null;
    }

    @Override
    public List<Board> list() {
        return null;
    }

    @Override
    public List<Board> list(Integer page, Model model) {
        return null;
    }

    @Override
    public Board selectById(Long id) {
        return null;
    }

    @Override
    public int update(Board board) {
        return 0;
    }

    @Override
    public int update(Board board, Map<String, MultipartFile> files, Long[] defile) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}

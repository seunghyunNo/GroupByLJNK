package com.project.service;

import com.project.domain.Board;
import com.project.repository.fileBoardRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@Service
public class fileBoardServiceImpl implements fileBoardService  {

    private fileBoardRepository fileRepository;

    @Autowired
    public fileBoardServiceImpl(SqlSession session) {
        fileRepository = session.getMapper(fileBoardRepository.class);
    }

    @Override
    public int write(Board board, Map<String, MultipartFile> files) {
        return 0;
    }

    @Override
    public List<Board> list() {
        return null;
    }

    @Override
    public List<Board> list(Model model, Integer page) {
        return null;
    }

    @Override
    public int update(Map<String, MultipartFile> files, Board board, Long[] deletefiles) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}

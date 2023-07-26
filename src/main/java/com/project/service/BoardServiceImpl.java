//package com.project.service;
//
//import com.project.domain.FileBoard;
//import com.project.repository.BoardRepository;
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//
//import java.util.List;
//
//@Service
//public class BoardServiceImpl implements BoardService {
//
//
//    private BoardRepository boardRepository;
//
//
//    @Autowired
//    public BoardServiceImpl(SqlSession sqlSession){
//        boardRepository=sqlSession.getMapper(BoardRepository.class);
//    }
//
//
//    @Override
//    public int write(FileBoard fileBoard) {
//
//        return boardRepository.write(fileBoard);
//    }
//
//
//
//    @Override
//    public FileBoard detail(Long id) {
//        boardRepository.viewCnt(id);
//        FileBoard fileBoard = boardRepository.findById(id);
//
//        return fileBoard;
//    }
//
//    @Override
//    public List<FileBoard> list() {
//
//        return boardRepository.countAll();
//    }
//
//    @Override
//    public List<FileBoard> list(Integer page, Model model) {
//        return null;
//    }
//
//    @Override
//    public FileBoard selectById(Long id) {
//        FileBoard fileBoard = boardRepository.findById(id);
//        return fileBoard;
//    }
//
//    @Override
//    public int update(FileBoard fileBoard) {
//
//        return boardRepository.update(fileBoard);
//    }
//
//    @Override
//    public int deleteById(Long id) {
//        int result = 0;
//
//        FileBoard fileBoard = boardRepository.findById(id);
//        if(fileBoard != null){
//            result = boardRepository.delete(fileBoard);
//        }
//
//        return result;
//    }
//}
package com.project.service;

import com.project.domain.Board;
import com.project.domain.User;
import com.project.repository.BoardRecommendRepository;
import com.project.repository.BoardRepository;
import com.project.repository.UserRepository;
import com.project.util.Util;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {


    private BoardRepository boardRepository;
    private UserRepository userRepository;

    private BoardRecommendRepository boardRecommendRepository;


    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;

    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;


    @Autowired
    public BoardServiceImpl(SqlSession sqlSession){
        boardRepository=sqlSession.getMapper(BoardRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);

    }

    // 글 작성
    @Override
    public int write(Board board) {

        return boardRepository.write(board);
    }


    // 글 조회+ 조회수 증가
    @Override
    @Transactional
    public Board detail(Long id) {
        boardRepository.viewCnt(id);
        Board board = boardRepository.findById(id);

        return board;
    }

    @Override
    public List<Board> list() {

        return boardRepository.list();
    }

    @Override
    public List<Board> list(Integer page, Model model, String appId) {

        if(page== null|| page<1){
            page=1;
        }
        HttpSession session = Util.getSession();
        Integer writePage = (Integer) session.getAttribute("writePage");
        Integer pageRows = (Integer) session.getAttribute("pageRows");
        if(writePage==null){
            writePage=WRITE_PAGES;
        }
        if(pageRows==null){
            pageRows=PAGE_ROWS;
        }
        session.setAttribute("page",page);

        long count = boardRepository.countAll(appId);
        int totalPage=(int) Math.ceil(count/(double)pageRows);

        if(page>totalPage){
            page=totalPage;
        }

        int fromRow = (page-1) * pageRows ;
        if(page==0){
            fromRow=0;
        }

        int start= (((page-1)/ writePage) * writePage) + 1;
        int end=start+writePage-1;
        if(end >= totalPage)end=totalPage;

        model.addAttribute("count",count);
        model.addAttribute("page",page);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageRows",pageRows);

        model.addAttribute("url",Util.getRequest().getRequestURI());
        model.addAttribute("writePage",writePage);
        model.addAttribute("start",start);
        model.addAttribute("end",end);

        List<Board> list = boardRepository.selectByPage(fromRow,pageRows, appId);
        for(Board board:list){
            Long id= board.getId();
            Long recommendCount = boardRepository.recommendCount(id);
            board.setRecommendCount(recommendCount);
        }

        model.addAttribute("list",list);
        return list;
    }

    @Override
    public Board selectById(Long id) {
        Board board = boardRepository.findById(id);
        return board;
    }

    @Override
    public int update(Board board) {

        return boardRepository.update(board);
    }

    @Override
    public int deleteById(Long id) {
        int result = 0;

        Board board = boardRepository.findById(id);
        if(board != null){
            result = boardRepository.delete(board);
        }

        return result;
    }
}
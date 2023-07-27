package com.project.service;

import com.project.domain.Attachment;
import com.project.domain.Board;
import com.project.domain.FileBoard;
import com.project.repository.*;
import com.project.util.Util;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService {

    private MyPageRepository myPageRepository;

    private FileBoardRepository fileBoardRepository;

    private RecommendRepository recommendRepository;

    private AttachmentRepository attachmentRepository;


    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;

    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;

    public MyPageServiceImpl(SqlSession sqlSession){
        myPageRepository = sqlSession.getMapper(MyPageRepository.class);
        fileBoardRepository = sqlSession.getMapper(FileBoardRepository.class);
        recommendRepository = sqlSession.getMapper(RecommendRepository.class);
        attachmentRepository = sqlSession.getMapper(AttachmentRepository.class);
    }

    @Override
    public List<FileBoard> fileList(Model model, Integer page, long id) {

        if(page == null || page < 1)
        {
            page = 1;
        }

        HttpSession session = Util.getSession();
        Integer writePage = (Integer)session.getAttribute("writePage");
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(writePage == null)
        {
            writePage = WRITE_PAGES;
        }
        if(pageRows == null)
        {
            pageRows = PAGE_ROWS;
        }

        session.setAttribute("page",page);

        long count = myPageRepository.fileCountAll(id);

        int totalPage = (int)Math.ceil(count/(double)pageRows);

        if(page > totalPage)
        {
            page = totalPage;
        }
        int fromRow = (page - 1) * pageRows;;

        if(page == 0)
        {
            fromRow = 0;
        }

        int start = (((page -1)/ writePage)*writePage)+1;
        int end = start + writePage - 1;
        if(end >= totalPage) end = totalPage;

        model.addAttribute("count",count);
        model.addAttribute("page",page);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageRows",pageRows);

        model.addAttribute("url",Util.getRequest().getRequestURI());
        model.addAttribute("writePage",writePage);
        model.addAttribute("start",start);
        model.addAttribute("end",end);

        List<FileBoard> list = myPageRepository.fileSelectByPage(fromRow,pageRows,id);


        for(int i = 0; i < list.size() ; i++)
        {
            Long boardId = list.get(i).getId();
            FileBoard fileBoard = fileBoardRepository.searchById(boardId);
            if(fileBoard != null)
            {
                List<Attachment> files = attachmentRepository.findByFileBoard(boardId);
                fileBoard.setFileList(files);
                fileBoard.setRecommend(recommendRepository.countByBoardId(boardId));
            }
            list.set(i,fileBoard);

        }

        model.addAttribute("list",list);
        return list;
    }

    @Override
    public Object boardList(Model model, Integer page, Long id) {
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

        long count = myPageRepository.boardCountAll(id);
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

        List<Board> list = myPageRepository.boardSelectByPage(fromRow,pageRows, id);

        for(Board board : list){
            Long boardId= board.getId();
            Long recommendCount = myPageRepository.recommendCount(boardId);
            board.setRecommendCount(recommendCount);
        }

        model.addAttribute("list",list);
        return list;
    }
}

package com.project.service;

import com.project.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {
    int write(Board board);

    @Transactional
    Board detail(Long id);

    List<Board> list();

    Board selectById(Long id);

    int update(Board board);

    int deleteById(Long id);
}

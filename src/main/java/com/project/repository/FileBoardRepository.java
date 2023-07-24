package com.project.repository;

import com.project.domain.FileBoard;

import java.util.List;

public interface FileBoardRepository {
    int write(FileBoard fileBoard, String appId);
    List<FileBoard> list();

    FileBoard searchById(Long id);

    int update(FileBoard fileBoard);

    int delete(FileBoard fileBoard);

    List<FileBoard> selectByPage(int from, int row, String appId);

    int countAll(String appId);

    int setCount(Long count,Long boardId);
}

package com.project.repository;

import com.project.domain.Attachment;

import java.util.List;
import java.util.Map;

public interface AttachmentRepository {
    int insert(List<Map<String,Object>> list, Long boardId);

    Integer saveFile(Attachment file);


    List<Attachment> findByFileBoard(Long boardId);

    Attachment findByFileId(Long id);

    List<Attachment> findByFileIds(Long[] ids);

    int deleteByFileId(Long[] ids);

    int delete(Attachment file);
}

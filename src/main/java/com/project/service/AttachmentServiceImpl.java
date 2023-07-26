package com.project.service;

import com.project.domain.Attachment;
import com.project.repository.AttachmentRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService{

    private AttachmentRepository attachmentRepository;


    @Autowired
    public AttachmentServiceImpl(SqlSession sqlSession) {
        attachmentRepository = sqlSession.getMapper(AttachmentRepository.class);
    }

    @Override
    public Attachment findByFileId(Long id) {
        return attachmentRepository.findByFileId(id);
    }
}
package com.project.service;

import com.project.domain.Attachment;

public interface AttachmentService {
    Attachment findByFileId(Long id);

}

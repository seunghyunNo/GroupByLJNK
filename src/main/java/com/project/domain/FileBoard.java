package com.project.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileBoard {
    private Long id;
    private LocalDateTime regdate;
    private Long count;
    private String title;           // NN
    private String content;         // NN
    private Boolean is_file;        // NN

    private String game_id;         // NN

    private User user;

    @Builder.Default
    private List<Attachment> fileList = new ArrayList<>();

    private Integer recommend;

}

package com.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    private Long id;
    private LocalDateTime regdate;
    private Long count;
    private String title;           // NN
    private String content;         // NN
    private Boolean is_file;        // NN


    private String game_id;         // NN
    private Long user_id;   // FK   // NN
}

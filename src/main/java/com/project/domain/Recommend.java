package com.project.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommend {
    private Long user_id;

    private Long board_id;

    @ToString.Exclude
    private User user;


    @ToString.Exclude
    private Board board;
}

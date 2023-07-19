package com.project.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommend {

    private User user;

    private Board board;
}

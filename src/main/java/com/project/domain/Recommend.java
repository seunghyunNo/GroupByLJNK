package com.project.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommend {
    private Long userId;

    private Long boardId;

}

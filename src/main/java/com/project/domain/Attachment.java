package com.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
    private Long id;                //NN
    private String sourcename;      //NN
    private String filename;        //NN


    private Long board_id;          //NN
}

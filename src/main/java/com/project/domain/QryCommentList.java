package com.project.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QryCommentList extends QryResult {

    @JsonProperty("data")  //data 이름으로 변환
    List<Comment> list;

}

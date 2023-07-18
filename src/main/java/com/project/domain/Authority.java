package com.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authority {

    // Authority id => pk
    private long id;
    // Authority name => ROLE_MEMBER, ROLE_ADMIN
    private String name;
}

package com.project.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private long id;
    private String username;
    private String password;
    @ToString.Exclude
    private String re_password;
    private String email;

}

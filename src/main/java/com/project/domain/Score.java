package com.project.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {
	private Long userId;
	private String appId;
	private Long score;
	private String content;
	
	@ToString.Exclude
	private User user;
}

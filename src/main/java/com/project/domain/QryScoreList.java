package com.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QryScoreList extends QryResult {
	@JsonProperty("data")
	List<Score> scores;
}

package com.devsuperior.dsmovie.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ScoreDTO {

	private Long movieId;
	
	private String userEmail;
	
	private Double score;
	
}

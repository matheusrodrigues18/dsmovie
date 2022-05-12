package com.devsuperior.dsmovie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmovie.dtos.MovieDTO;
import com.devsuperior.dsmovie.dtos.ScoreDTO;
import com.devsuperior.dsmovie.entities.Movie;
import com.devsuperior.dsmovie.entities.Score;
import com.devsuperior.dsmovie.entities.User;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.repositories.UserRepository;

@Service
public class ScoreService {

	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ScoreRepository scoreRepo;
	
	@Transactional
	public MovieDTO saveScore(ScoreDTO request) {
		
		User user = userRepo.findByEmail(request.getUserEmail());
		
		if(user==null) {
			
			user = new User();
			user.setEmail(request.getUserEmail());
			user = userRepo.saveAndFlush(user);
				
		}
		
		Movie movie = movieRepo.findById(request.getMovieId()).orElse(null);
		
		Score score = new Score();
		
		score.setUser(user);
		score.setMovie(movie);
		score.setValue(request.getScore());
		
		score = scoreRepo.saveAndFlush(score);
		
		double sum = 0;
		
		for(Score s : movie.getScores())sum+=s.getValue();
		
		double avg = sum/movie.getScores().size();
		
		movie.setScore(avg);
		movie.setCount(movie.getScores().size());
		
		movie = movieRepo.saveAndFlush(movie);
		
		return new MovieDTO(movie);
	}
}

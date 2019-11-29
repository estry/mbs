package mbs.domain.service.movie;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mbs.domain.model.Movie;
import mbs.domain.model.AvailableMovie;
import mbs.domain.model.AvailableMovieId;
import mbs.domain.repository.movie.MovieRepository;
import mbs.domain.repository.movie.AvailableMovieRepository;

@Service
@Transactional
public class MovieService {
	
    @Autowired
    AvailableMovieRepository availableMovieRepository;
    MovieRepository movieRepository;
    public List<AvailableMovie> findReservableMovies(LocalDate date) {
        return availableMovieRepository
                .findByAvailableMovieId_availableDateOrderByAvailableMovieId_movieIdAsc(date);
    }

	public List<AvailableMovie> findAllMovies(LocalDate date) {
		return availableMovieRepository.findAll();
	}
	public List<Movie> findAllMovies()
	{
		return movieRepository.findAll();	
	}

	public Movie findMovie(Integer movieId) {
		return movieRepository.findOne(movieId);
	}
}
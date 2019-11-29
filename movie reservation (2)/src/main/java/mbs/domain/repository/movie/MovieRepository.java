package mbs.domain.repository.movie;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import mbs.domain.model.Movie;

public interface MovieRepository extends JpaRepository<Movie , Integer > {
	
    List<Movie> findByMovieId(@Param("movie_Id") Integer movieId);
    
}

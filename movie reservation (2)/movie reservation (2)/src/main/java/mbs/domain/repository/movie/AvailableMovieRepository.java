package mbs.domain.repository.movie;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import mbs.domain.model.AvailableMovie;
import mbs.domain.model.AvailableMovieId;

public interface AvailableMovieRepository extends JpaRepository<AvailableMovie, AvailableMovieId> {
	
    List<AvailableMovie> findByAvailableMovieId_availableDateOrderByAvailableMovieId_movieIdAsc(
            @Param("date") LocalDate availableDate);
    
}
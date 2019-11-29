package mbs.domain.repository.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mbs.domain.model.AvailableMovieId;
import mbs.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	List<Reservation> findByAvailableMovie_AvailableMovieIdOrderByStartTimeAsc(AvailableMovieId availableMovieId);
}

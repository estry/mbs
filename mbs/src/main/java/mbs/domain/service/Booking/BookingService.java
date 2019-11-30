package mbs.domain.service.Booking;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mbs.domain.model.AvailableMovie;
import mbs.domain.model.AvailableMovieId;
import mbs.domain.model.Reservation;
import mbs.domain.model.RoleName;
import mbs.domain.model.User;
import mbs.domain.repository.movie.AvailableMovieRepository;
import mbs.domain.repository.reservation.ReservationRepository;

@Service
@Transactional
public class BookingService {
	
	 @Autowired
	    AvailableMovieRepository availableMovieRepository;
	 @Autowired
	    ReservationRepository reservationRepository;
	public Reservation reserve (Reservation reservation) {
		AvailableMovieId availableMovieId = reservation.getAvailableMovie().getReservableMovieId();
		Integer remain = 1;
		List<AvailableMovie> movies = availableMovieRepository.findAll();
    	AvailableMovie reservable = null;
    	for(int i=0;i<movies.size();i++)  
    	{
    		if(movies.get(i).getReservableMovieId().equals(availableMovieId))
    		{
    			reservable = movies.get(i);
    		}
    	}
		// 불가능할때 
		if(reservable == null) {
			throw new UnavailableBookingException("선택한 날짜와 영화로 예매 불가능.");
		}
		// 중복 확인
		boolean overlap = 
		reservationRepository.findByAvailableMovie_AvailableMovieIdOrderByStartTimeAsc(availableMovieId)
		.stream().anyMatch(x -> x.overlap(reservation));
		
		if(overlap) {
			throw new AlreadyBookedException("입력한 시간대에 이미 예매가 되어있습니다.");
		}
		
		remain= reservable.getSeats();
		if(remain <=0 )
		{
			throw new UnavailableBookingException("예매할 좌석이 없습니다.");
		}
		reservable.setSeats(remain -1);
		reservationRepository.save(reservation); 
		
		return reservation; 
	}
	public void cancel (Integer reservationId , User requestUser) {
		List <Reservation> reservations = reservationRepository.findAll();
		Reservation reservation = null; 
		Integer remain =0;
		for(int i=0;i<reservations.size();i++) 
		{
			if(reservations.get(i).getReservationId()==reservationId)
				reservation = reservations.get(i);
		}
		
		if(RoleName.ADMIN != requestUser.getRoleName() 
				&& !Objects.equals(reservation.getUser().getUserId(), requestUser.getUserId()))
		{
			throw new IllegalStateException("예매를 취소할 수 없습니다"); 
		}
		remain = reservation.getAvailableMovie().getSeats();
		reservation.getAvailableMovie().setSeats(remain+1);		
		reservationRepository.delete(reservation);
	}

	
	public List<Reservation> findReservations(AvailableMovieId availableMovieId) {
		return reservationRepository.findByAvailableMovie_AvailableMovieIdOrderByStartTimeAsc(availableMovieId);
	}
	
	
}
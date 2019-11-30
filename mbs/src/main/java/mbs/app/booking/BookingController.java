package mbs.app.booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mbs.domain.model.AvailableMovie;
import mbs.domain.model.AvailableMovieId;
import mbs.domain.model.Reservation;
import mbs.domain.model.RoleName;
import mbs.domain.model.User;
import mbs.domain.service.Booking.AlreadyBookedException;
import mbs.domain.service.Booking.BookingService;
import mbs.domain.service.Booking.UnavailableBookingException;
import mbs.domain.service.movie.MovieService;

@Controller
@RequestMapping("/movies/reservations/{date}/{movieId}")
public class BookingController {
    
	@Autowired
	MovieService movieService;
	
	@Autowired
	BookingService bookingService;
	
	@ModelAttribute 
	BookingForm setUpForm() {
		BookingForm form = new BookingForm();
		
		form.setStartTime(LocalTime.of(9, 0));
		form.setEndTime(LocalTime.of(10, 0));
		return form;
	}
	
	private User dummyUser() {
		User user = new User();
		user.setUserId("Lee Taesun");
		user.setFirstName("태선");
		user.setLastName("이");
		user.setRoleName(RoleName.ADMIN);
		user.setPassword("1234");
		return user;
	}
	
	@RequestMapping( method = RequestMethod.GET)
	String reserveForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
		@PathVariable("movieId") Integer movieId , Model model	)
	{
		User user = dummyUser();
		AvailableMovieId availableMovieId = new AvailableMovieId(movieId,date);
		List<Reservation> reservations = bookingService.findReservations(availableMovieId);
		List<LocalTime> timeList =  Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30)).limit(24*2).collect(Collectors.toList());
    	List<AvailableMovie> movies = movieService.findAllMovies(date);
    	AvailableMovie movie = null;
    	for(int i=0;i<movies.size();i++)  
    	{
    		if(movies.get(i).getMovie().getMovieId()==movieId)
    		{
    			movie = movies.get(i);
    		}
    	}
		model.addAttribute("movie", movie );
		model.addAttribute("reservations",reservations);
		model.addAttribute("timeList",timeList);
		model.addAttribute("user", user );
		return "reservation/reserveForm";
	}
	@RequestMapping( method = RequestMethod.POST)
	String reserve (@Validated BookingForm form, BindingResult bindingResult,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			@PathVariable("movieId") Integer movieId , Model model	)
	{
		if(bindingResult.hasErrors()) {
			return reserveForm(date,movieId,model);
		}
		AvailableMovie availableMovie = new AvailableMovie(new AvailableMovieId(movieId, date));
		Reservation reservation = new Reservation();
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setAvailableMovie(availableMovie);
		reservation.setUser(dummyUser());
		try {
			bookingService.reserve(reservation);
		}
		catch(UnavailableBookingException | AlreadyBookedException e ){
			
			model.addAttribute("error",e.getMessage());
			System.out.println("reservation fail!!!!!!");
			return reserveForm(date,movieId,model);
		}
		
		System.out.println("reservation success!!!!!!");
		return "redirect:/movies/reservations/{date}/{movieId} ";
	}
	@RequestMapping( method = RequestMethod.POST ,params = "cancel")
	String cancel(@RequestParam("reservationId") Integer reservationId, 
			@PathVariable("movieId") Integer movieId, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model)
	{
		User user = dummyUser();
		try {
			bookingService.cancel(reservationId, user);
		}
		catch (IllegalStateException e) {
			model.addAttribute("error",e.getMessage());
			System.out.println("error !!!!!!!!!!!!!!!!!");
			return reserveForm(date,movieId,model);
		}
		return "redirect:/movies/reservations/{date}/{movieId}";
	}

	
}
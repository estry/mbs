package mbs.app.movie;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mbs.domain.model.Movie;
import mbs.domain.model.AvailableMovie;
import mbs.domain.service.movie.MovieService;


@Controller
@RequestMapping("movies")
public class MoviesController {
	
    @Autowired
    MovieService movieService;
    /*
    @RequestMapping(value = "/reservations/{date}/{movieId}", method = RequestMethod.GET)
    String movieInfo(@DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
    		@PathVariable("movieId")int movieId, Model model){
    	
    	
    	List<AvailableMovie> movies = movieService.findAllMovies(date);
    	AvailableMovie movie = null;
    	for(int i=0;i<movies.size();i++)   // 효율성 측면에서 별로임 다른 방법도 찾아볼것. 
    	{
    		if(movies.get(i).getMovie().getMovieId()==movieId)
    		{
    			movie = movies.get(i);
    		}
    	}
    	//List<Movie> movies = movieService.findAllMovies();
    	//Movie movie = movies.get(movieId);
		model.addAttribute("movie", movie);
    	return "movie/movieInfo";
    	
    	
    }*/

    @RequestMapping(value = "{date}", method = RequestMethod.GET)
    String listMovies(@DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
            Model model) {
        List<AvailableMovie> movies = movieService.findReservableMovies(date);
        model.addAttribute("movies", movies);
        return "movie/listMovies";
    }

    @RequestMapping(method = RequestMethod.GET)
    String listMovies(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("date", today);
        return listMovies(today, model);
    }
    
    
}
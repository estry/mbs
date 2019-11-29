package mbs.domain.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class AvailableMovie implements Serializable {
	
    private static final long serialVersionUID = 957780691869519467L;

    @EmbeddedId
    private AvailableMovieId availableMovieId;
    
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    @MapsId("movieId")
    private Movie movie;
    
    private Integer seats;

    public AvailableMovie(AvailableMovieId availableMovieId) {
        this.availableMovieId = availableMovieId;
    }

    public AvailableMovie() {
    }

    public AvailableMovieId getReservableMovieId() {
        return availableMovieId;
    }

    public void setReservableMovieId(AvailableMovieId availableMovieId) {
        this.availableMovieId = availableMovieId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}
}
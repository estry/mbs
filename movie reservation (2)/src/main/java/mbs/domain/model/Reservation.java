package mbs.domain.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;


import javax.persistence.*;

@Entity
public class Reservation implements Serializable {
	
    private static final long serialVersionUID = 1016314399260689548L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  
    private Integer reservationId;
    
    private LocalTime startTime;
    
    private LocalTime endTime;
    
    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "available_date"),
            @JoinColumn(name = "movie_id") })
    private AvailableMovie availableMovie;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public AvailableMovie getAvailableMovie() {
        return availableMovie;
    }

    public void setAvailableMovie(AvailableMovie availableMovie) {
        this.availableMovie = availableMovie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(availableMovie.getReservableMovieId(),
                target.availableMovie.getReservableMovieId())) {
            return false;
        }
        if (startTime.equals(target.startTime)
                && endTime.equals(target.endTime)) {
            return true;
        }
        return target.endTime.isAfter(startTime)
                && endTime.isAfter(target.startTime);
    }
}
package mbs.app.booking;

import java.io.Serializable;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class BookingForm implements Serializable{
	private static final long serialVersionUID = - 1417530447325117037L;
	
	@NotNull(message = "필수 입력 항목입니다.")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@NotNull(message = "필수 입력 항목입니다.")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime; 
	
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
}

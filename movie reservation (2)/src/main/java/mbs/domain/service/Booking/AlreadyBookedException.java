package mbs.domain.service.Booking;

public class AlreadyBookedException extends RuntimeException{
	private static final long serialVersionUID = -4745523761146600645L;
	public AlreadyBookedException(String s) {
		super(s);
	}

}
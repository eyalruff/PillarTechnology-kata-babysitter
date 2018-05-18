package pillarkata.rest.project;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BabySitterBO {

	private Date checkInDate;
	private Date checkOutDate;
	private Date minDate;
	private Date maxDate;
	private Date eightPM;
	private Date midNight;

	public BabySitterBO(long checkIn, long checkOut, long min, long max) {
		this.checkInDate = new Date(checkIn);
		this.checkOutDate = new Date(checkOut);
		roundTimesTofullHours();
		this.minDate = new Date(min);
		this.maxDate = new Date(max);

		Calendar sleepCalendar = Calendar.getInstance();
		sleepCalendar.setTime(minDate);
		sleepCalendar.set(Calendar.HOUR_OF_DAY, 20);
		this.eightPM = sleepCalendar.getTime();

		Calendar nightCalendar = new GregorianCalendar();
		nightCalendar.setTime(maxDate);
		nightCalendar.set(Calendar.HOUR_OF_DAY, 0);
		this.midNight = nightCalendar.getTime();
	}

	// run times down/ up to full hours
	public void roundTimesTofullHours() {
		Calendar checkInCal = GregorianCalendar.getInstance();
		checkInCal.setTime(checkInDate);
		int checkInMin = checkInCal.get(Calendar.MINUTE);
		// if the minutes are over 0 and below 30 round the hour down to get
		// full hour
		if (0 < checkInMin && checkInMin < 30) {
			checkInCal.set(Calendar.MINUTE, 00);
		} else if (checkInMin >= 30 && checkInMin <= 59) {
			// if the minutes are between 30 and 59 round the hour up to get
			// full hour
			checkInCal.add(Calendar.HOUR_OF_DAY, 1);
			checkInCal.set(Calendar.MINUTE, 00);
		}
		this.checkInDate = checkInCal.getTime();

		Calendar checkOutCal = GregorianCalendar.getInstance();
		checkOutCal.setTime(checkOutDate);
		int checkOutMin = checkOutCal.get(Calendar.MINUTE);
		// if the minutes are over 0 and below 30 round the hour down to get
		// full hour
		if (0 < checkOutMin && checkOutMin < 30) {
			checkOutCal.set(Calendar.MINUTE, 00);
		} else if (checkOutMin >= 30 && checkOutMin <= 59) {
			// if the minutes are between 30 and 59 round the hour up to get
			// full hour
			checkOutCal.add(Calendar.HOUR_OF_DAY, 1);
			checkOutCal.set(Calendar.MINUTE, 00);
		}
		this.checkOutDate = checkOutCal.getTime();

		if (checkInDate.equals(checkOutDate)) {
			// edge case in which both are 04:00 AM, set check in date to 3AM
			if (checkInCal.get(Calendar.HOUR_OF_DAY) == 4) {
				checkInCal.set(Calendar.HOUR_OF_DAY, 3);
				checkInDate = checkInCal.getTime();
			} else { // if both equal add one hour to checkout time so the
						// babysitter will get paid
				checkOutCal.add(Calendar.HOUR_OF_DAY, 1);
				this.checkOutDate = checkOutCal.getTime();
			}
		}
	}
	
	public Date getCheckInDate() {
		return checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}



}

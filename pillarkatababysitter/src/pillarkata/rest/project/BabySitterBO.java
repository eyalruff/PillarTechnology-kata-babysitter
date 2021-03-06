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
	
	//calculate payments between 17 - 20 - $12/hour from start-time to bedtime
	public Integer calculateDayTimePayment() {
		if (checkInDate.before(eightPM)) {
			if (checkOutDate.after(eightPM)) {
				long diff = ((eightPM.getTime() - checkInDate.getTime()));
				int totalHours = (int) (diff / (1000 * 60 * 60));
				return totalHours * 12;
			} else if (checkOutDate.equals(eightPM) || checkOutDate.before(eightPM)) {
				long diff = ((checkOutDate.getTime() - checkInDate.getTime()));
				int totalHours = (int) (diff / (1000 * 60 * 60));
				return totalHours * 12;
			}
		}
		return 0;
	}
	
	//calculate payments between 20:00 (bedtime time) - 00:00 - $8/hour from bedtime to midnight
	public Integer calculateBedTimePayment() {
		if (checkOutDate.after(eightPM) && (checkOutDate.before(midNight) || checkOutDate.equals(midNight))) {
			if (checkInDate.before(eightPM)) {
				long diff = ((checkOutDate.getTime() - eightPM.getTime()));
				int totalHours = (int) (diff / (1000 * 60 * 60));
				return totalHours * 8;
			} else if (checkInDate.equals(eightPM) || checkInDate.after(eightPM)) {
				long diff = ((checkOutDate.getTime() - checkInDate.getTime()));
				int totalHours = (int) (diff / (1000 * 60 * 60));
				return totalHours * 8;
			}
		}

		if (checkOutDate.equals(midNight) || checkOutDate.after(midNight)) {
			if (checkInDate.before(eightPM)) {
				return 32;
			} else if (checkInDate.equals(midNight) || checkInDate.after(midNight)) {
				long diff = ((checkOutDate.getTime() - checkInDate.getTime()));
				int totalHours = (int) (diff / (1000 * 60 * 60));
				return totalHours * 8;
			} else if (checkInDate.equals(eightPM) || (checkInDate.after(eightPM) && checkInDate.before(midNight))) {
				long diff = ((midNight.getTime() - checkInDate.getTime()));
				int totalHours = (int) (diff / (1000 * 60 * 60));
				return totalHours * 8;
			}
		}
		return 0;
	}
	
	//calculate payments between 00:00 - 00:04AM - $16/hour from midnight to end of job 
	public Integer calculateNightTimePayment() {
		if (checkOutDate.after(midNight) && (checkInDate.equals(midNight) || checkInDate.after(midNight))) {
			long diff = ((checkOutDate.getTime() - checkInDate.getTime()));
			int totalHours = (int) (diff / (1000 * 60 * 60));
			return totalHours * 16;
		} else if (checkOutDate.after(midNight) && checkInDate.before(midNight)) {
			long diff = ((checkOutDate.getTime() - midNight.getTime()));
			int totalHours = (int) (diff / (1000 * 60 * 60));
			return totalHours * 16;
		}
		return 0;
	}
	
	public Integer calculateTotalSalary() {
		Integer salary = 0;
		salary = calculateDayTimePayment();
		salary += calculateBedTimePayment();
		salary += calculateNightTimePayment();
		return salary;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}



}

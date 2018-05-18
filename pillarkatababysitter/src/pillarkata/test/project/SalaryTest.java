package pillarkata.test.project;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import pillarkata.rest.project.BabySitterBO;

public class SalaryTest {

	BabySitterBO babySitterBOTest1;
	BabySitterBO babySitterBOTest2;
	Calendar checkInCalTest1;
	Calendar checkOutCalTest1;
	Calendar minCalTest1;
	Calendar maxCalTest1;
	Calendar checkInCalTest2;
	Calendar checkOutCalTest2;
	Calendar minCalTest2;
	Calendar maxCalTest2;

	/*set up 2 different dates for testing
	1 -  check in: 2018-05-08 Time: 19:00, check out: 2018-05-09 Time: 04:00
	2 -  check in: 2018-05-08 Time: 17:18, check out: 2018-05-08 Time: 23:20 
	*/ 
	@Before
	public void setUp() {
		// create test date & times using calendar to use as babySitterBOTest1 constructor params
		checkInCalTest1 = new GregorianCalendar();
		checkInCalTest1.set(2018, 4, 7, 19, 0, 0); // check in 2018-05-08T19:00
		long checkInCalMillis = checkInCalTest1.getTimeInMillis();
		checkOutCalTest1 = new GregorianCalendar();
		checkOutCalTest1.set(2018, 4, 8, 4, 0, 0); // check out 2018-05-09T04:00
		long checkOutCalMillis = checkOutCalTest1.getTimeInMillis();
		minCalTest1 = new GregorianCalendar();
		minCalTest1.set(2018, 4, 7, 17, 0, 0); // min 2018-05-08T17:00
		long minCalMillis = minCalTest1.getTimeInMillis();
		maxCalTest1 = new GregorianCalendar();
		maxCalTest1.set(2018, 4, 8, 4, 0, 0); // max 2018-05-09T04:00
		long maxCalMillis = maxCalTest1.getTimeInMillis();

		// create another test date & times using calendar to use as babySitterBOTest1 constructor params
		checkInCalTest2 = new GregorianCalendar();
		checkInCalTest2.set(2018, 4, 7, 17, 18, 0); // check in 2018-05-08T17:18
		long checkInCal1Millis = checkInCalTest2.getTimeInMillis();
		checkOutCalTest2 = new GregorianCalendar();
		checkOutCalTest2.set(2018, 4, 7, 23, 20, 0); // check out 2018-05-08T23:20
		long checkOutCal1Millis = checkOutCalTest2.getTimeInMillis();
		minCalTest2 = new GregorianCalendar();
		minCalTest2.set(2018, 4, 7, 17, 0, 0); // min 2018-05-08T19:00
		long minCal1Millis = minCalTest2.getTimeInMillis();
		maxCalTest2 = new GregorianCalendar();
		maxCalTest2.set(2018, 4, 8, 4, 0, 0); // max 2018-05-08T19:00
		long maxCal1Millis = maxCalTest2.getTimeInMillis();

		babySitterBOTest1 = new BabySitterBO(checkInCalMillis, checkOutCalMillis, minCalMillis, maxCalMillis);
		babySitterBOTest2 = new BabySitterBO(checkInCal1Millis, checkOutCal1Millis, minCal1Millis, maxCal1Millis);
	}
	
	// testing that the hours are being rounded to full hours (no fractional hours) according to BabySitterBO
	@Test
	public void roundTofullHoursTest() {
		checkInCalTest1 = new GregorianCalendar();
		checkInCalTest1.set(2018, 4, 7, 19, 0, 0); // check in 2018-05-08T19:00
		Date checkInCalDtTest1 = checkInCalTest1.getTime();
		checkOutCalTest1 = new GregorianCalendar();
		checkOutCalTest1.set(2018, 4, 8, 4, 0, 0); // check out 2018-05-09T04:00
		Date checkOutDtTest1 = checkOutCalTest1.getTime();

		assertEquals(checkInCalDtTest1, babySitterBOTest1.getCheckInDate());
		assertEquals(checkOutDtTest1, babySitterBOTest1.getCheckOutDate());
		
		checkInCalTest2 = new GregorianCalendar();
		checkInCalTest2.set(2018, 4, 7, 17, 0, 0); // check in 2018-05-08T17:00
		Date checkInCalDtTest2 = checkInCalTest2.getTime();
		checkOutCalTest2 = new GregorianCalendar();
		checkOutCalTest2.set(2018, 4, 7, 23, 0, 0); // check out 2018-05-08T23:00
		Date checkOutDtTest2 = checkOutCalTest2.getTime();

		assertEquals(checkInCalDtTest2, babySitterBOTest2.getCheckInDate());
		assertEquals(checkOutDtTest2, babySitterBOTest2.getCheckOutDate());
	}
}

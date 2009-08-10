package is.us.util;

import static org.junit.Assert.*;

import java.text.*;
import java.util.Date;

import org.junit.Test;

/**
 * Tests for the USHolidays class
 * 
 * @author Atli Páll Hafsteinsson <atlip@us.is>
 * @reviewedby Logi Helgu at Jun 24, 2009 (JIRA issue INN-622)
 */

public class TestUSHolidays {

	private static SimpleDateFormat df = new SimpleDateFormat( "yyyyMMdd HHmmssS" );

	@Test
	public void isHoliday() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2000 );
			Date date = df.parse( "20000101 0000000" );
			assertEquals( true, h.isHoliday( date ) );

			date = df.parse( "20000102 0000000" );
			assertEquals( false, h.isHoliday( date ) );

			h = new USHolidays( 2014 );
			date = df.parse( "20140420 0000000" );
			assertEquals( true, h.isHoliday( date ) );
		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void newYearsDay() {
		try {
			USHolidays h = new USHolidays( 2000 );
			Date expected = df.parse( "20000101 0000000" );
			Date actual = h.newYearsDay();
			assertEquals( expected, actual );
		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void easterDay() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090412 0000000" );
			Date actual = h.easterDay();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20130331 0000000" );
			actual = h.easterDay();
			assertEquals( expected, actual );
		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void easterMonday() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090413 0000000" );
			Date actual = h.easterMonday();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20130401 0000000" );
			actual = h.easterMonday();
			assertEquals( expected, actual );
		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void maundyThursday() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090409 0000000" );
			Date actual = h.maundyThursday();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20130328 0000000" );
			actual = h.maundyThursday();
			assertEquals( expected, actual );
		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void goodFriday() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090410 0000000" );
			Date actual = h.goodFriday();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20130329 0000000" );
			actual = h.goodFriday();
			assertEquals( expected, actual );
		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void firstDayOfSummer() {

		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090423 0000000" );
			Date actual = h.firstDayOfSummer();
			assertEquals( expected, actual );

			h = new USHolidays( 2012 );
			expected = df.parse( "20120419 0000000" );
			actual = h.firstDayOfSummer();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void firstOfMay() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090501 0000000" );
			Date actual = h.firstOfMay();
			assertEquals( expected, actual );

			h = new USHolidays( 2012 );
			expected = df.parse( "20120501 0000000" );
			actual = h.firstOfMay();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void ascensionDay() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090521 0000000" );
			Date actual = h.ascensionDay();
			assertEquals( expected, actual );

			h = new USHolidays( 2004 );
			expected = df.parse( "20040520 0000000" );
			actual = h.ascensionDay();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void whiteMonday() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090601 0000000" );
			Date actual = h.whiteMonday();
			assertEquals( expected, actual );

			h = new USHolidays( 2012 );
			expected = df.parse( "20120528 0000000" );
			actual = h.whiteMonday();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void seventeenthOfJune() {
		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090617 0000000" );
			Date actual = h.seventeenthOfJune();
			assertEquals( expected, actual );

			h = new USHolidays( 2012 );
			expected = df.parse( "20120617 0000000" );
			actual = h.seventeenthOfJune();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void trademensDay() {

		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20090803 0000000" );
			Date actual = h.trademensDay();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20130805 0000000" );
			actual = h.trademensDay();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void christmasEve() {

		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20091224 0000000" );
			Date actual = h.christmasEve();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20131224 0000000" );
			actual = h.christmasEve();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void christmasDay() {

		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20091225 0000000" );
			Date actual = h.christmasDay();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20131225 0000000" );
			actual = h.christmasDay();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void boxingDay() {

		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20091226 0000000" );
			Date actual = h.boxingDay();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20131226 0000000" );
			actual = h.boxingDay();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}

	@Test
	public void newYearsEve() {

		try {
			USHolidays h = null;

			h = new USHolidays( 2009 );
			Date expected = df.parse( "20091231 0000000" );
			Date actual = h.newYearsEve();
			assertEquals( expected, actual );

			h = new USHolidays( 2013 );
			expected = df.parse( "20131231 0000000" );
			actual = h.newYearsEve();
			assertEquals( expected, actual );

		}
		catch( ParseException e ) {
			fail();
		}
	}
}
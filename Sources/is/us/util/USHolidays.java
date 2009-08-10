package is.us.util;

import java.util.*;

/**
 * This class calculates all the holidays in a given year
 * and allow us to see if a certain date is a holiday.
 *
 * Each holiday in this class indicates the start of the day.
 *
 * @author Hugi Þórðarson
 * @reviewedby Logi Helgu at Jun 24, 2009( see JIRA issue INN_652 )
 */

public class USHolidays {

	/**
	 * The year to get holidays for
	 */
	private int _year;

	public USHolidays( int newYear ) {
		setYear( newYear );
	}

	public int year() {
		return _year;
	}

	private void setYear( int newYear ) {
		_year = newYear;
	}

	/**
	 * Returns an array of all Icelandic holiday dates.<br />
	 * Note that some of these holidays do not last the whole day.<br />
	 * To get the days that last a whole day use fullHoldays.<br />
	 * To get the days that last part of the day use partialHolidays.
	 */
	public List<Date> allHolidays() {
		List<Date> a = new ArrayList<Date>();

		a.add( newYearsDay() );
		a.add( maundyThursday() );
		a.add( goodFriday() );
		a.add( easterDay() );
		a.add( easterMonday() );
		a.add( firstDayOfSummer() );
		a.add( firstOfMay() );
		a.add( ascensionDay() );
		a.add( whiteMonday() );
		a.add( seventeenthOfJune() );
		a.add( trademensDay() );
		a.add( christmasEve() );
		a.add( christmasDay() );
		a.add( boxingDay() );
		a.add( newYearsEve() );

		return a;
	}

	/**
	* Returns an array of Icelandic holiday dates that last the whole day.
	*/
	public List<Date> fullHolidays() {
		List<Date> a = new ArrayList<Date>();

		a.add( newYearsDay() );
		a.add( maundyThursday() );
		a.add( goodFriday() );
		a.add( easterDay() );
		a.add( easterMonday() );
		a.add( firstDayOfSummer() );
		a.add( firstOfMay() );
		a.add( ascensionDay() );
		a.add( whiteMonday() );
		a.add( seventeenthOfJune() );
		a.add( trademensDay() );
		a.add( christmasDay() );
		a.add( boxingDay() );

		return a;
	}

	/**
	* Returns an array of holidays that last only part of the day.
	*/
	public List<Date> partialHolidays() {
		List<Date> a = new ArrayList<Date>();

		a.add( christmasEve() );
		a.add( newYearsEve() );

		return a;
	}

	/**
	 * Returns true if the date is a holiday
	 */
	public boolean isHoliday( Date date ) {
		USDateUtilities.normalizeToMidnight( date );
		List<Date> holidays = allHolidays();
		return holidays.contains( date );
	}

	/**
	 * New Year's Day (Icelandic: Nýársdagur)
	 */
	public Date newYearsDay() {
		GregorianCalendar c = USDateUtilities.cal();
		c.set( year(), GregorianCalendar.JANUARY, 1, 0, 0, 0 );
		return c.getTime();
	}

	/**
	 * Maundy Thursday (Icelandic: Skírdagur)
	 * Thursday before Easter Sunday.
	 */
	public Date maundyThursday() {
		return USDateUtilities.addDays( easterDay(), -3 );
	}

	/**
	 * Good Friday (Icelandic: Föstudagurinn langi)
	 * Friday before Easter Sunday.
	 */
	public Date goodFriday() {
		return USDateUtilities.addDays( easterDay(), -2 );
	}

	/**
	 * Easter Day (Icelandic: Páskadagur)
	 * 
	 * Borrowed from: http://www.smart.net/~mmontes/nature1876.html
	 * 
	 * Holds for any year in the Gregorian Calendar, which (of course) means years including and after 1583.
	 */
	public Date easterDay() {
		int a = year() % 19;
		int b = year() / 100;
		int c = year() % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int month = ((h + l - 7 * m + 114) / 31) - 1;
		int p = (h + l - 7 * m + 114) % 31;
		int day = p + 1;

		GregorianCalendar cal = USDateUtilities.cal();
		cal.set( year(), month, day, 0, 0, 0 );

		return cal.getTime();
	}

	/**
	 * Easter Monday (Icelandic: Annar í páskum)
	 * Monday following Easter Sunday
	 */
	public Date easterMonday() {
		return USDateUtilities.addDays( easterDay(), 1 );
	}

	/**
	 * First day of summer (Icelandic: Sumardagurinn fyrsti)
	 * Thursday during the period 19 to 25 April
	 */
	public Date firstDayOfSummer() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.APRIL, 19, 0, 0, 0 );
		calendar = USDateUtilities.setToNextDayOfWeek( calendar, GregorianCalendar.THURSDAY );
		return calendar.getTime();
	}

	/**
	 * Labour day (Icelandic: Verkalýðdagurinn (1. maí))
	 */
	public Date firstOfMay() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.MAY, 1 );
		return calendar.getTime();
	}

	/**
	 * Ascension day (Icelandic: Uppstigningardagur).
	 * Holy Thursday, six weeks after Maundy Thursday.
	 */
	public Date ascensionDay() {
		return USDateUtilities.addDays( maundyThursday(), 42 );
	}

	/**
	 * White Monday (Icelandic: Annar í hvítasunnu).
	 * Monday following White Sunday, seven weeks after Easter.
	 */
	public Date whiteMonday() {
		return USDateUtilities.addDays( easterMonday(), 49 );
	}

	/**
	 * Seventeenth of June (Icelandic: Þjóðhátíðardagurinn( 17. júní)).
	 * Icelandic national holiday.
	 */
	public Date seventeenthOfJune() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.JUNE, 17 );
		return calendar.getTime();
	}

	/**
	 * Trademen's Day (Icelandic: Frídagur verslunarmanna).
	 * First Monday in August.
	 */
	public Date trademensDay() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.AUGUST, 1 );
		calendar = USDateUtilities.setToNextDayOfWeek( calendar, GregorianCalendar.MONDAY );
		return calendar.getTime();
	}

	/**
	 * Christmas Eve (Icelandic: Aðfangadagur).
	 * Afternoon only.
	 */
	public Date christmasEve() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.DECEMBER, 24 );
		return calendar.getTime();
	}

	/**
	 * Christmas Day (Icelandic: Jóladagur).
	 */
	public Date christmasDay() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.DECEMBER, 25 );
		return calendar.getTime();
	}

	/**
	 * Boxing Day (Icelandic: Annar í jólum).
	 */
	public Date boxingDay() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.DECEMBER, 26 );
		return calendar.getTime();
	}

	/**
	 * New Year's Eve (Icelandic: Gamlársdagur).
	 * Afternoon only.
	 */
	public Date newYearsEve() {
		GregorianCalendar calendar = USDateUtilities.cal();
		calendar.set( year(), GregorianCalendar.DECEMBER, 31 );
		return calendar.getTime();
	}

}
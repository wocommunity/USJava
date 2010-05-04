package is.us.util;

import java.util.*;

/**
 * Calculates all the holidays in a given year, according to current Icelandic laws.
 * and allow us to check if a certain date is a holiday.
 * 
 * Each holiday in this class indicates the start of the day.
 * 
 * @issue INN-652
 * @author Hugi Þórðarson
 * @reviewedby Logi Helgu, Jun 24, 2009 
 */

public class USHolidays {

	/**
	 * The year to get holidays for
	 */
	private int _year;

	/**
	 * Constructs a new holidays calculator.
	 * 
	 * @param newYear The year to calculate holidays for.
	 */
	public USHolidays( int newYear ) {
		setYear( newYear );
	}

	/**
	 * The year to calculate holidays for. 
	 */
	public int year() {
		return _year;
	}

	/**
	 * Set the year to calculate holidays for.
	 * 
	 * @param newYear The year to calculate holidays for.
	 */
	private void setYear( int newYear ) {
		_year = newYear;
	}

	/**
	 * All Icelandic holiday dates, partial and complete.
	 * Note that some of these holidays do not last the whole day.
	 * To get the days that last a whole day use fullHoldays.
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
	* Icelandic full-day holidays.
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
	* Partial Icelandic holidays. (holiday after noon).
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
	public static boolean isHoliday( Date date ) {
		date = USDateUtilities.normalizeToMidnight( date );
		int year = USDateUtilities.year( date );
		return new USHolidays( year ).allHolidays().contains( date );
	}

	/**
	 * Returns true if the date is a full holiday
	 */
	public static boolean isFullHoliday( Date date ) {
		date = USDateUtilities.normalizeToMidnight( date );
		int year = USDateUtilities.year( date );
		return new USHolidays( year ).fullHolidays().contains( date );
	}

	/**
	 * Returns true if the date is a full holiday
	 */
	public static boolean isPartialHoliday( Date date ) {
		date = USDateUtilities.normalizeToMidnight( date );
		int year = USDateUtilities.year( date );
		return new USHolidays( year ).partialHolidays().contains( date );
	}

	/**
	 * New Year's Day (Icelandic: Nýársdagur)
	 */
	public Date newYearsDay() {
		return USDateUtilities.date( year(), 1, 1 );
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
		int month = ((h + l - 7 * m + 114) / 31);
		int p = (h + l - 7 * m + 114) % 31;
		int day = p + 1;

		return USDateUtilities.date( year(), month, day );
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
		return USDateUtilities.date( year(), 5, 1 );
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
		return USDateUtilities.date( year(), 6, 17 );
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
		return USDateUtilities.date( year(), 12, 24 );
	}

	/**
	 * Christmas Day (Icelandic: Jóladagur).
	 */
	public Date christmasDay() {
		return USDateUtilities.date( year(), 12, 25 );
	}

	/**
	 * Boxing Day (Icelandic: Annar í jólum).
	 */
	public Date boxingDay() {
		return USDateUtilities.date( year(), 12, 26 );
	}

	/**
	 * New Year's Eve (Icelandic: Gamlársdagur).
	 * Afternoon only.
	 */
	public Date newYearsEve() {
		return USDateUtilities.date( year(), 12, 31 );
	}
}
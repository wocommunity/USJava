package is.us.util;

/**
 * For measuring the time operations take.
 * 
 * @author Hugi Þórðarson
 * 
 * Example:
 * 
 * USStopwatch watch = new USStopWatch();
 * doStuff();
 * System.out.println( watch.elapsed() );
 * doMorestuff();
 * System.out.println( watch.elapsed() );
 */

public class USStopWatch {

	/**
	 * The time at which the watch was started.
	 */
	private long startTime;

	/**
	 * The time at which the watch was stopped.
	 */
	private long endTime;

	/**
	 * Constructs a new stopwatch and starts it.
	 */
	public USStopWatch() {
		start();
	}

	/**
	 * Starts the watch (automatically invoked when the object is constructed)
	 */
	public void start() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * Starts the watch (automatically invoked when the object is constructed)
	 */
	public void end() {
		endTime = System.currentTimeMillis();
	}

	/**
	 * @return time elapsed since the watch was started.
	 */
	public long elapsed() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * @return time elapsed since the watch was started.
	 */
	public long durationInMilliseconds() {
		return endTime - startTime;
	}
}
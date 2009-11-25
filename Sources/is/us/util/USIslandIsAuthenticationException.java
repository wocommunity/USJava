package is.us.util;

/**
 * Exception thrown when there is an error authenticating
 * using the island.is authentication service
 * 
 * @author Atli Páll Hafsteinsson <atlip@us.is>
 *
 */
public class USIslandIsAuthenticationException extends RuntimeException {

	public USIslandIsAuthenticationException( Throwable cause ) {
		super( cause );
	}

	public USIslandIsAuthenticationException( String message ) {
		super( message );
	}

	public USIslandIsAuthenticationException( String message, Throwable cause ) {
		super( message, cause );
	}
}

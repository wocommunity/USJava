package is.us.template;

import java.util.*;

/**
 * Abstract class for handling string replacement (templating).
 * 
 * To use this class, just
 * 
 * 1. Create a new instance.
 * 2. Set it's string template.
 * 3. Set it's variables.
 * 4. Call "parse()".
 * 
 * Example, using USTemplateSimple
 * 
 * <code>
 * 		USTemplate template = new USTemplateSimple();
 * 		template.put( "nafn", "Hugi" );
 * 		template.setTemplateString( "Halló ${nafn}" );
 * 		String result = template.parse();
 * </code>
 * 
 * Will generate the result "Halló Hugi".
 * 
 * WARNING! Template syntax is variable between implementations! See documentation for each subclass for information on each class' template syntax.
 * 
 * @author Hugi Þórðarson
 * @issue INT-128
 * @reviewedBy Bjarni Sævarsson
 */

public abstract class USTemplate {

	private Map<String, Object> _variables;
	private String _templateString;

	/**
	 * Constructs a new template using the given string as the template
	 */
	public USTemplate() {
		_variables = new HashMap<String, Object>();
	}

	/**
	 * Set a variable in the variable map.
	 * 
	 * @param name the variables name
	 * @param value the variables value
	 */
	public void put( String name, Object value ) {
		_variables.put( name, value );
	}

	/**
	 * Set multiple variables in the variable map.
	 * 
	 * @param vars the variables
	 */
	public void putAll( Map<String, Object> vars ) {
		_variables.putAll( vars );
	}

	/**
	 * Replaces all variables in the template with the new map.
	 * 
	 * @param vars the variables
	 */
	public void setVariables( Map<String, Object> vars ) {
		_variables = vars;
	}

	/**
	 * @return All variables in the variable map.
	 */
	public Map<String, Object> variables() {
		return _variables;
	}

	/**
	 * Clears all variables.
	 */
	public void clearVariables() {
		_variables = new HashMap<String, Object>();
	}

	/**
	 * Perform the "parse()" method, returning the rendered template.
	 */
	@Override
	public String toString() {
		return parse();
	}

	/**
	 * @param template the string template
	 */
	public void setTemplateString( String template ) {
		_templateString = template;
	}

	/**
	 * The template string used as basis for the rendering.
	 */
	public String templateString() {
		return _templateString;
	}

	/**
	 * Method to be overridden by subclasses.
	 * Performs rendering of the template.
	 */
	public abstract String parse();
}
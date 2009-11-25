package is.us.template;

import is.us.util.USStringUtilities;

import java.util.Set;

/**
 * A very simple template system
 * The templates used are strings with variables of the form ${variableName}
 * 
 * Example use:
 * 
 * <code>
 * 		String templateString = "The quick ${color} fox jumps over a lazy ${lazyAnimal}";
 * 		USTemplate template = new USTemplateSimple();
 * 		template.setTemplateString( templateString );
 * 		template.put( "color", "brown" );
 * 		template.put( "lazyAnimal", "dog" );
 * 		String result = template.parse();
 * </code>
 * 
 * Will return: The quick brown fox jumps over a lazy dog
 * 
 * @author Atli Páll Hafsteinsson
 * @reviewedBy Bjarni Sævarsson
 */

public class USTemplateSimple extends USTemplate {

	/**
	 * Replaces all occurancies of template variables with the desired values.
	 * If any of the values is null the template variable is removed.
	 * 
	 * @return the template with variables replaced with their values
	 */
	public String parse() {
		String result = templateString();
		Set<String> keys = variables().keySet();

		for( String key : keys ) {
			Object value = variables().get( key );

			if( value != null ) {
				result = USStringUtilities.replace( result, "${" + key + "}", value.toString() );
			}
			else {
				result = USStringUtilities.replace( result, "${" + key + "}", "" );
			}
		}

		return result;
	}
}
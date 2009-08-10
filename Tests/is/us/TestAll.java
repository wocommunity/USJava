package is.us;

import is.us.formatters.TestUSPersidnoFormatter;
import is.us.template.TestUSTemplateSimple;
import is.us.util.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for USUtilities. 
 * 
 * @author Hugi Þórðarson
 */

@RunWith( Suite.class )
@SuiteClasses( { TestUSDataUtilities.class, TestUSDateUtilities.class, TestUSHolidays.class, TestUSImageUtilities.class, TestUSIPRange.class, TestUSNumberUtilities.class, TestUSPersidnoFormatter.class, TestUSPersidnoUtilities.class, TestUSPhoneUtilities.class, TestUSRoadTaxCalculator.class, TestUSStringUtilities.class, TestUSTemplateSimple.class } )
public class TestAll {}
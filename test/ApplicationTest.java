import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.assertEquals;
import controllers.Application;
import controllers.Application.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 
 * Simple (JUnit) tests that can call all parts of a play app. If you are
 * interested in mocking a whole application, see the wiki for more details.
 * 
 */
@RunWith(Parameterized.class)
public class ApplicationTest {

	private String pwd;
	private boolean expectedResult;

	public ApplicationTest(String pwd, boolean expectedResult) {
		this.pwd = pwd;
		this.expectedResult = expectedResult;
	}

	@Parameters
	public static Collection<Object[]> generateData() {

		return Arrays.asList(new Object[][] { 
				{ "abc123", true },
				{ "j12b3", true }, 
				{ "defgh9ijkmlno", false },
				{ "abcdefg", false }, 
				{ "1234567", false },
				{ "abc123abc123", false },
				{ "abc12dabc12", true },
				{ "123aa", false },
				{ "a123b11c", false },
				{ "y1a1az", false },
				{ "", false },
				{ "abc_123", false },
				{ "abc 123", false },
		});
	}
	
	@Test
	public void myTests() {
		boolean actualResult = Application.isValidPwd(this.pwd);
		assertEquals(actualResult, this.expectedResult);
	}

}

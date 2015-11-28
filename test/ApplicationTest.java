import controllers.Application;

import org.junit.*;

import play.mvc.*;
import play.test.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends WithApplication {

	@Test
	public void testAuthentication() {
		Result result = new Application().authenticate();

	  String token = result.header("x-access-token");
	  Assert.assertNotNull("Access token is null!!", token);
	}


}

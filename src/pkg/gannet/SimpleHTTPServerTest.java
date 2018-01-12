package pkg.gannet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SimpleHTTPServerTest {

	@Test
	public void checkProcessParametersSetFinalString() throws IOException {
		SimpleHTTPServer server = new SimpleHTTPServer();
		Map<String,String> queryParams = new HashMap();
		queryParams.put("uppercase", "true");
		queryParams.put("reverse", "true");
		String inputString = "hello";
		server.processParameters(queryParams,inputString);
		
		Assert.assertEquals("Hello\nConverting hello to Uppercase: HELLO\nReversing hello :olleh\n",server.getFinalString());
	}

	@Test
	public void checkParseQueryStringReturnCorrectMap(){
		SimpleHTTPServer server = new SimpleHTTPServer();
		Assert.assertEquals("{uppercase=true, reverse=false}",server.parseQueryString("uppercase=true&reverse=false").toString());
	}
}

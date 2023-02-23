package day3;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class CookiesTest {
	@Test(priority=1)
	void testCookies() 
	{
		given()
		
		.when()
			.get("https://google.com/")
				
		.then()
			.cookie("AEC", "ARSKqsLMnuACK6TxHTiid74_R6E-srlRGbTn0KjtAqrhlSQRtHqu3EwQsyw") // Should fail( Cookie should be generated newly every request
			
			.log().all();
	}
	@Test(priority=2)
	void getCookiesInfo() 
	{
		Response res=given()
		
		.when()
			.get("https://google.com/");
		//Get single cookie info
		String cookie_value = res.getCookie("AEC");
		System.out.println("The value of cookie is====>" + cookie_value);
		//Get all cookie info
		Map<String, String> cookies_values = res.getCookies();
		//System.out.println(cookies_values.keySet());
		for(String k:cookies_values.keySet()) {
			String cookie_values = res.getCookie(k);
			System.out.println(k+"              " + cookie_values);
		}
	}



	
	
	
}

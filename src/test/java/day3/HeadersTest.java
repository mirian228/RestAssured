package day3;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;


public class HeadersTest {
	
	
	@Test(priority=1)
	void testHeaders() 
	{
		given()
		
		.when()
			.get("https://google.com/")
				
		.then()
			.header("Content-Type", "text/html; charset=ISO-8859-1")
			.and() // not necessary
			.header("Content-Encoding", "gzip")
			.and() // not necessary
			.header("Server", "gws")
			.log().all();
	}
	
	@Test(priority=2)
	void getAllHeaders() 
	{
	Response res=given()
		
		.when()
			.get("https://google.com/");
		//Get single header
		String serverHeader = res.getHeader("Server");
		System.out.println("Value of server header is: " + serverHeader);
		//Get all header info
		Headers allHeaders = res.getHeaders();
		for (Header header : allHeaders) {
			System.out.println(header.getName() + " = " + header.getValue());
		}
	}
}

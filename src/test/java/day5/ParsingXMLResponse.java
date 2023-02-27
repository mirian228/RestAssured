package day5;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;


public class ParsingXMLResponse {
	
	@Test(priority=1)
	void testXMLResponse() 
	{
		given()
			
		.when()
			.get("http://restapi.adequateshop.com/api/Traveler?page=1")
		.then()
			.header("Server", "Microsoft-IIS/10.0")
			.header("Content-Type", "application/xml; charset=utf-8")
			.body("TravelerinformationResponse.page", equalTo("1"))
			.body("TravelerinformationResponse.travelers.Travelerinformation[0].name", equalTo("Developer"))
			.statusCode(200)
			.log().all();
	}
	
	@Test(priority=2)
	void testXMLResponseWithObject() 
	{
	Response res=given()
			
		.when()
			.get("http://restapi.adequateshop.com/api/Traveler?page=1");
		
	
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(res.header("Content-Type"), "application/xml; charset=utf-8");
		Assert.assertEquals(res.header("Server"), "Microsoft-IIS/10.0");
		Assert.assertEquals(res.xmlPath().get("TravelerinformationResponse.page").toString(), "1");
		Assert.assertEquals(res.xmlPath().get("TravelerinformationResponse.travelers.Travelerinformation[0].name").toString(), "Developer");
		
	}
	
	@Test(priority=3)
	void testXMLResponseWithObjectDifferentValidations() 
	{
	Response res=given()
			
		.when()
			.get("http://restapi.adequateshop.com/api/Traveler?page=1");
		
		XmlPath xo = new XmlPath(res.asString());
		// Verify total traveler number
		List<String> travelers = xo.getList("TravelerinformationResponse.travelers.Travelerinformation");
		Assert.assertEquals(travelers.size(), 10);
		// Verify whether travler name is present in response
		List<String> travelerNames = xo.getList("TravelerinformationResponse.travelers.Travelerinformation.name");
		boolean status =false;
		for(String travelerName :travelerNames) {
			if(travelerName.equals("Developer")) {
				status=true;
				break;
			}
		}
		Assert.assertEquals(status, true);
		
		
	}
	
}

package day4;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ParsingJSONResponseData {

	// Approach 1
	@Test(priority = 1)
	public void testJsonResponse()

	{

		given().contentType(ContentType.JSON)
		.when()
		.get("http://localhost:3000/users")
		.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.body("people[1].name", equalTo("Kim"))
				.statusCode(200);

	}

	// JSON RESPONSE TEST Using object and TESTNG assertions ( Best Approach)
	// Approach2
	@Test(priority = 2)
	public void testJsonResponseUsingObject() 
	{
		Response res = 
				given()
				.contentType(ContentType.JSON)
				.when()
				.get("http://localhost:3000/users");
		Assert.assertEquals(res.getStatusCode(), 200); // Validation 1
		Assert.assertEquals(res.header("Content-Type"), "application/json; charset=utf-8");
		String personName = res.jsonPath().get("people[1].name").toString();
		Assert.assertEquals(personName, "Kim");
	}

	@Test(priority =3)
	public void testJsonResponseBodyData() 
	{
		Response res = 
				given()
				.contentType(ContentType.JSON)
				.when()
				.get("http://localhost:3000/store");
			
		
			// Validation 1
			JSONObject jo = new JSONObject(res.getBody().asString()); // Converting response to JSONObject type (Should remember to convert response to String before passing it to JSONObject
			
			boolean status=false;
			
			for(int i=0; i<jo.getJSONArray("book").length(); i++) 
			{
				String allNames = jo.getJSONArray("book").getJSONObject(i).get("title").toString();
				
				if(allNames.equals("The Lord of the Rings"))   // Checks if book title: "The Lord of the Rings" is in all titles
				{
					status=true;
					break;
				}
				
			}
			
			Assert.assertEquals(status, true); // Check if title found in JSON response ( if status is true than it is found )
			
			// Validation 2 
			double totalPrice = 0;
			for(int i=0; i<jo.getJSONArray("book").length(); i++) 
			{
				String allPrices = jo.getJSONArray("book").getJSONObject(i).get("price").toString();
				totalPrice = totalPrice + Double.parseDouble(allPrices);
			}
			System.out.println(totalPrice);
			
			Assert.assertEquals(totalPrice, 53.92); // Checks if total price is equal to the price from JSON response

	}
	
}

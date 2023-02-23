package day1;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

/*given()

when()

then()*/


public class HTTPRequests {
	
	int id;
	
	@Test(priority=1)
	void getUsers() 
	{
		given()
		
		.when()
			.get("https://reqres.in/api/users?page=2")
		
		.then()
			.statusCode(200)
			.body("page", equalTo(2))
			.log().all();
		
	}
	
	@Test(priority=2)
	void getUser() {
		given()
		
		.when()
			.get("https://reqres.in/api/users/2")
		.then()
			.statusCode(200)
			.body("data.id", equalTo(2))
			.log().all();
			
	}
	
	@Test(priority=3)
	void createUser() {
		HashMap data = new HashMap();
		data.put("name", "mirian");
		data.put("job", "test automation engineer");
		
		id=given()
			.contentType(ContentType.JSON)
			.body(data)
		.when()
			.post("https://reqres.in/api/users")
			.jsonPath().getInt("id");
		
			/*.then()
			.statusCode(201)
			.log().all();*/
	
	}
	
	@Test(priority=4, dependsOnMethods= {"createUser"})
	void updateUser() {
		HashMap data = new HashMap();
		data.put("name", "giorgi");
		data.put("job", "scrum master");
		
		given()
			.contentType(ContentType.JSON)
			.body(data)
		.when()
			.put("https://reqres.in/api/users/" + id)
		.then()
			.statusCode(200)
			.log().all();
			
	}
	
	@Test(priority=5, dependsOnMethods= {"createUser"}) 
		void deleteUser() {
			
			given()
			
			.when()
			 	.delete("https://reqres.in/api/users/" + id)
			
			.then()
				.statusCode(204)
				.log().all();
	}
	
}

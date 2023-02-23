package day3;

import org.testng.annotations.Test;

import com.mysql.cj.log.Log;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Logging {

	@Test(priority = 1)
	void testLogs() {
		given()

		.when()
				.get("https://reqres.in/api/users?page=2")
		.then()
				.log().body() // Prints only responseBody
				.log().cookies() // Print only cookies from response
				.log().headers() // Print only headers from response
				.log().all(); // Prints everything
	}
}

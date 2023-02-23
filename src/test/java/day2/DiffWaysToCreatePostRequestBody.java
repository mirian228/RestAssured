package day2;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;


 //1) Post request body using HashMap
 

public class DiffWaysToCreatePostRequestBody {

	//@Test(priority=1)
	void testPostUsingHashMap() 
	{
		String courseArr[] = {"C", "C++"};
		HashMap data = new HashMap();
		data.put("name", "giorgi");
		data.put("location", "kutaisi");
		data.put("phone", "12213123413");
		data.put("courses", courseArr);
		
		given()
			.contentType(ContentType.JSON)
			.body(data)
		.when()
			.post("http://localhost:3000/users")
		
		.then()
			.statusCode(201)
			.body("name", equalTo("giorgi"))
			.body("location", equalTo("kutaisi"))
			.body("phone", equalTo("12213123413"))
			.body("courses[0]", equalTo("C"))
			.body("courses[1]", equalTo("C++"))
			.header("Content-Type", "application/json; charset=utf-8")
			.log().all();
	
	}
	
	//@Test(priority=2)
	void testDelete() {
		given()
		
		.when()
			.delete("http://localhost:3000/users/6")
		.then()
			.statusCode(200);
	}
	
	
	
	// 2) Post request body using org.json
	// @Test
	void testPostUsingJsonLibrary()
	{
		JSONObject data = new JSONObject();
		data.put("name", "lasha");
		data.put("location", "tbilisi");
		data.put("phone", "12213123413");
		
		String courseArr[] = {"C", "C++"};
		data.put("courses", courseArr);
		
		given()
			.contentType(ContentType.JSON)
			.body(data.toString())
		.when()
			.post("http://localhost:3000/users")
		
		.then()
			.statusCode(201)
			.body("name", equalTo("lasha"))
			.body("location", equalTo("tbilisi"))
			.body("phone", equalTo("12213123413"))
			.body("courses[0]", equalTo("C"))
			.body("courses[1]", equalTo("C++"))
			.header("Content-Type", "application/json; charset=utf-8")
			.log().all();
	
	}
	
	
	
	// 3) Post request body using POJO class
	@Test
	void testPostUsingPOJO()
	{
		Pojo_PostRequest data = new Pojo_PostRequest();
		data.setName("Kote");
		data.setLocation("Poti");
		data.setPhone("3413241241");
		String coursesArr[] = {"C", "C++"};
		data.setCourses(coursesArr);
		
		given()
			.contentType(ContentType.JSON)
			.body(data)
		.when()
			.post("http://localhost:3000/users")
		
		.then()
			.statusCode(201)
			.body("name", equalTo("Kote"))
			.body("location", equalTo("Poti"))
			.body("phone", equalTo("3413241241"))
			.body("courses[0]", equalTo("C"))
			.body("courses[1]", equalTo("C++"))
			.header("Content-Type", "application/json; charset=utf-8")
			.log().all();
	}
	
	
	// 4) Post request using external JSON file data
	@Test
	void testPostUsingFile() throws FileNotFoundException
	{
		File f = new File(".\\src\\test\\esources\\info.json");
		FileReader fr = new FileReader(f);
		JSONTokener jt = new JSONTokener(fr);  // used for parsing json file and then needs to be passed to jsonobject
		JSONObject data = new JSONObject(jt);
		
		given()
			.contentType(ContentType.JSON)
			.body(data)
		.when()
			.post("http://localhost:3000/users")
		
		.then()
			.statusCode(201)
			.body("name", equalTo("Kote"))
			.body("location", equalTo("Poti"))
			.body("phone", equalTo("3413241241"))
			.body("courses[0]", equalTo("C"))
			.body("courses[1]", equalTo("C++"))
			.header("Content-Type", "application/json; charset=utf-8")
			.log().all();
	}
	
	
}

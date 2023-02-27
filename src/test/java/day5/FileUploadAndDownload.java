package day5;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class FileUploadAndDownload {

	@Test(priority=1)
	void singleFileUpload() 
	{
		File myfile = new File("");
		given()
			.multiPart("file", myfile)
			.contentType(ContentType.MULTIPART)
			//.contentType("multipart/form-data)
		.when()
			.post("http://localhost:8080/uploadFile")
			
		.then()
			.statusCode(200)
			.body("fileName", equalTo("Test1.txt"))
			.log().all();
	}
	
	@Test
	void multipleFileUpload() 
	{
		File myfile1 = new File("");
		File myfile2 = new File("");
		
	//	File filearr[] = {myfile1, myfile2}; Another method after this we can use only one .multiPart and pass filearr[]
		
		given()
			.multiPart("files", myfile1)
			.multiPart("files", myfile2)
			.contentType(ContentType.MULTIPART)
			//.contentType("multipart/form-data)
		.when()
			.post("http://localhost:8080/uploadFiles")
			
		.then()
			.statusCode(200)
			.body("[0].fileName", equalTo("Test1.txt"))
			.body("[1].fileName", equalTo("Test2.txt"))
			.log().all();
	}
	
	
	
	@Test(priority=2)
	void singleFileDownload() 
	{
		given() 
		
		.when() 
			.get("http://localhost:8080/downloadFile/Test1.txt")
		.then()
			.log().body()
			.statusCode(200);
			
	}
	
	
}

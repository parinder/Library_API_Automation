package Library_Request_Automation_Code;


import org.testng.annotations.Test;

import Resuable_Stuffs.bodies_request;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class Lib_Requests_Add_Delete_Get {

	Properties prop = new Properties();

	@Test
	public void add() throws IOException {

		FileInputStream fis = new FileInputStream(
				"/home/parinder/eclipse-workspace/Testing_Projects/Library_API_Automation/src/test/java/Resuable_Stuffs/DataDriven.properties");

		prop.load(fis);

		RestAssured.baseURI = prop.getProperty("Host");

		Response respAdd = given().body(bodies_request.addBody()).when().post(prop.getProperty("AddResource")).then()
				.assertThat().statusCode(200).extract().response();

		String respAddStr = respAdd.asString();
		System.out.println(respAddStr);
		JsonPath jp = new JsonPath(respAddStr);
		String id = jp.get("ID");
		System.out.println(id);

		Response respGet = given().queryParam("ID", id).when().get(prop.getProperty("DeleteResource")).then()
				.assertThat().statusCode(200).extract().response();

		String respGetStr = respGet.asString();
		System.out.println(respGetStr);

		Response respDel = given().body("{\n" + " \n" + "\"ID\" : \"" + id + "\"" + " \n" + "} ").when()
				.post(prop.getProperty("GetResource")).then().assertThat().statusCode(200).extract().response();
	}

}

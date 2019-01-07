package Jira_Automation_Code;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


import org.testng.annotations.Test;

public class Auth_CreateIssue {
	
	@Test
	public void auth() {

		RestAssured.baseURI = "https://parinder.atlassian.net";
		Response respAuth = given().header("Content-Type", "application/json")
				.body("{ \"username\": \"parindersinghraina\", \"password\": \"P@rinder55\" }").when()
				.post("/rest/auth/1/session").then().assertThat().statusCode(200).extract().response();
		
		String respAuthStr=respAuth.asString();
		System.out.println(respAuthStr);
		JsonPath js=new JsonPath(respAuthStr);
		String respAuthJson=js.get("session.value");
		System.out.println(respAuthJson);
	

		Response respCI=given().header("Content-Type","application/json").header("Cookie","JSESSIONID=" + respAuthJson).body("{\n" + 
				"    \"fields\": {\n" + 
				"       \"project\":\n" + 
				"       {\n" + 
				"          \"key\": \"RAA\"\n" + 
				"       },\n" + 
				"       \"summary\": \"REST ye merry gentlemen.\",\n" + 
				"       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" + 
				"       \"issuetype\": {\n" + 
				"          \"name\": \"Bug\"\n" + 
				"       }\n" + 
				"   }\n" + 
				"}").when().post("/rest/api/2/issue/").then().assertThat().statusCode(201).extract().response();
		String respCIStr=respCI.asString();
		System.out.println(respCIStr);
		JsonPath jsCI=new JsonPath(respCIStr);
		String respCIJson=jsCI.get("id");
		System.out.println(respCIJson);
		
		
	}
	
}

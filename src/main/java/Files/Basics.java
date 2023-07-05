package Files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {
public static void main(String args[]) throws IOException
{
	RestAssured.baseURI="https://rahulshettyacademy.com";
	
	String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	.body(new String(Files.readAllBytes(Paths.get("D:\\Eclipse_Workspace\\RestAssuredDemoProject\\src\\main\\resources\\addPlace.json"))))
	.when().post("maps/api/place/add/json")
	.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
	.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
	System.out.println(response);
	JsonPath js=new JsonPath(response);
	String placeId = js.getString("place_id");
	System.out.println(placeId);
	String newAddress="wework,Hebbal,Bangalore";
	
	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	.body("{\r\n"
			+ "    \"place_id\":\""+placeId+"\",\r\n"
			+ "    \"address\":\""+newAddress+"\",\r\n"
			+ "    \"key\":\"qaclick123\"\r\n"
			+ "}")
	.when().put("maps/api/place/update/json")
	.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
	String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId)
	.when().get("maps/api/place/get/json")
	.then().assertThat().log().all().statusCode(200).extract().response().asString();
	JsonPath js1=ReusableMethods.rawToJson(getPlaceResponse);
	String actualAddress = js1.getString("address");
	System.out.println(actualAddress);
	Assert.assertEquals(actualAddress, newAddress);
}
}

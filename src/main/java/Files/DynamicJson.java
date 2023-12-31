package Files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
@Test(dataProvider="BooksData")
public void addBook(String aisle,String isbn)
{
	RestAssured.baseURI="http://216.10.245.166";
	String response=given().log().all().header("Content-Type","application/json")
	.body(payload.AddBook(aisle,isbn))
	.when().post("Library/Addbook.php")
	.then().log().all().assertThat().statusCode(200).extract().asString();
	JsonPath js=ReusableMethods.rawToJson(response);
	String id=js.get("ID");
	System.out.println(id);
}

@DataProvider(name="BooksData")
public Object[][] getData()
{
	return new Object[][] {{"1234","uyqr"},{"5678","jjui"},{"9012","eshr"},{"8912","hoieyr"}};
}
}

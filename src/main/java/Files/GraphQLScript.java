package Files;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {
public static void main(String args[])
{
	String response=given().log().all().header("Content-Type","application/json")
	.body("{\"query\":\"query{\\n  character(characterId:1)\\n  {\\n    name\\n    gender\\n    id\\n    status\\n  }\\n  location(locationId: 1)\\n  {\\n    name\\n    dimension\\n  }\\n  episode(episodeId:1)\\n  {\\n    name\\n    episode\\n    id\\n    air_date\\n  }\\n  characters(filters: {name:\\\"Rahul\\\"})\\n  {\\n    info{\\n      count\\n    }\\n    result\\n    {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters:{episode:\\\"hulu\\\"})\\n  {\\n    result{\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\t\",\"variables\":{}}")
	.when().post("https://rahulshettyacademy.com/gq/graphql")
	.then().extract().response().asString();
	System.out.println(response);
	JsonPath js=new JsonPath(response);
	String characterName=js.getString("data.character.name");
	Assert.assertEquals(characterName, "Robin");	
}
}

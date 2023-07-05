package Files;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import POJO.LoginRequest;
import POJO.LoginResponse;
import POJO.OrderDetail;
import POJO.Orders;

public class EcommerceAPITest {

	public static void main(String[] args) {
		//Login Request
		RequestSpecification reqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();

		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setUserEmail("postman17@gmail.com");
		loginRequest.setUserPassword("Deesh@@#17");

		RequestSpecification reqLogin=given().relaxedHTTPSValidation().log().all().spec(reqspec).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login")
				.then().log().all().extract().response().as(LoginResponse.class);

		System.out.println(loginResponse.getToken());
		String token=loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId=loginResponse.getUserId();

		//Add Product 
		RequestSpecification addProductReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();

		RequestSpecification reqAddProduct = given().log().all().spec(addProductReqSpec).param("productName","Shoes")
				.param("productAddedBy",userId).param("productCategory","Sports Shoes")
				.param("productSubCategory", "Nike Shoes").param("productPrice", "3695")
				.param("productDescription", " Air Jordan 35 ").param("productFor", "Mens")
				.multiPart("productImage",new File("D:\\Eclipse_Workspace\\RestAssuredDemoProject\\src\\main\\resources\\shoes1.png"));

		String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product")
				.then().log().all().extract().response().asString();
		JsonPath js=new JsonPath(addProductResponse);
		String productId=js.get("productId");
		System.out.println(productId);

		//Create Order
		RequestSpecification createorderReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		Orders orders=new Orders();
		orders.setOrders(orderDetailList);	

		RequestSpecification createOrderReq=given().log().all().spec(createorderReqSpec).body(orders);
		String responseAddOrder=createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();
		System.out.println(responseAddOrder);
		JsonPath js1=new JsonPath(responseAddOrder);
		String OrderId=js1.getString("orders");
		String OId = OrderId.replaceAll("[^a-zA-Z0-9]","");
		System.out.println(OId);

		//Delete Product
		RequestSpecification deleteProdReq=given().log().all().spec(createOrderReq).pathParam("productId",productId);
		String deleteProductResponse=deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();
		JsonPath js2=new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully",js2.get("message"));
		
		//Delete Order
				RequestSpecification deleteOrdReq=given().log().all().spec(createOrderReq).pathParam("OrderId",OId);
				String deleteOrderResponse=deleteOrdReq.when().delete("/api/ecom/order/delete-order/{OrderId}")
				.then().log().all().extract().response().asString();
				JsonPath js3=new JsonPath(deleteOrderResponse);
				Assert.assertEquals("Orders Deleted Successfully",js3.get("message"));
		
	}
}

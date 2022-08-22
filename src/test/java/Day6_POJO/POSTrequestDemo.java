package Day6_POJO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
/**
 * https://www.mockaroo.com/ for generating data
 * */

public class POSTrequestDemo {

    @BeforeClass
    public void BeforeClass() {
        RestAssured.baseURI="http://100.26.60.119:8000";
    }

    /*
   Given accept type and Content type is JSON
   And request json body is:
   {
     "gender":"Male",
     "name":"MikeEU",
     "phone":8877445596
  }
   When user sends POST request to '/api/spartans'
   Then status code 201
   And content type should be application/json
   And json payload/response/body should contain:
   "A Spartan is Born!" message
   and same data what is posted
*/
    @Test
    public void POSTrequest(){
        String jsonBody ="{\n" +
                "      \"gender\":\"Male\",\n" +
                "      \"name\":\"MikeEU\",\n" +
                "      \"phone\":8877445596\n" +
                "   }";
        //by using log all I can see all output
        Response response = RestAssured.given().log().all().accept(ContentType.JSON). //I am asking json file from you
                            and().contentType(ContentType.JSON). //I am sending json file to you
                            and().body(jsonBody).
                            when().post("/api/spartans"); //POST

        Assert.assertEquals(response.statusCode(),201); //for POST 201 !!!!!!!!

        Assert.assertEquals(response.contentType(),"application/json");

        String actualMessage = response.path("success");
        String expectedMessage = "A Spartan is Born!";

        Assert.assertEquals(actualMessage,expectedMessage);

        //assertion for spartan data
        String name = response.path("data.name");
        String gender = response.path("data.gender");

        Assert.assertEquals(name,"MikeEU");
        Assert.assertEquals(gender,"Male");


    } //send request as String

    @Test
    public void PostNewSpartan() {
        //create a map to keep request json body information
        Map<String , Object> requestMap = new HashMap<>();

        //adding value
        requestMap.put("name", "Mike");
        requestMap.put("gender", "Male");
        requestMap.put("phone", "1234567890");

        Response post = RestAssured.given().log().all().accept(ContentType.JSON). //log all of request if i use after then and verification it prints response
                and().contentType(ContentType.JSON).
                body(requestMap). // !!!! here in body method , it converts to json and send automatically
                when().post("api/spartans");

        Assert.assertEquals(post.statusCode(),201);

    } //send request as Map

    @Test
    public void PostNewSpartan3() {
        Spartan spartanEU = new Spartan();
        spartanEU.setName("MikeEU3");
        spartanEU.setGender("Male");
        spartanEU.setPhone(8877445596l);


        RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when()
                .post("/api/spartans")
                .then().log().all()
                .statusCode(201)
                .and()
                .contentType("application/json")
                .and()
                .body("success", Matchers.is("A Spartan is Born!"),
                        "data.name",Matchers.equalTo("MikeEU3"),
                        "data.gender",Matchers.equalTo("Male"),
                        "data.phone",Matchers.equalTo(8877445596l));

        //after post request, send a get request to generated spartan


        //END OF POST REQUEST
        Response response = RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when()
                .post("/api/spartans");

        //get request
        int idFromPost = response.path("data.id");
        System.out.println("id = " + idFromPost);
        //after post request, send a get request to generated spartan
        RestAssured.given().accept(ContentType.JSON)
                .pathParam("id",idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();
    }



}

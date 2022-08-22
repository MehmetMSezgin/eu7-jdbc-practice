package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue; //when you import statically you can directly use methods

public class SpartanTestWithParameters {

    @BeforeClass
    public void beforeclass(){
        baseURI="http://23.23.52.36:8000";
        //once you created , you dont write this again and again
        //get("/api/spartans") before of this path is already baseURI
    }
    /*
          Given accept type is Json
          And Id parameter value is 5
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json;charset=UTF-8
          And "Blythe" should be in response payload
       */
    @Test
    public void parameterTest() {
        //dynamic way
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",5)
                .when().get("/api/spartans/{id}"); //curly bracelet makes this path dynamic
        //I did not write full path because I have before class
        //we can make it static



        Assert.assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.contentType(),"application/json");

        assertTrue(response.body().asString().contains("Blythe"));

    }

     /*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json;charset=UTF-8
        And "Spartan Not Found" message should be in response payload
     */

    @Test
    public void getSpartanID_Negative_PathParam(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get("/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),404);

        Assert.assertEquals(response.contentType(),"application/json");

        Assert.assertTrue(response.prettyPrint().contains("Not Found"));

    }

     /*
        Given accept type is Json
        And query parameter values are :
        gender|Female
        nameContains|e
        When user sends GET request to /api/spartans/search
        Then response status code should be 200
        And response content-type: application/json;charset=UTF-8
        And "Female" should be in response payload
        And "Janette" should be in response payload
     */
    @Test
    public void positiveTestWithQueryParam() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get("/api/spartans/search");
        //I get these parameters from api documentation api/spartans/search
        //parameters is written there

        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content-type
        Assert.assertEquals(response.contentType(),"application/json");
        //verify Female in the response
        assertTrue(response.body().asString().contains("Female"));
        //verify Janette in the response
        assertTrue(response.body().asString().contains("Janette"));

    }
    @Test
    public void positiveTestWithQueryParamWithMaps(){
        //create a map and add query parameters
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("gender","Female");
        queryMap.put("nameContains","e");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/api/spartans/search");

        //response verification
        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content-type
        Assert.assertEquals(response.contentType(),"application/json");
        //verify Female in the response
        assertTrue(response.body().asString().contains("Female"));
        //verify Janette in the response
        assertTrue(response.body().asString().contains("Janette"));

    }

}

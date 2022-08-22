package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;

public class spartanGETRequest {
    String spartanURL = "http://23.23.52.36:8000";

    @Test
    public void request() {
        Response response = RestAssured.get(spartanURL+"/api/spartans");
        System.out.println(response.statusCode());
    }

    @Test
    public void specific(){
        Response response = RestAssured.get(spartanURL + "/api/spartans/3");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //content type
        Assert.assertEquals(response.contentType(),"application/json");

        //json body should contain Fidole
        Assert.assertTrue(response.prettyPrint().contains("Fidole"));
        //or
        Assert.assertTrue(response.body().asString().contains("Fidole"));
    }

     /*
        Given no headers provided
        When Users sends GET request to /api/hello
        Then response status code should be 200
        And Content type header should be “text/plain;charset=UTF-8”
        And header should contain date
        And Content-Length should be 17
        And body should be “Hello from Sparta"
        */

    @Test
    public void helloTest(){
        //request
        Response response = when().get(spartanURL + "/api/hello");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content-type
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        //verify we have headers named date
        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));
        //to get any header passing as a key
        System.out.println(response.header("Content-Length"));
        System.out.println(response.header("Date"));

        //verify content length is 17
        Assert.assertEquals(response.header("Content-Length"),"17");

        //verify hello from sparta
        Assert.assertEquals(response.getBody().asString(),"Hello from Sparta");
    }

}

package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class simpleGetRequest {
    String hrURL = "http://23.23.52.36:1000/ords/hr/regions";

    @Test
    public void test1() {
        Response response = RestAssured.get(hrURL);//request
        System.out.println(response.statusCode());
        response.prettyPrint(); //print the json body
    }

    @Test
    public void assertion(){
        //request //rest Assured's gherkin language
        Response response = RestAssured.given().accept(ContentType.JSON).when().get(hrURL);

        Assert.assertEquals(response.statusCode(),200);

        //verify content type is json
        System.out.println(response.contentType());
        Assert.assertEquals(response.contentType(),"application/json");

    }

    @Test
    public void assertionWithRestAssured() {
        RestAssured.given().accept(ContentType.JSON)
                   .when().get(hrURL)
                   .then().assertThat().statusCode(200)
                   .and().contentType("application/json");
    }

    @Test
    public void verifyBodyContains() {
        Response response = RestAssured.given().accept(ContentType.JSON)
                                        .when().get(hrURL + "/2");

        //verify status code
        System.out.println(response.statusCode());

        //verify content type
        System.out.println(response.contentType());

        //verify body contains america
        System.out.println(response.body().asString().contains("Americas")); //returns boolean


    }

}

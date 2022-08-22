package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SpartanTestWithPath {
    @BeforeClass
    public void beforeclass(){ //this is my ec2's ip . it wont work anymore check new java class
        baseURI="http://23.23.52.36:8000";
        //once you created , you dont write this again and again
        //get("/api/spartans") before of this path is already baseURI
    }

     /*
   Given accept type is json
   And path param id is 10
   When user sends a get request to "api/spartans/{id}"
   Then status code is 200
   And content-type is "application/json;charset=UTF-8"
   And response payload values match the following:
           id is 10,
           name is "Lorenza",
           gender is "Female",
           phone is 3312820936
    */

    @Test
    public void getOneSpartan_path() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("/api/spartans/{id}");
        //normally path ends like that in postman /api/spartans/:id
        //but here I use {} to parmetiraze

        Assert.assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //printing each key value in json body/payload
        System.out.println(response.path("id").toString());
        System.out.println(response.path("name").toString());  //path() or body().path() it does not matter
        System.out.println(response.body().path("gender").toString());
        System.out.println(response.body().path("phone").toString());

        //this type T type
        Object T = response.path("id");
        Object T1 = response.path("name"); //you can define as object
        int id = response.path("id"); //casting
        String name = response.path("name"); //casting
    }



}

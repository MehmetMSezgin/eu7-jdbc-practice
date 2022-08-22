package apitests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.List;


public class SpartanPathWithJsonPathNewIP {
    @BeforeClass
    public void BeforeClass() {
        RestAssured.baseURI="http://100.26.60.119:8000";
    }

    @Test
    public void test() {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .and().pathParam("id",11)
                .when().get("/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        Assert.assertEquals(response.path("name").toString(),"Nona");
        Assert.assertEquals(response.path("gender").toString(),"Female");

        //second way JsonPath
        JsonPath jsonPath = response.jsonPath(); //first create object

        System.out.println(jsonPath.getInt("id")); //reach directly
        System.out.println(jsonPath.getString("name"));
        System.out.println(jsonPath.getLong("phone"));

    }
}

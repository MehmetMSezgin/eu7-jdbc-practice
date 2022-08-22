package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class hrAPIparameterTest {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api");

    }

    /*
        Given accept type is Json
        And parameters: q = {"region_id":2}
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
        {"region_id":2}
     */
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"AD_VP\"}")
                .when().get("/employees");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("United States of America"));
    }
}

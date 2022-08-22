package apitests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import static io.restassured.RestAssured.baseURI;

public class SpartanWithCydeoIP {

    @BeforeClass
    public void BeforeClass() {
        baseURI="http://100.26.60.119:8000";
    }

    @Test
    public  void getAllSpartansWithPath() {
        Response response = given().accept(ContentType.JSON)
                .when().get("api/spartans");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //i wanna specifically point a spartan
        System.out.println(response.path("id[0]").toString()); //first spartan's id
        System.out.println(response.path("name[0]").toString()); //do not forget toString

        String lastFirstNAme = response.path("name[-1]"); //-1 returns to last element of file
        //this is not array this syntax is Jpath
        System.out.println("lastFirstNAme = " + lastFirstNAme);

        //print all names
        List<String> names = response.path("name");
        System.out.println("names = " + names);

        //all phone number
        List<Long> pNumbers = response.path("phone");
        System.out.println("pNumbers = " + pNumbers);
    }
}

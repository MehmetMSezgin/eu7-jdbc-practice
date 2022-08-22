package apitests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.List;

public class HrApiWithPath {
    @BeforeClass
    public void BeforeClass() {
        baseURI="http://100.26.60.119:1000";
    }

    @Test
    public void getCountriesWithPath() {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("/ords/hr/countries");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        //print limit value
        System.out.println("response.path(\"limit\") = " + response.path("limit"));
        //print hasMore
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        //i can not directly print countries because it is inside of "item"
        //by using "." I get into item value
        //so my first country
        System.out.println(response.path("items.country_id[0]").toString());
        System.out.println(response.path("items.country_name[0]").toString());

        //list of countries
        List<String> country_names = response.path("items.country_name");
        System.out.println(country_names.toString()); //to print countries I tried without "toString()" and it worked
        //it is speciality of List ! not JsonPath!!!

        //go into deeper
        System.out.println(response.path("items.links[2].href").toString());
        //VERY IMPORTANT if into json file , it keeps array into links you should use index number again
        //in jamal class it is used like that --> response.path("items.links[2].href[1]").toString()
        // so in the future If you can not reach , you can try to put index number

        //assert that region ids smaller than 4
        List<Integer> regionIDs = response.path("items.region_id");
        boolean flag = false;
        for (Integer regionID : regionIDs) {
            System.out.println(regionID);
            if (regionID>4) {
                Assert.assertTrue(flag);
            }
        }

    }
}

package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class jsonToJavaCollection {
    @BeforeClass
    public void BeforeClass() {
        RestAssured.baseURI="http://100.26.60.119:8000";
    }

    @Test
    public void deSerializationMAP(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}");
        Assert.assertEquals(response.statusCode(),200);

        //We will convert json response to java map
        Map<String,Object> jsonDataMap = response.body().as(Map.class);
        //key string but value can be any because of that we put object

        //To get the body as map we need Jackson or Gson (Databinder)
        //so I add Gson dependency from mvnrepository
        //and now everything is ok
        System.out.println("jsonDataMap = " + jsonDataMap);

        String name = (String) jsonDataMap.get("name");
        System.out.println("name = " + name);

        double id = (double) jsonDataMap.get("id");
        System.out.println("id = " + id);

        BigDecimal phone = new BigDecimal(String.valueOf(jsonDataMap.get("phone")));
        System.out.println("phone = " + phone);
        //I use math class BigDecimal for phone because in map phone number was like "phone=1.938695106E9"


    }

    @Test
    public void deSerializationMAPintoList(){
        Response response = RestAssured.given().accept(ContentType.JSON).
                when().get("/api/spartans");

        Assert.assertEquals(response.statusCode(),200);

        //we need to de serialize JSON response to list of maps
        List<Map<String,Object>> jsonDataMapIntoList = response.body().as(List.class); //this is important
        // !!!!! as(List.class);
        System.out.println(jsonDataMapIntoList);

        //second spartan first name
        System.out.println(jsonDataMapIntoList.get(1).get("name"));

        Map<String ,Object> spartan300 = jsonDataMapIntoList.get(2);
        System.out.println("spartan300 = " + spartan300);
    }

    @Test
    public void regionMap() {
        Response response = RestAssured.given().accept(ContentType.JSON).
                when().get("http://100.26.60.119:1000/ords/hr/regions");
        Assert.assertEquals(response.statusCode(),200);

        Map<String ,Object> deSerialization = response.body().as(Map.class);

        deSerialization.get("items"); //items keeps list of map inside
        /**
         * "items": [
         *         {
         *             "region_id": 1,
         *             "region_name": "Europe",
         *
         *         },
         *         {
         *             "region_id": 2,
         *             "region_name": "Americas",
         *
         *         },
         *         {
         *             "region_id": 3,
         *             "region_name": "Asia",
         *
         *         },
         *         {
         *             "region_id": 4,
         *             "region_name": "Middle East and Africa",
         *
         *         },
         *         {
         *             "region_id": 11,
         *             "region_name": "Cybertek Germany",
         *
         *         }
         *     ]
         * */
        List<Map<String,Object>> itemsList = (List<Map<String, Object>>) deSerialization.get("items");
        //casting is needed

        Object region_name = itemsList.get(0).get("region_name");
        System.out.println("region_name = " + region_name);

        //Actually we did this
        Map<String, List<Map<String ,Object>>> summary;


    }


}

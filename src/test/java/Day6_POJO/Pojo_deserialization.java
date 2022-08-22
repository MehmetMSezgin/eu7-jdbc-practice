package Day6_POJO;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class Pojo_deserialization {
    @Test
    public void oneSpartanPojo() {
        Response response = RestAssured.given().accept(ContentType.JSON).
                and().pathParam("id", 15).
                when().get("http://100.26.60.119:8000/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),200);

        //json to our spartan class object
        //json to pojo

        Spartan spartan15 = response.body().as(Spartan.class);

        Assert.assertEquals(spartan15.getId(),15);

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        /*************** Json file converting to class
         * https://www.jsonschema2pojo.org/
         * ****************/
    }


    @Test
    public void regionToPojo(){

        Response response = RestAssured.when().get("http://100.26.60.119:1000/ords/hr/regions");

        Assert.assertEquals(response.statusCode(),200);

        //JSON to POJO(regions class)

        Regions regions = response.body().as(Regions.class);

        System.out.println("regions.getHasMore() = " + regions.getHasMore());
        System.out.println("regions.getCount() = " + regions.getCount());

        System.out.println(regions.getItems().get(0).getRegionName());

        List<Item> items = regions.getItems();

        System.out.println(items.get(1).getRegionId());
    }

    /**
     *     @SerializedName("items")
     *     //with this annotation ,it helps me to find java data match json file data even if names are different
     * */

    @Test
    public void gson_example(){

        Gson gson = new Gson();

        //JSON to JAva collections or Pojo --> De-serialization
        String myJsonData = "{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";

        Map<String,Object> map = gson.fromJson(myJsonData, Map.class);
        System.out.println(map);

        Spartan spartan15 = gson.fromJson(myJsonData,Spartan.class);
        System.out.println(spartan15);

        //-----------SERIALIZATION---------------
        //JAVA Collection or POJO to JSON
        Spartan spartanEU = new Spartan(200,"Mike","Male",123123123);

        String jsonSpartanEU = gson.toJson(spartanEU);
        System.out.println(jsonSpartanEU);
    }

}

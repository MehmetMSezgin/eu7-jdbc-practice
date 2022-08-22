package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

;

public class HamcrestMatchersApiTest {
    //no need for testng becuase it is already assertion library

     /*
      given accept type is Json
      And path param id is 15
      When user sends a get request to spartans/{id}
      Then status code is 200
      And content type is Json
      And json data has following
          "id": 15,
          "name": "Meta",
          "gender": "Female",
          "phone": 1938695106
       */

    @Test //hamcrest is into restAssured
    public void OneSpartanWithHamcrest(){
        RestAssured.given().accept(ContentType.JSON)
                .and().pathParam("id",15).
                when().get("http://100.26.60.119:8000/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType(Matchers.equalTo("application/json"))
                .and().assertThat().body("id",Matchers.equalTo(15),
                        "name",Matchers.equalTo("Meta"),
                        "gender",Matchers.equalTo("Female"),
                        "phone",Matchers.equalTo(1938695106));


    }

    @Test
    public void teacherData(){
        RestAssured.given().accept(ContentType.JSON)
                .and().pathParam("id",3165)
                .when().log().all().get("http://api.cybertektraining.com/teacher/{id}")
                .then().assertThat().statusCode(200)
                .and().contentType(Matchers.equalTo("application/json;charset=UTF-8"))
                .and().header("Content-Length",Matchers.equalTo("246"))
                .and().header("Connection",Matchers.equalTo("Keep-Alive"))
                .and().header("Date",Matchers.notNullValue())
                .and().assertThat().body("teachers.firstName[0]",Matchers.equalTo("James"),
                        "teachers.lastName[0]",Matchers.equalTo("Bond"),
                        "teachers.gender[0]",Matchers.equalTo("male"))
                .log().all(); //by using log , you can print all info
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //.log().all;
  //important !!!!!!!!!!!!!!!!!!!!
    }

    @Test
    public void teachersWithDepartments(){

        RestAssured.given().accept(ContentType.JSON)
                .and().pathParam("name","Computer")
                .when().log().all().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200).and()
                .contentType(Matchers.equalTo("application/json;charset=UTF-8")).and()
                .body("teachers.firstName",Matchers.hasItems("Alexander","Marteen"));

        //Matchers.hasItems("Alexander","Marteen") multiple assertion by using Matchers

    }

}

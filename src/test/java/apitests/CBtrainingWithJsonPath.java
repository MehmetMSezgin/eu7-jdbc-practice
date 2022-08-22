package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;

public class CBtrainingWithJsonPath { //INFO : array index[] and get into json object{} explanation is here

    @BeforeClass
    public void BeforeClass() {
        RestAssured.baseURI= ConfigurationReader.get("cybertekTraining_api_url");
    }
    //api documentation : http://api.cybertektraining.com/swagger-ui.html#/
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON).
                            and().pathParam("id", 24661).
                            when().get("/student/{id}");
        Assert.assertEquals(response.statusCode(),200);

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        //get first name
        System.out.println(jsonPath.getString("students.firstName[0]"));
        //in order to reach into array, you should use index number even if you have just one firstname
        //by the way it is case sensitive so you need to read json file carefully

        //when json object into one each other , you should use "."
        //and index number
        String phone = jsonPath.getString("students.contact[0].emailAddress");
        System.out.println(phone);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //I did not use index number for email because after contact there is no array
        //inside of contact is just json file
        //but in hrApiWithJpath class I use two time index !!!!!
        //why we use it. because in that example link was holding another array
        //if there is array [] use index
        //if there is no array just json object {} use "."
        //one array can hold multiple json files [{},{},{},{},{}]

        /**
         * {
         *     "students": [
         *         {
         *             "studentId": 24661,
         *             "firstName": "Athena",
         *             "lastName": "Wick",
         *             "batch": 22,
         *             "joinDate": "04/04/2021",
         *             "birthDate": "05/18/1990",
         *             "password": "string",
         *             "subject": "postman automation",
         *             "gender": "Female",
         *             "admissionNo": "NUMBER",
         *             "major": "string",
         *             "section": "string",
         *             "contact": {
         *                 "contactId": 24681,
         *                 "phone": "string",
         *                 "emailAddress": "athena@gmail.com",
         *                 "premanentAddress": "Colorado"
         *             },
         *             "company": {
         *                 "companyId": 24601,
         *                 "companyName": "string",
         *                 "title": "string",
         *                 "startDate": "string",
         *                 "address": {
         *                     "addressId": 24601,
         *                     "street": "string",
         *                     "city": "Loveland",
         *                     "state": "string",
         *                     "zipCode": 0
         *                 }
         *             }
         *         }
         *     ]
         * }
         */

        String city = jsonPath.getString("students.company[0].address.city");
        System.out.println("city = " + city);

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println("zipCode = " + zipCode);
        //**********************************

        String Firstname2 = jsonPath.getString("students.firstName");
        System.out.println("Firstname2 = " + Firstname2); //returns [Athena]

        /*String Firstname3 = response.path("students.firstName");
        System.out.println("Firstname3 = " + Firstname3); */ //returns casting error


    }



}

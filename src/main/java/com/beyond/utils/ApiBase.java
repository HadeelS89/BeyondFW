package com.beyond.utils;

import com.mongodb.util.JSON;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class ApiBase {


    RequestSpecification httpRequest;


    public String uploadFilePost(String filePath,
                                 String headerKey1, String headerValue1,
                                 String headerKey2, String headerValue2) {
        File file = new File(filePath);
        Response response =
                given()
                        .header(headerKey1, headerValue1)
                        .header(headerKey2, headerValue2)
                        .multiPart("xml_file", file) // attachemnt name in UI
                        .when()
                        .post()
                        .andReturn();
        return response.asPrettyString();
    }

    public String postRequest(String requestPayLoad,
                              String headerKey1, String headerValue1,
                              String headerKey2, String headerValue2) {
        //  File file = new File(filePath);
        Response response =
                given()
                        .header(headerKey1, headerValue1)
                        .header(headerKey2, headerValue2)
                        .and()
                        .body(requestPayLoad)
                        .when()
                        .post()
                        .andReturn();
        return response.asPrettyString();
    }

    public String getRequest() {

        Response response =
                given().header("accept", "application/json")
                        //     .param("sort_order", "desc").param("page", "5").param("page_limit", "1")
                        .when()
                        .get()
                        .then().extract().response();
        return response.asPrettyString();
    }

    public String putRequest(String requestPayLoad,
                             String headerKey1, String headerValue1,
                             String headerKey2, String headerValue2,
                             String tokenHeader) {

        Response response =
                given()
                        .header(headerKey1, headerValue1)
                        .header(headerKey2, headerValue2)
                        .and()
                        .body(requestPayLoad)
                        .when()
                        .put()
                        .andReturn();
        return response.asPrettyString();

    }

    @Test
    public void test() throws ParseException {
        RestAssured.baseURI = "https://pnlg-be.dev.udi.beyond.ai/api/pnlg-examples";


        String uploadResponse = uploadFilePost("src/main/resources/xmlTests/Beyond/Regression_CMD.xml",
                "accept", "application/json"
                , "Content-Type", "multipart/form-data");
        System.out.println(uploadResponse);
        JSONParser parser = new JSONParser();
        JSONObject results = (JSONObject) parser.parse(uploadResponse);
        String id = (String) results.get("id");

        String xml_uri = (String) results.get("xml_uri");


        Assert.assertTrue(!id.equals(""));
        Assert.assertTrue(xml_uri.contains(".xml"));


    }

}

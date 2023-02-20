package com.epam.tc.hw9;

import com.epam.entities.BoardEntity;
import com.epam.entities.CardEntity;
import com.epam.entities.ListEntity;
import com.epam.entities.Trello;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import util.Config;
import util.Endpoint;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {

    public static final String PATH_TO_PROPERTY = "src/test/resources/config.properties";
    Map<String, String> params = new HashMap<>();
    protected String boardName = "Board from API tests";
    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;
    BoardEntity board;

    @BeforeClass
    public void setup() {
        System.out.println("setup");
        Config config = new Config(PATH_TO_PROPERTY);
        RestAssured.baseURI = Endpoint.TRELLO_URI;
        requestSpec = new RequestSpecBuilder().addQueryParam("key", config.getApiKey()).addQueryParam("token", config.getApiToken())
                .setContentType(ContentType.JSON).build();
        responseSpec = new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK).expectContentType(ContentType.JSON)
                .build();
    }

    @AfterClass
    public void close() {
        RestAssured.reset();
        requestSpec = null;
        responseSpec = null;
    }

    protected ListEntity createListOnTheBoard(String boardId, String listName) {
        params.clear();
        params.put("name", listName);
        params.put("idBoard", boardId);
        return sendPostRequest(params, Endpoint.LISTS)
                .then().spec(responseSpec)
                .extract().body().as(ListEntity.class);
    }

    protected CardEntity createCardInTheList(String idList, String cardName) {
        params.clear();
        params.put("idList", idList);
        params.put("name", cardName);
        return sendPostRequest(params, Endpoint.CARD)
                .then().spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    public Response sendPostRequest(Map<String, String> params, String endPoint) {
        return given()
                .spec(requestSpec)
                .when()
                .queryParams(params)
                .basePath(endPoint)
                .post();
    }

    public Response sendGetRequest(Map<String, String> params, String endPoint) {
        return given()
                .spec(requestSpec)
                .when().basePath(endPoint)
                .pathParams(params)
                .get();
    }

    public Response sendPutRequest(Map<String, String> params, String endPoint, Trello entity) {
        return given()
                .spec(requestSpec)
                .when().basePath(endPoint)
                .pathParams(params)
                .body(entity)
                .put();
    }

    public void sendDeleteRequestAndCheck(String id, String endPoint) {
        given()
                .spec(requestSpec)
                .when().basePath(endPoint)
                .pathParam("id", id)
                .delete()
                .then()
                .spec(responseSpec);
    }
}

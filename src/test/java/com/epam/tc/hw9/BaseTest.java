package com.epam.tc.hw9;

import com.epam.entities.BoardEntity;
import com.epam.entities.CardEntity;
import com.epam.entities.ListEntity;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import util.Endpoint;

import static io.restassured.RestAssured.given;
import static util.Config.getApiKey;
import static util.Config.getApiToken;

public class BaseTest {
    protected String boardName = "Board from API tests";
    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;
    BoardEntity board;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Endpoint.TRELLO_URI;
        requestSpec = new RequestSpecBuilder().addQueryParam("key", getApiKey()).addQueryParam("token", getApiToken())
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

    protected BoardEntity createBoard(String boardName) {
        return given()
                .spec(requestSpec)
                .when()
                .queryParam("name", boardName)
                .basePath(Endpoint.BOARD)
                .post()
                .then()
                .spec(responseSpec)
                .extract().body().as(BoardEntity.class);
    }

    protected ListEntity createListOnTheBoard(String boardId, String listName) {
        return given()
                .spec(requestSpec)
                .basePath(Endpoint.LISTS)
                .queryParam("name", listName).queryParam("idBoard", boardId)
                .when()
                .post()
                .then().spec(responseSpec)
                .extract().body().as(ListEntity.class);
    }

    protected CardEntity createCardInTheList(String idList, String cardName) {
        return given()
                .spec(requestSpec)
                .when()
                .queryParam("idList", idList)
                .queryParam("name", cardName)
                .basePath(Endpoint.CARD)
                .post()
                .then().spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    protected void deleteBoard(String boardId) {
        given()
                .spec(requestSpec)
                .when().basePath(Endpoint.BOARD_ID)
                .pathParam("id", boardId)
                .delete()
                .then()
                .log().all()
                .spec(responseSpec);
    }
}

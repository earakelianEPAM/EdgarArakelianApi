package com.epam.tc.hw9;

import com.epam.entities.CardEntity;
import com.epam.entities.ListEntity;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.Endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class CardsTests extends BaseTest {

    CardEntity cardEntity;
    ListEntity listEntity;
    String listName = "created list";
    String cardName = "cardName";
    String boardName = "board with List";

    @BeforeClass
    protected void createBoardWithList() {
        board = createBoard(boardName);
        listEntity = createListOnTheBoard(board.id(), listName);
    }

    @BeforeMethod
    protected void beforeCreateCard() {
        cardEntity = given()
                .spec(requestSpec)
                .when()
                .queryParam("idList", listEntity.id())
                .queryParam("name", cardName)
                .basePath(Endpoint.CARD)
                .post()
                .then()
                .body("name", equalTo(cardName))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @AfterClass
    protected void deleteBoard() {
        deleteBoard(board.id());
    }

    @Test(description = "adding new card")
    protected void addNewCard() {
        cardEntity = given()
                .spec(requestSpec)
                .when()
                .queryParam("idList", listEntity.id())
                .queryParam("name", cardName)
                .basePath(Endpoint.CARD)
                .post()
                .then()
                .body("name", equalTo(cardName))
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "updating card")
    protected void updateCard() {
        var cardNew = CardEntity.builder().name(cardName).id(cardEntity.id()).build();

        cardEntity = given()
                .spec(requestSpec)
                .when().basePath(Endpoint.CARD_ID)
                .pathParam("id", cardEntity.id())
                .body(cardNew)
                .put()
                .then()
                .body("name", equalTo(cardName))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "get information about card")
    protected void getCard() {
        cardEntity = given()
                .spec(requestSpec)
                .when().basePath(Endpoint.CARD_ID)
                .pathParam("id", cardEntity.id())
                .get()
                .then()
                .spec(responseSpec)
                .body("name", startsWith(cardName))
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "deletion card")
    protected void deleteCard() {
        given()
                .spec(requestSpec)
                .when().pathParam("id", cardEntity.id())
                .delete(Endpoint.CARD_ID)
                .then()
                .spec(responseSpec);
    }
}

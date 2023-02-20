package com.epam.tc.hw9;

import com.epam.entities.BoardEntity;
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
        params.clear();
        params.put("name", boardName);
        board = sendPostRequest(params, Endpoint.BOARD)
                .then()
                .extract().body().as(BoardEntity.class);
        listEntity = createListOnTheBoard(board.id(), listName);
    }

    @BeforeMethod
    protected void beforeCreateCard() {
        params.clear();
        params.put("idList", listEntity.id());
        params.put("name", cardName);
        cardEntity = sendPostRequest(params, Endpoint.CARD)
                .then()
                .body("name", equalTo(cardName))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @AfterClass
    protected void deleteBoard() {
        sendDeleteRequestAndCheck(board.id(), Endpoint.BOARD_ID);
    }

    @Test(description = "adding new card")
    protected void addNewCard() {
        params.clear();
        params.put("idList", listEntity.id());
        params.put("name", cardName);
        cardEntity = sendPostRequest(params, Endpoint.CARD)
                .then()
                .body("name", equalTo(cardName))
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "updating card")
    protected void updateCard() {
        var cardNew = CardEntity.builder().name(cardName).id(cardEntity.id()).build();
        params.clear();
        params.put("id", cardEntity.id());
        cardEntity = sendPutRequest(params, Endpoint.CARD_ID, cardNew)
                .then()
                .body("name", equalTo(cardName))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "get information about card")
    protected void getCard() {
        params.clear();
        params.put("id", cardEntity.id());
        cardEntity = sendGetRequest(params, Endpoint.CARD_ID)
                .then()
                .spec(responseSpec)
                .body("name", startsWith(cardName))
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "deletion card")
    protected void deleteCard() {
        sendDeleteRequestAndCheck(cardEntity.id(), Endpoint.CARD_ID);
    }
}

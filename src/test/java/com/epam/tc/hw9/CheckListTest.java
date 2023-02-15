package com.epam.tc.hw9;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.epam.entities.BoardEntity;
import com.epam.entities.CardEntity;
import com.epam.entities.CheckListEntity;
import com.epam.entities.ListEntity;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.Endpoint;

public class CheckListTest extends BaseTest {

    CardEntity cardEntity;
    ListEntity listEntity;
    CheckListEntity checkListEntity;
    String checkListName = "created checkList";
    String checkListNameUpdated = "checkListName is updated";
    String listName = "created list";
    String cardName = "cardName";
    String boardName = "board with List";

    @BeforeClass
    protected void createBoardWithCard() {
        params.clear();
        params.put("name", boardName);
        board = sendPostRequest(params, Endpoint.BOARD)
                .then()
                .extract().body().as(BoardEntity.class);
        listEntity = createListOnTheBoard(board.id(), listName);
        cardEntity = createCardInTheList(listEntity.id(), cardName);
    }

    @BeforeMethod
    protected void createCheckList() {
        params.clear();
        params.put("idCard", cardEntity.id());
        params.put("name", checkListName);
        checkListEntity = sendPostRequest(params, Endpoint.CHECKLISTS)
                .then()
                .spec(responseSpec)
                .extract().body().as(CheckListEntity.class);
    }

    @Test(description = "adding new checklist")
    protected void addCheckList() {
        params.clear();
        params.put("idCard", cardEntity.id());
        params.put("name", checkListName);
        checkListEntity = sendPostRequest(params, Endpoint.CHECKLISTS)
                .then()
                .body("name", equalTo(checkListName))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CheckListEntity.class);
    }

    @Test(description = "updating checkList")
    protected void updateCheckList() {
        var checkListNew = CheckListEntity.builder().name(checkListNameUpdated).id(checkListEntity.id()).build();
        params.clear();
        params.put("id", checkListEntity.id());
        cardEntity = sendPutRequest(params, Endpoint.CHECKLISTS_ID, checkListNew)
                .then()
                .body("name", equalTo(checkListNameUpdated))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "deletion checklist")
    protected void deleteCheckList() {
        sendDeleteRequestAndCheck(checkListEntity.id(), Endpoint.CHECKLISTS_ID);

    }

    @AfterClass
    protected void deleteBoard() {
        sendDeleteRequestAndCheck(board.id(), Endpoint.BOARD_ID);
    }
}

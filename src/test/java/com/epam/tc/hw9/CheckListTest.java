package com.epam.tc.hw9;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
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
        board = createBoard(boardName);
        listEntity = createListOnTheBoard(board.id(), listName);
        cardEntity = createCardInTheList(listEntity.id(), cardName);
    }

    @BeforeMethod
    protected void createCheckList() {
        checkListEntity = given()
                .spec(requestSpec)
                .when()
                .queryParam("idCard", cardEntity.id())
                .queryParam("name", checkListName)
                .post(Endpoint.CHECKLISTS)
                .then()
                .spec(responseSpec)
                .extract().body().as(CheckListEntity.class);
    }

    @Test(description = "adding new checklist")
    protected void addCheckList() {
        checkListEntity = given()
                .spec(requestSpec)
                .when()
                .queryParam("idCard", cardEntity.id())
                .queryParam("name", checkListName)
                .post(Endpoint.CHECKLISTS)
                .then()
                .body("name", equalTo(checkListName))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CheckListEntity.class);
    }

    @Test(description = "updating checkList")
    protected void updateCheckList() {
        var checkListNew = CheckListEntity.builder().name(checkListNameUpdated).id(checkListEntity.id()).build();
        cardEntity = given()
                .spec(requestSpec)
                .when().basePath(Endpoint.CHECKLISTS_ID)
                .pathParam("id", checkListEntity.id())
                .body(checkListNew)
                .put()
                .then()
                .body("name", equalTo(checkListNameUpdated))
                .log().all()
                .spec(responseSpec)
                .extract().body().as(CardEntity.class);
    }

    @Test(description = "deletion checklist")
    protected void deleteCheckList() {
        given()
                .spec(requestSpec)
                .when()
                .pathParam("id", checkListEntity.id())
                .delete(Endpoint.CHECKLISTS_ID)
                .then()
                .spec(responseSpec);

    }

    @AfterClass
    protected void deleteBoard() {
        deleteBoard(board.id());
    }
}

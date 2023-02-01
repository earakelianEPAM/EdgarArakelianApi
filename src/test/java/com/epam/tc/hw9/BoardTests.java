package com.epam.tc.hw9;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import com.epam.entities.BoardEntity;
import org.testng.annotations.Test;
import util.Endpoint;

public class BoardTests extends BaseTest {

    @Test(description = "test on creating new board", priority = 1)
    protected void createNewBoard() {
        board = given()
                .spec(requestSpec)
                .when()
                .queryParam("name", boardName)
                .basePath(Endpoint.BOARD)
                .post()
                .then()
                .body("name",equalTo(boardName) )
                .spec(responseSpec)
                .extract().body().as(BoardEntity.class);
    }

    @Test(description = "get info about board", priority = 2)
    protected void getInfoAboutBoard() {
        board = given()
                .spec(requestSpec)
                .when().basePath(Endpoint.BOARD_ID)
                .pathParam("id", board.id())
                .get()
                .then()
                .spec(responseSpec)
                .body("name",equalTo(boardName) )
                .extract().body().as(BoardEntity.class);
    }

    @Test(description = "update board", priority = 3)
    protected void updateBoard() {
        String descNewBoard = "Description for new board";
        var updatedBoardName = BoardEntity.builder().desc(descNewBoard).id(board.id()).build();
        board = given()
                .spec(requestSpec)
                .when().basePath(Endpoint.BOARD_ID)
                .pathParam("id", board.id())
                .body(updatedBoardName)
                .put()
                .then()
                .body("desc",equalTo(descNewBoard))
                .spec(responseSpec)
                .extract().body().as(BoardEntity.class);
    }

    @Test(description = "delete board", priority = 4)
    protected void deleteBoard() {
        given()
                .spec(requestSpec)
                .when().basePath(Endpoint.BOARD_ID)
                .pathParam("id", board.id())
                .delete()
                .then()
                .spec(responseSpec);
    }
}

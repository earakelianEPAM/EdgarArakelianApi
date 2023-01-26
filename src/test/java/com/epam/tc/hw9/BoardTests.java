package com.epam.tc.hw9;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static util.Constants.NEW_BOARD_NAME;

import beans.Board;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import steps.BoardSteps;

public class BoardTests extends BaseTest {

    @Test
    public void boardExistingTest() {
        assertThat(board, allOf(hasProperty("id"), hasProperty("name")));
    }

    @Test
    public void boardDeletionTest() {
        BoardSteps.deleteBoardStep(currentBoardId);
        Response response = BoardSteps.getResponseStep(currentBoardId);
        assertThat(response.body().prettyPrint(), equalTo("The requested resource was not found."));
    }

    @Test
    public void boardNameModifyingTest() {
        Board newBoard = BoardSteps.changeBoardNameStep(currentBoardId, NEW_BOARD_NAME);
        assertThat(newBoard.getName(), equalTo(NEW_BOARD_NAME));
    }
}

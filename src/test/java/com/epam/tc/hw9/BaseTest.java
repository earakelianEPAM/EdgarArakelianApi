package com.epam.tc.hw9;

import beans.Board;
import beans.Lists;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import steps.BoardSteps;
import steps.ListSteps;

public class BaseTest {
    protected Board board;
    protected Lists lists;
    protected String currentBoardId;
    protected String currentListId;

    @BeforeMethod
    public void setUp() {
        board = BoardSteps.createBoardStep();
        currentBoardId = board.getId();
        lists = ListSteps.createListStep(currentBoardId);
        currentListId = lists.getId();
    }

    @AfterMethod
    public void clean() {
        BoardSteps.deleteBoardStep(currentBoardId);
    }
}

package com.epam.tc.hw9;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static util.Constants.LIST_NAME;
import static util.Constants.NEW_LIST_NAME;

import beans.Lists;
import org.testng.annotations.Test;
import steps.ListSteps;

public class ListsTests extends BaseTest {

    @Test
    public void listExistingTest() {
        assertThat(lists, allOf(hasProperty("id"), hasProperty("name")));
    }

    @Test
    public void correctListNameTest() {
        assertThat(lists.getName(), equalTo(LIST_NAME));
    }

    @Test
    public void listNameChangeTest() {
        Lists newList = ListSteps.changeListNameStep(currentListId, NEW_LIST_NAME);
        assertThat(newList.getName(), equalTo(NEW_LIST_NAME));
    }

    @Test
    public void listDeleteTest() {
        Lists newList = ListSteps.deleteListStep(currentListId);
        assertThat(newList.getClosed(), is(true));
    }
}

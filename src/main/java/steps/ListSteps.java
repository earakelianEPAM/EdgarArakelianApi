package steps;

import static service.ServiceObject.getList;
import static service.ServiceObject.requestBuilder;
import static util.Constants.LIST_NAME;
import static util.Constants.LIST_URI;

import beans.Lists;
import io.qameta.allure.Step;
import io.restassured.http.Method;

public class ListSteps {

    @Step("Create List")
    public static Lists createListStep(String idBoard) {
        return getList(
                requestBuilder()
                        .setMethod(Method.POST)
                        .setName(LIST_NAME)
                        .setBoardId(idBoard)
                        .buildRequest()
                        .sendRequest(LIST_URI)
        );
    }

    @Step("Update list")
    public static Lists changeListNameStep(String id, String name) {
        return getList(
                requestBuilder()
                        .setMethod(Method.PUT)
                        .setName(name)
                        .buildRequest()
                        .sendRequest(LIST_URI + id)
        );
    }

    @Step("Delete list by ID")
    public static Lists deleteListStep(String id) {
        return getList(requestBuilder()
                .setMethod(Method.PUT)
                .addQueryParam("value", "true")
                .buildRequest()
                .sendRequest(LIST_URI + id + "/closed/")
        );
    }
}

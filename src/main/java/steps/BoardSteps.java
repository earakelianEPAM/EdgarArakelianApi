package steps;

import static service.ServiceObject.getBoard;
import static service.ServiceObject.requestBuilder;
import static util.Constants.BOARD_NAME;
import static util.Constants.BOARD_URI;

import beans.Board;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class BoardSteps {

    @Step("Create board")
    public static Board createBoardStep() {
        return getBoard(
                requestBuilder()
                        .setMethod(Method.POST)
                        .setName(BOARD_NAME)
                        .addQueryParam("defaultLists", "false")
                        .buildRequest()
                        .sendRequest(BOARD_URI)
        );
    }

    @Step("Get response")
    public static Response getResponseStep(String id) {
        return requestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(BOARD_URI + id);
    }

    @Step("Update board name")
    public static Board changeBoardNameStep(String id, String name) {
        return getBoard(
                requestBuilder()
                        .setMethod(Method.PUT)
                        .setName(name)
                        .buildRequest()
                        .sendRequest(BOARD_URI + id)
        );
    }

    @Step("Delete board by ID")
    public static void deleteBoardStep(String id) {
        requestBuilder()
                .setMethod(Method.DELETE)
                .buildRequest()
                .sendRequest(BOARD_URI + id);
    }
}

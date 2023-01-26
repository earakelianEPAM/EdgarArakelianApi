package service;

import static org.hamcrest.Matchers.lessThan;
import static util.Constants.BASE_URL;
import static util.Constants.KEY;
import static util.Constants.TOKEN;

import beans.Board;
import beans.Lists;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.openqa.selenium.json.TypeToken;

public class ServiceObject {

    private Method requestMethod;
    private Map<String, String> parameters;

    private ServiceObject(Map<String, String> parameters, Method method) {
        this.parameters = parameters;
        this.requestMethod = method;
    }

    public static ApiRequestBuilder requestBuilder() {
        return new ApiRequestBuilder();
    }

    public static class ApiRequestBuilder {
        private Method requestMethod = Method.GET;
        private Map<String, String> parameters = new HashMap<>();

        {
            parameters.put("key", KEY);
            parameters.put("token", TOKEN);
        }

        public ApiRequestBuilder setMethod(Method method) {
            requestMethod = method;
            return this;
        }

        public ApiRequestBuilder setName(String name) {
            parameters.put("name", name);
            return this;
        }

        public ApiRequestBuilder setId(String id) {
            parameters.put("id", id);
            return this;
        }

        public ApiRequestBuilder setBoardId(String id) {
            parameters.put("idBoard", id);
            return this;
        }

        public ServiceObject buildRequest() {
            return new ServiceObject(parameters, requestMethod);
        }

        public ApiRequestBuilder addQueryParam(String paramName, String paramValue) {
            parameters.put(paramName, paramValue);
            return this;
        }
    }

    public Response sendRequest(String uri) {
        return RestAssured
                .given(requestSpecification().log().all())
                .queryParams(parameters)
                .body(parameters)
                .request(requestMethod, uri)
                .prettyPeek();
    }

    public RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    public static ResponseSpecification goodResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(10000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static Board getBoard(Response response) {
        return new Gson()
                .fromJson(response.asString().trim(), new TypeToken<Board>() {
                }.getType());
    }

    public static Lists getList(Response response) {
        return new Gson()
                .fromJson(response.asString().trim(), new TypeToken<Lists>() {
                }.getType());
    }
}

package com.reqres.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testframework.PropertiesManager;

import static io.restassured.RestAssured.given;

public class UsersService extends ReqresApi {

    public UsersService() { super("/users"); }

    private RequestSpecification base() {
        RequestSpecification rs = given()
                .baseUri(getServiceUrl())
                .contentType(ContentType.JSON);

        String apiKey = PropertiesManager.getOrDefault("reqres.apiKey", "");
        if (!apiKey.isBlank()) {
            rs.header("x-api-key", apiKey);
        }
        return rs;
    }

    public ValidatableResponse listUsers(int page) {
        return base()
                .queryParam("page", page)
                .when()
                .get(getServicePath())
                .then()
                .statusCode(200);
    }

    public ValidatableResponse getUser(String id) {
        return base()
                .when()
                .get(getServicePath() + "/" + id)
                .then();
    }

    public ValidatableResponse createUser(Object payload) {
        return base()
                .body(payload)
                .when()
                .post(getServicePath())
                .then();
    }

    public ValidatableResponse deleteUser(String id) {
        return base()
                .when()
                .delete(getServicePath() + "/" + id)
                .then();
    }
}

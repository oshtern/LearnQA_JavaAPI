package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import lib.BaseTestcase;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests extends BaseTestcase {
    @Step("Make a GET-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with auth cookie only")
    public Response makeGetRequestWithCookie(String url, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with token only")
    public Response makeGetRequestWithToken(String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make a POST-request")
    public Response makePostRequest(String url, Map<String, String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST-request with invalid email")
    public Response makePostRequestWithInvalidEmail(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST-request with too short first name")
    public Response makePostRequestWithShortName(String url, Map<String, String> userData) {
        String firstName = "l";
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "l");
        userData.put("lastName", "learnqa");

        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST-request with too long first name")
    public Response makePostRequestWithLongName(String url, Map<String, String> userData) {

        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "You can send requests in Postman to connect to APIs you are working with. Your requests can retrieve, add, delete, and update data. Whether you are building or testing your own API, or integrating with a third-party API, you can send your requests in Postman. Your requests can send parameters, authorization details, and any body data you require.\n" +
                "\n" +
                "For example, if you're building a client application (such as a mobile or web app) for a store, you might send one request to retrieve the list of available products, another request to create a new order (including the selected product details), and a different request to log a customer in to their account.\n" +
                "\n" +
                "When you send a request, Postman displays the response received from the API server in a way that lets you examine, visualize, and if necessary troubleshoot it.Your requests can include multiple details determining the data Postman will send to the API you are working with. Enter a URL and choose a method, then optionally specify a variety of other details.\n" +
                "\n" +
                "You can create a new request from the Postman home screen, by using New > HTTP Request, or by selecting + to open a new tab.");
        userData.put("lastName", "learnqa");

        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST-request without parameter")
    public Response makePostRequestWithoutSingleParameter(String url, Map<String, String> userData) {
        String email = DataGenerator.getRandomEmail();

        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make a GET-request to obtain data of other user")
    public Response makeGetRequestForOtherUser(String url, Map<String, String> userData) {

        userData.put("cookie", "auth_sid");
        userData.put("header", "x-csrf-token");
        userData.put("username", "learnqa");
        userData.put("firstName", "l");
        userData.put("lastName", "learnqa");

        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .get(url)
                .andReturn();
    }

    @Step("Make a PUT-request by unauthorized user")
    public Response makePutRequestByUnauthorizedUser(String url, Map<String, String> editData) {
        String newName = "Changed Name";
        editData.put("firstName", newName);

        return given()
                .filter(new AllureRestAssured())
                .body(editData)
                .put(url)
                .andReturn();
    }

    @BeforeEach
    @Step("Make a PUT-request by the same user to set invalid email")
    public Response makePutRequestToSetInvalidEmail(String url, Map<String, String> editData) {
        String newEmail = "vinkotovexample.com";
        editData.put("email", newEmail);
        editData.put("cookie", "auth_sid");
        editData.put("header", "x-csrf-token");

        String cookie;
        String header;

        return given()
                .filter(new AllureRestAssured())
                .body(editData)
                .put(url)
                .andReturn();
    }
}

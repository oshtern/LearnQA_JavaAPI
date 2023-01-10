package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestcase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import lib.ApiCoreRequests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UserRegisterTest extends BaseTestcase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);
//        userData.put("password", "123");
//        userData.put("username", "learnqa");
//        userData.put("firstName", "learnqa");
//        userData.put("lastName", "learnqa");

        Response responseCreateAuth = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        System.out.println(responseCreateAuth.asString());
        System.out.println(responseCreateAuth.statusCode());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @Test
    @Epic("Authorization cases")
    @Feature("Authorization")
    @Description("This test tries to create a user with invalid email")
    @DisplayName("Test negative auth user")
    public void testCreateUserWithInvalidEmail() {
        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestWithInvalidEmail("https://playground.learnqa.ru/api/user/", userData);

        System.out.println(responseCreateAuth.asString());
        System.out.println(responseCreateAuth.statusCode());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }

    @Test
    @Epic("Authorization cases")
    @Feature("Authorization")
    @Description("This test tries to create a user with too short first name")
    @DisplayName("Test negative auth user")
    public void testCreateUserWithShortName() {
        Map<String, String> userData = new HashMap<>();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestWithShortName("https://playground.learnqa.ru/api/user/", userData);

        System.out.println(responseCreateAuth.asString());
        System.out.println(responseCreateAuth.statusCode());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");
    }

    @Test
    @Epic("Authorization cases")
    @Feature("Authorization")
    @Description("This test tries to create a user with too long first name")
    @DisplayName("Test negative auth user")
    public void testCreateUserWithLongName() {
        Map<String, String> userData = new HashMap<>();


        Response responseCreateAuth = apiCoreRequests
                .makePostRequestWithLongName("https://playground.learnqa.ru/api/user/", userData);

        System.out.println(responseCreateAuth.asString());
        System.out.println(responseCreateAuth.statusCode());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too long");
    }

    @Test
    public void testCreateUserSuccessfully() {
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = DataGenerator.getRegistrationData();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();


        System.out.println(responseCreateAuth.asString());
        System.out.println(responseCreateAuth.statusCode());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        System.out.println(responseCreateAuth.asString());
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }





//    @Test
//    @ParameterizedTest
//    @ValueSource(strings = {"null!123!learnqa!learnqa!learnqa",
//            "vinkotov@example.com!null!learnqa!learnqa!learnqa",
//            "vinkotov@example.com!123!null!learnqa!learnqa",
//            "vinkotov@example.com!123!learnqa!null!learnqa",
//            "vinkotov@example.com!123!learnqa!learnqa!null"})
//    @Epic("Authorization cases")
//    @Feature("Authorization")
//    @Description("This test tries to create a user without email")
//    @DisplayName("Test negative auth user")
//    public void testCreateUserWithoutEmail(String fields) {
//        Map<String, String> userData = new HashMap<>();
//        String[] registrationData = fields.split("!");
//        String email = DataGenerator.getRandomEmail();
//
//        userData.put("email", email);
//        userData.put("password", registrationData[1]);
//        userData.put("username", registrationData[2]);
//        userData.put("firstName", registrationData[3]);
//        userData.put("lastName", registrationData[4]);
//
//
//        Response responseCreateAuth = RestAssured
//                .given()
//                .body(userData)
//                .post("https://playground.learnqa.ru/api/user/")
//                .andReturn();
//
//
//        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
//        Assertions.assertResponseTextEquals(responseCreateAuth, "The following required params are missed: email");
//    }



    void testWithMethodSource(String argument1, String argument2, String argument3) {

    }

//    static Stream<Arguments> argsForUserCreation() {
//        String email = DataGenerator.getRandomEmail();
//        return Stream.of(arguments(null, "123", "learnqa", "learnqa", "learnqa", "email"),
//        arguments(email, null, "learnqa", "learnqa", "learnqa", "password"),
//        arguments(email, "123", null, "learnqa", "learnqa", "username"),
//        arguments(email, "123", "learnqa", null, "learnqa", "firstName"),
//        arguments(email, "123", "learnqa", "learnqa", null, "lastName"));
//    }
//
//    @ParameterizedTest
//    @MethodSource("argsForUserCreation")
//    public void testCreateUserWithoutSomeParameter(String email, String password, String username, String firstName, String lastName, String key) {
//
//        Map<String, String> userData = new HashMap<>();
//
//        userData.put("email", email);
//        userData.put("password", password);
//        userData.put("username", username);
//        userData.put("firstName", firstName);
//        userData.put("lastName", lastName);
//
//        Response responseCreateAuth = RestAssured
//                .given()
//                .body(userData)
//                .log().uri()
//                .log().body()
//                .post("https://playground.learnqa.ru/api/user/")
//                .andReturn();
//
//        responseCreateAuth.prettyPrint();
//
//        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
//        assertTrue(responseCreateAuth.statusCode() == 400, "The following required params are missed: " + key);
//    }
static Stream<Arguments> argsForUserCreation() {
    String email = DataGenerator.getRandomEmail();
    return Stream.of(arguments(null, "123", "learnqa", "learnqa", "learnqa", "email"),
            arguments(email, null, "learnqa", "learnqa", "learnqa", "password"),
            arguments(email, "123", null, "learnqa", "learnqa", "username"),
            arguments(email, "123", "learnqa", null, "learnqa", "firstName"),
            arguments(email, "123", "learnqa", "learnqa", null, "lastName"));
}
    @ParameterizedTest
    @MethodSource("argsForUserCreation")
    public void testCreateUserWithoutSomeParameter(String email, String password, String username, String firstName, String lastName, String key) {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password);
        userData.put("username", username);
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestWithoutParameter("https://playground.learnqa.ru/api/user/", userData);

        responseCreateAuth.prettyPrint();

        assertTrue(responseCreateAuth.statusCode() == 400, "The following required params are missed: " + key);
    }

}

package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDeleteTest {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void deleteUserWithId2() {

        //Login with user with id=2
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseAuth = given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        responseAuth.prettyPrint();

        //Get token and cookie of this logged-in user
        String cookie = responseAuth.getCookie("auth_sid");
        String token = responseAuth.getHeader("x-csrf-token");
        System.out.println(cookie);
        System.out.println(token);


        //Try to delete user with id=2
        int userToBeDeletedId = 2;
        Response responseDelete = given()
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .delete("https://playground.learnqa.ru/api/user/" + userToBeDeletedId)
                .andReturn();
        responseDelete.prettyPrint();

        assertTrue(responseDelete.statusCode() == 400, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
    }

    @Test
    public void deleteUserThemself() {
        //Generate user
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateUser = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateUser.getString("id");

        //Login with this user
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseAuth = given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        responseAuth.prettyPrint();

        //Get token and cookie of logged-in user
        String cookie = responseAuth.getCookie("auth_sid");
        String token = responseAuth.getHeader("x-csrf-token");
        System.out.println(cookie);
        System.out.println(token);


        //Delete this user by himself

        Response responseDelete = given()
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        responseDelete.prettyPrint();

        //Get info on deleted user by id

        Response getDataById = given()
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        getDataById.prettyPrint();

        System.out.println(getDataById.statusCode());
        assertTrue(getDataById.statusCode() == 404, "User not found");

    }

    @Test
    public void deleteUserByOtherUser() {
        //Generate user #1
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateUser = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        responseCreateUser.prettyPrint();
        int user1Id = responseCreateUser.getInt("id");
        System.out.println(user1Id);

        //Generate user #2
        String email = DataGenerator.getRandomEmail();

        Map<String, String> user2Data = DataGenerator.getRegistrationData();

        user2Data.put("email", email);
        user2Data.put("password", "123");
        user2Data.put("username", "learnqa");
        user2Data.put("firstName", "learnqa");
        user2Data.put("lastName", "learnqa");

        JsonPath responseCreateUser2 = given()
                .body(user2Data)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        responseCreateUser2.prettyPrint();
        int user2Id = responseCreateUser2.getInt("id");
        System.out.println(user2Id);

        //Login as user #2
        Map<String, String> authData = new HashMap<>();

        authData.put("email", user2Data.get("email"));
        authData.put("password", user2Data.get("password"));

        Response responseAuth = given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();


        //Get token and cookie of user#2
        String cookie = responseAuth.getCookie("auth_sid");
        String token = responseAuth.getHeader("x-csrf-token");
        System.out.println(cookie);
        System.out.println(token);


        //Delete user#1 by user#2

        Response responseDelete = given()
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .delete("https://playground.learnqa.ru/api/user/" + user1Id)
                .andReturn();
        responseDelete.prettyPrint();

        //Get info on deleted user by id

        Response getDataById = given()
                .get("https://playground.learnqa.ru/api/user/" + user1Id)
                .andReturn();

        getDataById.prettyPrint();

        System.out.println(responseDelete.getStatusCode());

        assertTrue(responseDelete.statusCode() == 400, "User can't be deleted");
    }

}

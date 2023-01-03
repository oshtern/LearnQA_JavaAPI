import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasValue;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex11 {

    @Test
    public void requestCookieMethod() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .prettyPeek()
                .andReturn();

        response.prettyPrint();
        System.out.println("\nCookies:");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        String homeWork = response.getCookie("HomeWork");
        System.out.println(homeWork);

        assertTrue(homeWork.contentEquals("hw_value"), "Unexpected value. Actual one is: " + homeWork);
    }
}

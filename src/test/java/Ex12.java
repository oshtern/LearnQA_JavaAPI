import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasValue;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex12 {
    @Test
    public void requestHeaderMethod() {
        Map<String, String> headers = new HashMap<>();
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .prettyPeek()
                .andReturn();

        response.prettyPrint();
        System.out.println("\nHeaders:");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        String secretHeader = response.getHeader("x-secret-homework-header");
        System.out.println(secretHeader);

        assertTrue(secretHeader.contentEquals("Some secret value"), "Unexpected value. Actual is: " + secretHeader);
    }
}

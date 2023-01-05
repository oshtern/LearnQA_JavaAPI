import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex13 {
    @ParameterizedTest
    @ValueSource(strings = {"Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
            "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
            "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"})
    public void receiveInfoOnUser(String user_agent) {
        Map<String, String> queryParams = new HashMap<>();

            queryParams.put("user_agent", user_agent);

        JsonPath response = RestAssured
                .given()
                .queryParams(queryParams)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();


        System.out.println(response);

        String actualPlatform = response.getString("platform");
        System.out.println(actualPlatform);
        String expectedPlatform = (user_agent.length() > 0) ? user_agent : "empty platform";
        System.out.println(expectedPlatform);

        String actualBrowser = response.getString("browser");
        System.out.println(actualBrowser);
        String expectedBrowser = (user_agent.length() > 0) ? user_agent : "empty browser";
        System.out.println(expectedPlatform);

        String actualDevice = response.getString("device");
        System.out.println(actualDevice);
        String expectedDevice = (user_agent.length() > 0) ? user_agent : "empty device";
        System.out.println(expectedDevice);

        assertEquals("Platform is: " + expectedPlatform, actualPlatform, "Unexpected platform");
        assertEquals("Browser is: " + expectedBrowser, actualBrowser, "Unexpected browser");
        assertEquals("Device is: " + expectedDevice, actualDevice, "Unexpected device");



    }
}

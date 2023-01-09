import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Ex13 {
    @ParameterizedTest
//    @ValueSource(strings = {"Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
//            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
//            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
//            "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"})

    @CsvSource({"Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30! Mobile! No! Android",
            "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1! Mobile! Chrome! iOS",
            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)! Googlebot! Unknown! Unknown",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0! Web! Chrome! No",
            "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1! Mobile! No! iPhone"})
    public void receiveInfoOnUser(String user_agent) {
        String[] expectedValues = user_agent.split("! ");
        Map<String, String> headers = new HashMap<>();

        headers.put("user-agent", user_agent);

        JsonPath response = RestAssured
                .given()
                .headers(headers)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        System.out.println(response);

        String platform = response.get("platform");
        System.out.println(platform);
        String browser = response.get("browser");
        System.out.println(browser);
        String device = response.get("device");
        System.out.println(device);

        System.out.println(expectedValues[0]);

//        assertEquals(expectedValues, platform, "Unexpected platform. Actual is " + platform);

//        String[] expectedPlatforms = {"Mobile", "Mobile", "Googlebot", "Web", "Mobile"};

//        for (int i = 0; i < expectedPlatforms.length; i++) {
//            assertTrue(expectedPlatforms[i] == platform, "Unexpected platform. Actual is: " + platform);
//            System.out.println(expectedPlatforms[i]);
//        }
//
//        String[] actualPlatforms = new String[] {};
//        int i = 0;
//        for (String expectedPlatform : expectedPlatforms) {
//            assertEquals(expectedPlatforms[i], platform, "Unexpected platform. Actual is " + platform);
//        }


    }

}

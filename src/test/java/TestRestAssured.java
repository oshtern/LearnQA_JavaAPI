import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.http.Headers;
import java.time.LocalTime;
import java.util.Timer;
import java.util.Date;
import java.time.Clock;

import static java.lang.Thread.sleep;


public class TestRestAssured {

    @Test
    public void testRestAssured1() {
        int i = 5;
        int j = 3;
        System.out.println(i+j);


        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();

        String token = response.get("token");
        System.out.println(token);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        System.out.println(params.put("token", token));

        Response response1 = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        response1.prettyPrint();

    }
}

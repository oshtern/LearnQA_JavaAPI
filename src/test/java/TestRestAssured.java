import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
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

//    @Test
//    public void testRestAssured1() {
//        int i = 5;
//        int j = 3;
//        System.out.println(i+j);
//
//
//        JsonPath response = RestAssured
//                .given()
//                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
//                .jsonPath();
//        response.prettyPrint();
//
//        String token = response.get("token");
//        System.out.println(token);
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        Map<String, String> params = new HashMap<>();
//        params.put("token", token);
//        System.out.println(params.put("token", token));
//
//        Response response1 = RestAssured
//                .given()
//                .queryParams(params)
//                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
//                .andReturn();
//
//        response1.prettyPrint();
//
//    }


    @SneakyThrows
    @Test
    public void testRestAssured2() {
        PojoToken pojoToken = RestAssured.given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .then()
                .statusCode(200)
                .log().body()
                .extract().body().as(PojoToken.class);

        String token = pojoToken.getToken();
        Integer seconds = pojoToken.getSeconds();

        PojoStatus pojostatus = RestAssured.given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .then()
                .statusCode(200)
                .log().body()
                .extract().body().as(PojoStatus.class);

        Assertions.assertEquals(pojostatus.getStatus(), "Job is NOT ready");


        Thread.sleep(seconds * 1000);


        pojostatus = RestAssured.given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .then()
                .statusCode(200)
                .log().body()
                .extract().body().as(PojoStatus.class);

        Assertions.assertEquals(pojostatus.getStatus(), "Job is ready");
        Assertions.assertEquals(pojostatus.getResult(), "42");
    }
}

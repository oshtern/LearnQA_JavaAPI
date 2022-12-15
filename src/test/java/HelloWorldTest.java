import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.http.Headers;

public class HelloWorldTest {

    @Test
    public void testRestAssured() {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("myHeader1", "myValue1");
//        headers.put("myHeader2", "myValue2");

        String resource = "https://playground.learnqa.ru/api/long_redirect";
        Response code = RestAssured.get(resource);
        int counter = 0;
        int statusCode = code.getStatusCode();

        Response response1 = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get(resource)
                .andReturn();
        String locationHeader1 = response1.getHeader("Location");
        System.out.println(locationHeader1);


        do {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(resource);
//                    .andReturn();

            counter += 1;
            statusCode = response.getStatusCode();
//            response.prettyPrint();
            String locationHeader = response.getHeader("Location");
            System.out.println(locationHeader);
            System.out.println(statusCode);
            resource = locationHeader;
            System.out.println(counter);
        }
        while (statusCode != 200);

    }

}


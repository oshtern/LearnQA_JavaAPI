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

        for (int i = 0; i < 50; i++) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(true)
                    .when()
                    .get(resource)
                    .andReturn();

            counter = counter + 1;
//            response.prettyPrint();
            String locationHeader = response.getHeader("Location");
            System.out.println(locationHeader);
            resource = locationHeader;
            int statusCode = code.getStatusCode();
            System.out.println(statusCode);
            System.out.println(counter);

        if (statusCode == 200) {
            break;
        }
        }
    }
}

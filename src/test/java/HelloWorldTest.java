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

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        response.prettyPrint();
        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }

}

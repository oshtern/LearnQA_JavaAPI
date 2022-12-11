import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import io.restassured.path.json.JsonPath;

public class HelloWorldTest {

    @Test
    public void testRestAssured() {
        Map<String,String> params = new HashMap<>();
        params.put("message", "And this is a second message");
        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        response.prettyPrint();
        String message = response.get("message");
        if (message == null){
            System.out.println("The key 'message' is absent");
        } else {
            System.out.println(message);
        }
    }

}

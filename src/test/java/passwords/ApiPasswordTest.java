package passwords;

import lombok.var;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiPasswordTest {
    private final Method method = new Method();

    @BeforeAll
    public static void installSpec() {
        SpecConfigurator.installSpecification200();

    }

    @Test
    public void checkPassword() {
        var passwords = method.getPasswordListFromWiki();

        for (var password : passwords) {
            var cookie = method.getSecretPasswordHomework(password);
            var body = method.checkAuthCookie(cookie);

            if (body.contains("You are authorized")) {
                System.out.println("Password is found: " + password);
                System.out.println(password);
                break;
            }
        }

    }


}

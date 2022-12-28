package passwords;

import io.restassured.path.xml.XmlPath;
import lombok.var;

import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.XmlPath.CompatibilityMode.HTML;

public class Method {
    public String getSecretPasswordHomework(String password) {

        var body2 = PojoPassword.builder().login("super_admin").password(password).build();

        return given()
                .body(body2)
                .post(Endpoint.Post.getSecretPasswordHomework())
                .then()
                .extract().cookie("auth_cookie");


    }

    public String checkAuthCookie(String cookie) {
        return given()
                .cookie("auth_cookie", cookie)
                .get(Endpoint.Get.checkAuthCookie())
                .then()
                .extract().response().htmlPath().getString("body");
    }

    public List<String> getPasswordListFromWiki() {
        var response = given()
                .get(Endpoint.Get.wikiPage())
                .then()
                .extract().response();

        XmlPath htmlPath = new XmlPath(HTML, response.getBody().asString());
        List<String> passwords = new ArrayList<>();

        int r = 1;
        int c = 1;
        String path = "html.body.div[2].div[2].div[4].div[0].table[2].tbody.";

        for (int i = 0; i < 224; i++) {
            if (c == 10) {
                c = 1;
                r++;
            }
            passwords.add(i, htmlPath.getString(path + "tr[" + r + "].td[" + c + "]").replaceAll("\n", ""));
            c++;
        }

        Set<String> set = new HashSet<>(passwords);
        passwords.clear();
        passwords.addAll(set);

        return passwords;
    }

}

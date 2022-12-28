package passwords;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class SpecConfigurator {

    private static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://playground.learnqa.ru/ajax/api")
                .setContentType(JSON)
                .build()
                .filters(
                        new RequestLoggingFilter(URI),
                        new RequestLoggingFilter(COOKIES),
                        new RequestLoggingFilter(BODY),
                        new ResponseLoggingFilter(BODY)
                );
    }

    private static ResponseSpecification responseSpec200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(
                        anyOf(
                                is(200),
                                is(201)
                        ))
                .build();
    }

    private static void installSpec(RequestSpecification requestSpec, ResponseSpecification responseSpec) {
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    public static void installSpecification200() {
        installSpec(requestSpec(), responseSpec200());
    }

}
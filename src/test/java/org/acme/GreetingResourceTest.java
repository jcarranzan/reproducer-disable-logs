package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    private static final Logger LOGGER = Logger.getLogger(GreetingResourceTest.class);

    @Test
    void testHelloEndpoint() {
            LOGGER.info("Creating Quarkus app for testing testHelloEndpoint");
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST"));
    }

    @Test
    public void testHelloInfinispanEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Infinispan Client\",\"message\":\"Hello World, Infinispan is up!\"}")
                .when()
                .post("/greeting/quarkus")
                .then()
                .statusCode(200);

        given()
                .when().get("/greeting/quarkus")
                .then()
                .statusCode(200)
                .body(is("{\"name\":\"Infinispan Client\",\"message\":\"Hello World, Infinispan is up!\"}"));
    }

}
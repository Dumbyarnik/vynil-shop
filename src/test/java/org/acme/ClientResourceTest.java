package org.acme;

import org.junit.jupiter.api.Test;

import control.DTO.CreateClientDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ClientResourceTest {

    @Test
    public void testGetClients() {
        given()
            .when().get("/client")
            .then()
                .statusCode(200);
    }

    @Test
    public void testCreateClient() {
        CreateClientDTO createClientDTO = new CreateClientDTO();
        createClientDTO.username = "frank";
        createClientDTO.password = "frank";

        given()
            .contentType("application/json")
            .body(createClientDTO)
            .when().post("/client")
            .then()
                .statusCode(200);

        given()
            .contentType("application/json")
            .body(createClientDTO)
            .when().post("/client")
            .then()
                .statusCode(406);
    }

    @Test
    public void testGetClient() {
        given()
            .when().get("/client/3")
            .then()
                .statusCode(200);

        given()
            .when().get("/client/20")
            .then()
                .statusCode(406);
    }

    @Test
    public void testDeleteClient() {
        given()
            .when().delete("/client/3")
            .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Client"})
    public void testDeleteClientAsUser() {
        given()
            .when().delete("/client/3")
            .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void testDeleteClientAsAdmin() {
        given()
            .when().delete("/client/3")
            .then()
                .statusCode(200);

        given()
            .when().get("/client/6")
            .then()
            .statusCode(406);
    }

}

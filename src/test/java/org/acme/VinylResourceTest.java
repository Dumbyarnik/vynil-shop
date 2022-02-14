/*
    @author: Daniil Vorobyev
*/
package org.acme;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import control.DTO.CreateVinylDTO;
import io.quarkus.test.security.TestSecurity;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class VinylResourceTest {
    @Test
    public void testGetVinyls() {
        given()
            .when().get("/vinyl")
            .then()
                .statusCode(200);
    }

    @Test
    public void testCreateVinyl() {
        CreateVinylDTO createVinylDTO = new CreateVinylDTO();
        createVinylDTO.title = "Good Vinyl";
        createVinylDTO.description = "Interesting";
        createVinylDTO.artist = "Good artist";
        createVinylDTO.genre = "ELECTRO";
        createVinylDTO.price = 12L;

        given()
            .contentType("application/json")
            .body(createVinylDTO)
            .when().post("/vinyl")
            .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "ringo", roles = {"Client"})
    public void testCreateVinylAsUser() {
        CreateVinylDTO createVinylDTO = new CreateVinylDTO();
        createVinylDTO.title = "Good Vinyl";
        createVinylDTO.description = "Interesting";
        createVinylDTO.artist = "Good artist";
        createVinylDTO.genre = "ELECTRO";
        createVinylDTO.price = 12L;

        given()
            .contentType("application/json")
            .body(createVinylDTO)
            .when().post("/vinyl")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "ringo", roles = {"Client"})
    public void testDeleteVinylAsUser() {
        given()
            .when().delete("/vinyl/1")
            .then()
                .statusCode(200);

        given()
            .when().delete("/vinyl/1")
            .then()
                .statusCode(406);
    }

}

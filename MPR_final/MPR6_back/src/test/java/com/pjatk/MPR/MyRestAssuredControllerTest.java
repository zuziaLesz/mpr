package com.pjatk.MPR;

import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyRestAssuredControllerTest {
    private static final String URI = "http://localhost:8080";

    @Test
    public void testGetCatById() {
         Cat cat = when()
                .get(URI + "/cats/3")
                .then()
                .statusCode(200)
                 .extract()
                 .as(Cat.class);

         assertEquals(3, cat.getId());
    }

    @Test
    public void testSendCat() {
        with()
                .body(new Cat(10,"Robert"))
                .contentType("application/json")
                .when()
                .post(URI + "/cats")
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("Robert"))
                .log()
                .body();

    }

    @Test
    public void testDeleteCat() {

        when()
                .delete(URI + "/cats/2")
                .then()
                .statusCode(200);
        when()
                .get(URI + "/cats/2")
                .then()
                .statusCode(404);

    }

    @Test
    public void EditCatNameTest() {
        with()
                .body(new Cat(1,"Beata Szydlo"))
                .contentType("application/json")
                .when()
                .put(URI + "/cats/4")
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("Beata Szydlo"));
    }
}

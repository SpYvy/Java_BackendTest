package ru.gb.homework3;

import io.restassured.path.json.JsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class HW2PostmanCasesTest extends AbstractClass {

    @Test
    void dishesWithLemonAndPineapple() {
        JsonPath resultPath = given() // Ответ на запрос
                .queryParam("includeIngredients", "lemon")
                .queryParam("includeIngredients", "pineapple")
                .queryParam("apiKey", getApiKey())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        String[] dishes = new String[10];

        for (int i = 0; i <= 9; i++) {
            dishes[i] = resultPath.get("results[" + i + "].title");
            System.out.println(dishes[i]);
        }

        for (int i = 0; i < dishes.length; i++) {
            JsonPath queryResultPath = given() // Ответ на запрос
                    .queryParam("query", dishes[i])
                    .queryParam("apiKey", getApiKey())
                    .when()
                    .get(getBaseUrl() + "recipes/complexSearch")
                    .then()
                    .statusCode(200)
                    .extract()
                    .jsonPath();
            assertThat(queryResultPath.get("results[0].title"), equalTo(dishes[i]));
            System.out.println("Результат: " + dishes[i] + " равен запросу " + queryResultPath.get("results[0].title") + ".");
        }
    }

    String[] americanCuisineQueries = new String[]{"burger", "french fries", "apple pie"};

    @Test
    void americanCuisineTest() {
        for (int i = 0; i < americanCuisineQueries.length; i++) {
            JsonPath americanCuisineResultPath = given()
                    .queryParam("apiKey", getApiKey())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("title", americanCuisineQueries[i])
                    .when()
                    .post(getBaseUrl() + "recipes/cuisine")
                    .then().statusCode(200)
                    .extract()
                    .jsonPath();
            assertThat("Данное блюдо не принадлежит американской кухне", americanCuisineResultPath.get("cuisine"), equalTo("American"));
            System.out.println("Блюдо: " + americanCuisineQueries[i] + " действительно принадлежит американской кухне.");
        }
    }

    String[] japaneseCuisineQueries = new String[]{"sushi"};
    @Test
    void japaneseCuisineTest() {
        JsonPath japaneseCuisineResultPath = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", japaneseCuisineQueries[0])
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then().statusCode(200)
                .extract()
                .jsonPath();
        assertThat("Данное блюдо не принадлежит японской кухне", japaneseCuisineResultPath.get("cuisine"), equalTo("Japanese"));
        System.out.println("Блюдо: " + japaneseCuisineQueries[0] + " действительно принадлежит японской кухне.");
    }
    String[] mexicanCuisineQueries = new String[]{"taco"};
    @Test
    void mexicanCuisineTest() {
        JsonPath mexicanCuisineResultPath = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", mexicanCuisineQueries[0])
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then().statusCode(200)
                .extract()
                .jsonPath();
        assertThat("Данное блюдо не принадлежит мексиканской кухне", mexicanCuisineResultPath.get("cuisine"), equalTo("Mexican"));
        System.out.println("Блюдо: " + mexicanCuisineQueries[0] + " действительно принадлежит мексиканской кухне.");
    }

    String[] frenchCuisineQueries = new String[]{"ratatouille"};
    @Test
    void frenchCuisineTest() {
        JsonPath frenchCuisineResultPath = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", frenchCuisineQueries[0])
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then().statusCode(200)
                .extract()
                .jsonPath();
        assertThat("Данное блюдо не принадлежит французской кухне", frenchCuisineResultPath.get("cuisines[2]"), equalTo("French"));
        System.out.println("Блюдо: " + frenchCuisineQueries[0] + " действительно принадлежит французской кухне.");
    }

    String[] italianCuisineQueries = new String[]{"pizza"};
    @Test
    void italianCuisineTest() {
        JsonPath italianCuisineResultPath = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", italianCuisineQueries[0])
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then().statusCode(200)
                .extract()
                .jsonPath();
        assertThat("Данное блюдо не принадлежит итальянской кухне", italianCuisineResultPath.get("cuisines[2]"), equalTo("Italian"));
        System.out.println("Блюдо: " + italianCuisineQueries[0] + " действительно принадлежит итальянской кухне.");
    }
}

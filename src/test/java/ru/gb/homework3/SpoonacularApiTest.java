package ru.gb.homework3;

import com.github.javafaker.Faker;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SpoonacularApiTest extends AbstractClass {
    Faker faker = new Faker(); //Библиотека для генерации случайных тестовых данных

    @Test
    void createShoppingListChainTest() {
        JsonPath userInfoResponse = given() //Создание пользователя через Connect User
                .queryParam("apiKey", getApiKey())
                .contentType("application/json")
                .body("{\n" +
                        "  \"username\": \"" + faker.name().username() + "\",\n" +
                        "  \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "  \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "  \"email\": \"" + faker.internet().emailAddress() + "\"\n" +
                        "}")
                .expect().statusCode(200)
                .when()
                .post(getBaseUrl() + "users/connect")
                .body()
                .jsonPath();

        String username = userInfoResponse.getString("username"); // Запоминаю username для дальнейшего использования
        String spoonacularPassword = userInfoResponse.getString("spoonacularPassword");
        String hash = userInfoResponse.getString("hash"); // Запоминаю hash для дальнейшего использования

        given() //Создание Shopping list
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/{start-date}/{end-date}",
                        username, "2022-12-20", "2022-12-27") //Endpoints запроса
                .then()
                .statusCode(200);

        JsonPath shoppingListItemResponse = given() //Добавление item в Shopping list
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .body("{\n" +
                        "  \"item\": \"1 package baking powder\",\n" +
                        "  \"aisle\": \"Baking\",\n" +
                        "  \"parse\": true\n" +
                        "}")
                .expect()
                .statusCode(200)
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/items",
                        username)
                .body()
                .jsonPath();
        String shoppingItemId = shoppingListItemResponse.getString("id"); // Запоминаю id item для дальнейшего использования

        given() //Получение содержимого Shopping list
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list",
                        username)
                .body()
                .prettyPrint();

        JsonPath shoppingListItemDeleteResponse = given() //Удаление item из Shopping list
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .when()
                .delete(getBaseUrl() + "mealplanner/{username}/shopping-list/items/{id}",
                        username, shoppingItemId)
                .body()
                .jsonPath();
        assertThat(shoppingListItemDeleteResponse.get("status"), equalTo("success"));

        given() // Проверка, что item удален
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list",
                        username)
                .body()
                .prettyPrint();
    }
        @Test
        void getExampleTest() { //Тест для примера
            given()
                    .when()
                    .get(getBaseUrl() + "recipes/716429/information?" +
                            "includeNutrition=false&apiKey=" + getApiKey())
                    .then()
                    .statusCode(200);

            given()
                    .when() //Еще один способ записи
                    .request(Method.GET, getBaseUrl() + "recipes/716429/information?" +
                            "includeNutrion={Nutrition}&apiKey={apiKey}", false, getApiKey())//Воракс: Два параметра подставятся в {} вместо Nutrition и apiKey
                    .then()
                    .statusCode(200);
        }
}
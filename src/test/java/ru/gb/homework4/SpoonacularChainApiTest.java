package ru.gb.homework4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.gb.homework4.builder.ConnectUserRequestBuilder;
import ru.gb.homework4.builder.AddShoppingItemRequestBuilder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SpoonacularChainApiTest extends AbstractClass {
    Faker faker = new Faker(); //Библиотека для генерации случайных тестовых данных
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    void createShoppingListChainTest() throws JsonProcessingException {
        ConnectUserRequest connectUserRequestData = new ConnectUserRequestBuilder() //Тестовые данные в объекте для запроса
                .setUsername(faker.name().username())
                .setFirstName(faker.name().firstName())
                .setLastName(faker.name().lastName())
                .setEmail(faker.internet().emailAddress())
                .createConnectUserRequest();

        ConnectUserResponse userInfoResponse = given() //Создание пользователя через Connect User
                .spec(getRequestSpecification()) //apiKey, contentType, и т.д.
                .body(objectWriter.writeValueAsString(connectUserRequestData)) // ObjectWriter преобразует объект в JSON
                .when()
                .post(getBaseUrl() + "users/connect")
                .then()
                .spec(getResponseSpecification()) //statusCode и statusLine, contentType, и т.д.
                .extract()
                .body()
                .as(ConnectUserResponse.class); //Создание объекта класса ConnectUserResponse для дальнейшего обращения к полям

        given() //Создание Shopping list
                .spec(getRequestSpecification())
                .queryParam("hash", userInfoResponse.getHash())
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/{start-date}/{end-date}",
                        userInfoResponse.getUsername(), "2022-12-20", "2022-12-27") //Endpoints запроса
                .then()
                .spec(getResponseSpecification());

        AddShoppingItemRequest addShoppingItemRequestData = new AddShoppingItemRequestBuilder() //Тестовые данные в объекте для запроса
                .setItem("1 package baking powder")
                .setAisle("Baking")
                .setParse(true)
                .createAddShoppingItemRequest();

        AddShoppingItemResponse addShoppingItemResponse = given() //Добавление item в Shopping list
                .spec(getRequestSpecification())
                .queryParam("hash", userInfoResponse.getHash())
                .body(objectWriter.writeValueAsString(addShoppingItemRequestData)) // ObjectWriter преобразует объект в JSON
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/items",
                        userInfoResponse.getUsername())
                .then()
                .spec(getResponseSpecification())
                .extract()
                .body()
                .as(AddShoppingItemResponse.class);

        given() //Получение содержимого Shopping list
                .spec(getRequestSpecification())
                .queryParam("hash", userInfoResponse.getHash())
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list",
                        userInfoResponse.getUsername())
                .body()
                .prettyPrint();

        DeleteItemStatus deleteItemStatus = given() //Удаление item из Shopping list
                .spec(getRequestSpecification())
                .queryParam("hash", userInfoResponse.getHash())
                .when()
                .delete(getBaseUrl() + "mealplanner/{username}/shopping-list/items/{id}",
                        userInfoResponse.getUsername(), addShoppingItemResponse.getId())
                .then()
                .spec(getResponseSpecification())
                .extract()
                .body()
                .as(DeleteItemStatus.class);
        assertThat(deleteItemStatus.getStatus(), equalTo("success"));

        given() // Проверка, что item удален
                .spec(getRequestSpecification())
                .queryParam("hash", userInfoResponse.getHash())
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list",
                        userInfoResponse.username)
                .body()
                .prettyPrint();
    }
}

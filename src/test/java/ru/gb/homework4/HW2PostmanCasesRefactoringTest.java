package ru.gb.homework4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class HW2PostmanCasesRefactoringTest extends AbstractClass {

    public boolean cuisineMatch(List responseCuisines, String queryCuisine) {
        boolean isCuisineMatch = false;
        for (Object responseCuisine : responseCuisines) {
            if (responseCuisine.toString().equalsIgnoreCase(queryCuisine)) {
                isCuisineMatch = true;
                break;
            }
        }
        return isCuisineMatch;
    }

    @Test
    void dishesWithLemonAndPineapple() {
        DishesQueryResponse dishesQueryResponse = given()
                .spec(getRequestSpecification())
                .queryParam("includeIngredients", "lemon")
                .queryParam("includeIngredients", "pineapple")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .body()
                .as(DishesQueryResponse.class);

        String[] dishes = new String[dishesQueryResponse.getResults().size()]; // Создаем массив для хранения названий блюд
        for (int i = 0; i < dishesQueryResponse.getResults().size(); i++) {
            dishes[i] = dishesQueryResponse.getResults().get(i).getTitle();
        }

        for (int j = 0; j < dishes.length; j++) {
            DishesQueryResponse dishesQueryResponseAssert = given()
                    .spec(getRequestSpecification())
                    .queryParam("query", dishes[j])
                    .when()
                    .get(getBaseUrl() + "recipes/complexSearch")
                    .then()
                    .spec(getResponseSpecification())
                    .extract()
                    .body()
                    .as(DishesQueryResponse.class);
            assertThat(dishesQueryResponseAssert.results.get(0).getTitle(), equalTo(dishes[j]));
        }
    }

    String[] americanCuisineQueries = new String[]{"burger", "french fries", "apple pie"};
    @Test
    void americanCuisineTest() {
        for (int i = 0; i < americanCuisineQueries.length; i++) {
            CuisineResponse cuisineResponse = given()
                    .spec(getRequestSpecification())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("title", americanCuisineQueries[i])
                    .when()
                    .post(getBaseUrl() + "recipes/cuisine")
                    .then()
                    .spec(getResponseSpecification())
                    .extract()
                    .body()
                    .as(CuisineResponse.class);
            assertThat("Данное блюдо не принадлежит американской кухне",
                    cuisineMatch(cuisineResponse.cuisines, "american"), equalTo(true));
            System.out.println("Блюдо " + americanCuisineQueries[i] + " действительно относится к американской кухне");
        }
    }

    String[] japaneseCuisineQueries = new String[]{"sushi"};
    @Test
    void japaneseCuisineTest() {
        for (int i = 0; i < japaneseCuisineQueries.length; i++) {
            CuisineResponse cuisineResponse = given()
                    .spec(getRequestSpecification())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("title", japaneseCuisineQueries[i])
                    .when()
                    .post(getBaseUrl() + "recipes/cuisine")
                    .then()
                    .spec(getResponseSpecification())
                    .extract()
                    .body()
                    .as(CuisineResponse.class);
            assertThat("Данное блюдо не принадлежит японской кухне",
                    cuisineMatch(cuisineResponse.cuisines, "japanese"), equalTo(true));
            System.out.println("Блюдо " + japaneseCuisineQueries[i] + " действительно относится к японской кухне");
        }
    }

    String[] mexicanCuisineQueries = new String[]{"taco"};
    @Test
    void mexicanCuisineTest() {
        for (int i = 0; i < mexicanCuisineQueries.length; i++) {
            CuisineResponse cuisineResponse = given()
                    .spec(getRequestSpecification())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("title", mexicanCuisineQueries[i])
                    .when()
                    .post(getBaseUrl() + "recipes/cuisine")
                    .then()
                    .spec(getResponseSpecification())
                    .extract()
                    .body()
                    .as(CuisineResponse.class);
            assertThat("Данное блюдо не принадлежит мексиканской кухне",
                    cuisineMatch(cuisineResponse.cuisines, "mexican"), equalTo(true));
            System.out.println("Блюдо " + mexicanCuisineQueries[i] + " действительно относится к мексиканской кухне");
        }
    }

    String[] frenchCuisineQueries = new String[]{"ratatouille"};
    @Test
    void frenchCuisineTest() {
        for (int i = 0; i < frenchCuisineQueries.length; i++) {
            CuisineResponse cuisineResponse = given()
                    .spec(getRequestSpecification())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("title", frenchCuisineQueries[i])
                    .when()
                    .post(getBaseUrl() + "recipes/cuisine")
                    .then()
                    .spec(getResponseSpecification())
                    .extract()
                    .body()
                    .as(CuisineResponse.class);
            assertThat("Данное блюдо не принадлежит французской кухне",
                    cuisineMatch(cuisineResponse.cuisines, "french"), equalTo(true));
            System.out.println("Блюдо " + frenchCuisineQueries[i] + " действительно относится к французской кухне");
        }
    }

    String[] italianCuisineQueries = new String[]{"pizza"};
    @Test
    void italianCuisineTest() {
        for (int i = 0; i < italianCuisineQueries.length; i++) {
            CuisineResponse cuisineResponse = given()
                    .spec(getRequestSpecification())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("title", italianCuisineQueries[i])
                    .when()
                    .post(getBaseUrl() + "recipes/cuisine")
                    .then()
                    .spec(getResponseSpecification())
                    .extract()
                    .body()
                    .as(CuisineResponse.class);
            assertThat("Данное блюдо не принадлежит итальянской кухне",
                    cuisineMatch(cuisineResponse.cuisines, "italian"), equalTo(true));
            System.out.println("Блюдо " + italianCuisineQueries[i] + " действительно относится к итальянской кухне");
        }
    }
}
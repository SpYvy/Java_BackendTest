package ru.gb.homework5;

import com.github.javafaker.Faker;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.gb.homework5.api.ProductService;
import ru.gb.homework5.dto.Product;
import ru.gb.homework5.util.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CRUDProductTest {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    static int id = 0;

    @BeforeAll
    static void beforeAll() throws IOException {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    /**
     * Create a new product. If id != null, then throw bad request response
     * @throws IOException
     */
    @Test
    @Order(1)
    void createProductInFoodCategoryTest() throws IOException {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice(faker.number().numberBetween(1, 1000))
                .withCategoryTitle("Food");

        Response<Product> response = productService.createProduct(product).execute();
        assertThat("Response return null", response.body()!=null, CoreMatchers.is(true));
        id = response.body().id;
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    /**
     * Returns a specific product by their identifier. 404 if does not exist.
     * @throws IOException
     */
    @Test //Get created product
    @Order(2)
    void getProductByIdTest() throws IOException {
        Response<Product> response = productService.getProductById(id).execute();
        System.out.println(response.body());
    }

    /**
     * Modify product
     * @throws IOException
     */
    @Test //Modify product
    @Order(3)
    void modifyCreatedProductTest() throws IOException {
        product = new Product()
                .withId(id)
                .withTitle(faker.food().ingredient())
                .withPrice(faker.number().numberBetween(1,1000))
                .withCategoryTitle("Food");

        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat("Response return null", response.body()!=null, CoreMatchers.is(true));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    /**
     * Delete product
     * @throws IOException
     */
    @AfterAll
    static void tearDown() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}

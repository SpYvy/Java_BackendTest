package ru.gb.homework6;

import com.github.javafaker.Faker;
import okhttp3.ResponseBody;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.gb.homework6.api.ProductService;
import db.dao.ProductsMapper;
import db.model.Products;
import db.model.ProductsExample;
import ru.gb.homework6.dto.Product;
import ru.gb.homework6.util.RetrofitUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CRUDProductTest {
    static ProductService productService;
    static Product product;
    Faker faker = new Faker();
    static int id = 0;
    static ProductsMapper productsMapper;

    @BeforeAll
    static void beforeAll() throws IOException {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);

        String resource = "mybatis-config.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);

                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession sqlSession = sqlSessionFactory.openSession();

        productsMapper = sqlSession.getMapper(ProductsMapper.class);
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

        //assert that db contains the same product
        ProductsExample productsCreateExample = new ProductsExample();
        productsCreateExample.createCriteria().andIdEqualTo((long) id).andTitleLike(product.title).andPriceEqualTo(product.price);
        List<Products> list = productsMapper.selectByExample(productsCreateExample);
        assertThat(list.size(), CoreMatchers.is(1));
        assertThat(list.get(0).getTitle().equals(product.title)&&
                list.get(0).getPrice().equals(product.price), CoreMatchers.is(true));
    }

    /**
     * Returns a specific product by their identifier. 404 if it does not exist.
     * @throws IOException
     */
    @Test //Get created product
    @Order(2)
    void getProductByIdTest() throws IOException {
        Response<Product> response = productService.getProductById(id).execute();

        assertThat("Response return null", response.body()!=null, CoreMatchers.is(true));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        ProductsExample productsGetExample = new ProductsExample();
        productsGetExample.createCriteria().andIdEqualTo((long) id).andTitleLike(product.title).andPriceEqualTo(product.price);
        List<Products> list = productsMapper.selectByExample(productsGetExample);
        assertThat(list.size(), CoreMatchers.is(1));
        assertThat(list.get(0).getTitle().equals(response.body().title) &&
                list.get(0).getPrice().equals(response.body().price), CoreMatchers.is(true));
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

        ProductsExample productsModifyExample = new ProductsExample();

        productsModifyExample.createCriteria().andIdEqualTo((long) id).andTitleEqualTo(product.title).andPriceEqualTo(product.price);
        List<Products> list = productsMapper.selectByExample(productsModifyExample);
        assertThat(list.size(), CoreMatchers.is(1));
        assertThat(list.get(0).getTitle().equals(product.title) &&
                list.get(0).getPrice().equals(product.price), CoreMatchers.is(true));
    }

    /**
     * Delete product
     * @throws IOException
     */
    @Test //Delete product
    @Order(4)
     void deleteCreatedProductTest() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        ProductsExample productsDeleteExample = new ProductsExample();
        productsDeleteExample.createCriteria().andIdEqualTo((long) id);
        List<Products> list = productsMapper.selectByExample(productsDeleteExample);
        assertThat(list.size(), CoreMatchers.is(0));
    }
}

package ru.gb.homework5;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.gb.homework5.api.CategoryService;
import ru.gb.homework5.dto.GetCategoryResponse;
import ru.gb.homework5.util.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCategoryTest {
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() throws IOException {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    /**
     * Get category by id
     * @throws IOException
     */
    @Test
    void getCategoryByIdPositiveTest() throws IOException {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product -> assertThat(product.getCategoryTitle(), equalTo("Food")));
    }
}

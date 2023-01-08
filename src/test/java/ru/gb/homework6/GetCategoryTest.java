package ru.gb.homework6;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.gb.homework6.api.CategoryService;
import db.model.Categories;
import db.model.CategoriesExample;
import db.dao.CategoriesMapper;
import ru.gb.homework6.dto.GetCategoryResponse;
import ru.gb.homework6.util.RetrofitUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetCategoryTest {
    static CategoryService categoryService;
    static CategoriesMapper categoriesMapper;

    @BeforeAll
    static void beforeAll() throws IOException {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        categoriesMapper = sqlSession.getMapper(CategoriesMapper.class);
    }

    /**
     * Get category by id
     * @throws IOException
     */
    @Test
    void getCategoryByIdPositiveTest() throws IOException {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        assertThat("Response return null", response.body()!=null, CoreMatchers.is(true));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        response.body().getProducts().forEach(product -> assertThat(product.getCategoryTitle(), equalTo("Food")));

        CategoriesExample categoriesExample = new CategoriesExample();

        categoriesExample.createCriteria().andIdEqualTo(1L).andTitleEqualTo(response.body().title);
        List<Categories> list = categoriesMapper.selectByExample(categoriesExample);
        assertThat("Category not found in db", list.size() > 0, CoreMatchers.is(true));
    }
}

package ru.gb.homework5.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.gb.homework5.dto.GetCategoryResponse;

public interface CategoryService {
    @GET("categories/{id}") //getCategoryById
    Call<GetCategoryResponse> getCategory(@Path("id") int id);
}

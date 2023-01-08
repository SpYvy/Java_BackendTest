package ru.gb.homework6.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.gb.homework6.dto.Product;

public interface ProductService {
    @GET("products") //Returns products
    Call<Product> getProduct();

    @POST("products") //Creates a new product. If id != null, then throw bad request response
    Call<Product> createProduct(@Body Product createProductRequest);

    @PUT("products") //Modify product
    Call<Product> modifyProduct(@Body Product modifyProductRequest);

    @GET("products/{id}") //Returns a specific product by their identifier. 404 if it does not exist.
    Call<Product> getProductById(@Path("id") int id);

    @DELETE("products/{id}") //Delete product
    Call<ResponseBody> deleteProduct(@Path("id") int id);
}

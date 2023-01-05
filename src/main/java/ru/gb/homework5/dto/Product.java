package ru.gb.homework5.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "price",
        "categoryTitle"
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@With
public class Product {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("categoryTitle")
    public String categoryTitle;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

//    public Product withId(int id){ //Сеттеры для отдельных полей в fluent-стиле, также можно сделать через Lombok @With
//        this.id = id;
//        return this;
//    }
//
//    public Product withTitle(String title){
//        this.title = title;
//        return this;
//    }
//
//    public Product withPrice(int price){
//        this.price = price;
//        return this;
//    }
//
//    public Product withCategoryTitle(String categoryTitle){
//        this.categoryTitle = categoryTitle;
//        return this;
//    }
}

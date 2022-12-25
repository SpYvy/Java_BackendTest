package ru.gb.homework4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "results",
        "offset",
        "number",
        "totalResults"
})
@Data //Lombok автоматически создает геттеры и сеттеры для всех полей класса!
public class DishesQueryResponse {
    @JsonProperty("results")
    public List<Result> results = null;
    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("number")
    public Integer number;
    @JsonProperty("totalResults")
    public Integer totalResults;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "title",
            "image",
            "imageType"
    })
    @Data //Lombok автоматически создает геттеры и сеттеры для всех полей класса!
    protected static class Result {
        @JsonProperty("id")
        public Integer id;
        @JsonProperty("title")
        public String title;
        @JsonProperty("image")
        public String image;
        @JsonProperty("imageType")
        public String imageType;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    }
}
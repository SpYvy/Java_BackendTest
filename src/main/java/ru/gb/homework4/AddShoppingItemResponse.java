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
    "id",
    "name",
    "measures",
    "usages",
    "usageRecipeIds",
    "pantryItem",
    "aisle",
    "cost",
    "ingredientId"
})
@Data //Lombok автоматически создает геттеры и сеттеры для всех полей класса!
public class AddShoppingItemResponse {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("measures")
    public Measures measures;
    @JsonProperty("usages")
    public List<Object> usages = null;
    @JsonProperty("usageRecipeIds")
    public List<Object> usageRecipeIds = null;
    @JsonProperty("pantryItem")
    public Boolean pantryItem;
    @JsonProperty("aisle")
    public String aisle;
    @JsonProperty("cost")
    public Double cost;
    @JsonProperty("ingredientId")
    public Integer ingredientId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "original",
            "metric",
            "us"
    })
    private static class Measures {
        @JsonProperty("original")
        public Original original;
        @JsonProperty("metric")
        public Metric metric;
        @JsonProperty("us")
        public Us us;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonPropertyOrder({
                "amount",
                "unit"
        })
        private static class Original {
            @JsonProperty("amount")
            public Double amount;
            @JsonProperty("unit")
            public String unit;
            @JsonIgnore
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonPropertyOrder({
                "amount",
                "unit"
        })
        private static class Metric {
            @JsonProperty("amount")
            public Double amount;
            @JsonProperty("unit")
            public String unit;
            @JsonIgnore
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonPropertyOrder({
                "amount",
                "unit"
        })
        private static class Us {
            @JsonProperty("amount")
            public Double amount;
            @JsonProperty("unit")
            public String unit;
            @JsonIgnore
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        }
    }
}




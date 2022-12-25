package ru.gb.homework4;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "item",
        "aisle",
        "parse"
})
public class AddShoppingItemRequest {
    @JsonProperty("item")
    public String item;
    @JsonProperty("aisle")
    public String aisle;
    @JsonProperty("parse")
    public Boolean parse;

    public AddShoppingItemRequest(String item, String aisle, Boolean parse) {
        this.item = item;
        this.aisle = aisle;
        this.parse = parse;
    }
}
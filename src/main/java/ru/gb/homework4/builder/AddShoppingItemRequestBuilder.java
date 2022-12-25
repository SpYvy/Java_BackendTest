package ru.gb.homework4.builder;

import ru.gb.homework4.AddShoppingItemRequest;

public class AddShoppingItemRequestBuilder {
    private String item = "";
    private String aisle = "";
    private Boolean parse = false;

    public AddShoppingItemRequestBuilder setItem(String item) {
        this.item = item;
        return this;
    }

    public AddShoppingItemRequestBuilder setAisle(String aisle) {
        this.aisle = aisle;
        return this;
    }

    public AddShoppingItemRequestBuilder setParse(Boolean parse) {
        this.parse = parse;
        return this;
    }

    public AddShoppingItemRequest createAddShoppingItemRequest() {
        return new AddShoppingItemRequest(item, aisle, parse);
    }
}

package ru.gb.homework4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "username",
        "spoonacularPassword",
        "hash"
})
@Data //Lombok автоматически создает геттеры и сеттеры для всех полей класса!
public class ConnectUserResponse {

    @JsonProperty("status")
    public String status;
    @JsonProperty("username")
    public String username;
    @JsonProperty("spoonacularPassword")
    public String spoonacularPassword;
    @JsonProperty("hash")
    public String hash;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
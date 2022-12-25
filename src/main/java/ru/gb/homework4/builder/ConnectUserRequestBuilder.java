package ru.gb.homework4.builder;

import ru.gb.homework4.ConnectUserRequest;

public class ConnectUserRequestBuilder {
    private String username = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";

    public ConnectUserRequestBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public ConnectUserRequestBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ConnectUserRequestBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ConnectUserRequestBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public ConnectUserRequest createConnectUserRequest() {
        return new ConnectUserRequest(username, firstName, lastName, email);
    }

}

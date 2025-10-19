package com.reqres.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public Integer id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;
}

package com.proyecto.spring.app.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

//Clase POJO

public class Customer {

    //ATRIBUTOS
    @JsonProperty("id")
    private Integer id;

    private String name;

    private String username;

    private String password;

    //CONSTRUCTOR
    public Customer(Integer id, String name, String username, String password) {        //CONSTRUCTOR 
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }


  
                
    public int getID() {        //GETTERS
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {   //SETTERS
                                                 
    
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   

}

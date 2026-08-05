/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

public class UserSearchResult {

    private String fullName;
    private String username;
    private String location;

    public UserSearchResult(String fullName, String username, String location) {
        this.fullName = fullName;
        this.username = username;
        this.location = location;
    }

    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getLocation() { return location; }
}

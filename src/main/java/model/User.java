/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Iuri
 */
@Value
public class User {
@SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(User.class);

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
 
 public String getEmail() {
        return this.email;
 }
 
 
}
 
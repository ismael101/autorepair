package com.project.autoshop.utils;

import java.util.regex.Pattern;

public class EmailValidator {
    //loads regex from environmenmt
    public String regex = "^(.+)@(\\S+)$";;

    //checks to see if email is valid
    public Boolean validate(String email){
        return Pattern.compile(regex).matcher(email).matches();
    }
}

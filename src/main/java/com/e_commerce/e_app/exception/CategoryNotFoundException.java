package com.e_commerce.e_app.exception;

public class CategoryNotFoundException extends RuntimeException {
    public  CategoryNotFoundException(String message){
        super(message);
    }
}

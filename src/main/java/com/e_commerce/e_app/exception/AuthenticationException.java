package com.e_commerce.e_app.exception;

public class AuthenticationException extends RuntimeException{
   AuthenticationException(String message){
       super(message);
   }
}

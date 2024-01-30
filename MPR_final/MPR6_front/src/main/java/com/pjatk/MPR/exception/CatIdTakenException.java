package com.pjatk.MPR.exception;

public class CatIdTakenException extends RuntimeException{
    public CatIdTakenException(){super("Cat with this Id already exists :(");}
}

package com.pjatk.MPR.exception;

public class CatNotFoundException extends RuntimeException{
    public CatNotFoundException() {super("Cat not found :(");}
}

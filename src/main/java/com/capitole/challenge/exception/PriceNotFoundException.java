package com.capitole.challenge.exception;

public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException() {
        super("NO EXISTE REGISTRO DE PRECIO PARA LOS PARAMETROS INGRESADOS");
    }
}
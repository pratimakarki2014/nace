package com.demo.nace.exception;

import java.io.IOException;

public class CSVParseException extends IOException {

    public CSVParseException(String message){
        super(message);
    }
}

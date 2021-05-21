package com.jgeig001.printerCounter.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NoColorPrinterException extends RuntimeException {

    public NoColorPrinterException() {
        super();
    }

    public NoColorPrinterException(String message) {
        super(message);
    }

}

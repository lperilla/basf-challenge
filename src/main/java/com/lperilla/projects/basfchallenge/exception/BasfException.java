package com.lperilla.projects.basfchallenge.exception;

import java.io.Serial;

public class BasfException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1570951635194149804L;

    public BasfException(String message) {
        super(message);
    }

    public BasfException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasfException(Throwable cause) {
        super(cause);
    }

}

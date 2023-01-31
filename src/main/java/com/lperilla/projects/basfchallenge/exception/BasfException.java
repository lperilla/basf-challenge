package com.lperilla.projects.basfchallenge.exception;

public class BasfException extends RuntimeException {

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

package com.lperilla.projects.basfchallenge.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BasfExceptionTest {

    @Test
    void testBasfException_WithMessage() {
        var ex = new BasfException("Example Exception");
        assertNotNull(ex.getMessage());
        assertEquals("Example Exception", ex.getMessage());
    }

    @Test
    void testBasfException_WithMessageAndCause() {
        var cause = new Throwable("Cause 1");
        var ex = new BasfException("Example Exception", cause);
        assertNotNull(ex.getMessage());
        assertNotNull(ex.getCause());
        assertEquals("Example Exception", ex.getMessage());
        assertEquals("Cause 1", ex.getCause().getMessage());
    }

    @Test
    void testBasfException_WithCause() {
        var cause = new Throwable("Cause 1");
        var ex = new BasfException(cause);
        assertNotNull(ex.getCause());
        assertEquals("Cause 1", ex.getCause().getMessage());
    }
}
package com.ezo.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private final IService service = new Service();

    @Test
    void testValidExpressions() {
        assertEquals(2.0, service.run("1+1"));
        assertEquals(3.0, service.run("1 + 2"));
        assertEquals(0.0, service.run("1 + -1"));
        assertEquals(0.0, service.run("-1 - -1"));
        assertEquals(1.0, service.run("5-4"));
        assertEquals(10.0, service.run("5*2"));
        assertEquals(21.0, service.run("(2+5)*3"));
        assertEquals(5.0, service.run("10/2"));
    }

    @Test
    void testDivisionByZero() {
        Object result = service.run("1/0");
        assertTrue(result.toString().contains("Pas de division par 0"));
    }

    @Test
    void testOperatorNotSupported() {
        Object result = service.run("2%3");
        assertTrue(result.toString().contains("Operateur invalide"));
    }
}

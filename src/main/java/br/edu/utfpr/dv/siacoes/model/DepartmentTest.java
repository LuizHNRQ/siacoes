package br.edu.utfpr.dv.siacoes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void getIdDepartment() {
        Department d = new Department();
        int numeroDep = 7;

        d.setIdDepartment(numeroDep);
        int resultadoDoTeste = d.getIdDepartment();

        assertEquals(numeroDep, resultadoDoTeste);
    }
}


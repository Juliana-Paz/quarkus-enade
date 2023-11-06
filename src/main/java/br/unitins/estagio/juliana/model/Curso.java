package br.unitins.estagio.juliana.model;

import jakarta.persistence.Entity;

@Entity
public class Curso extends DefaultEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

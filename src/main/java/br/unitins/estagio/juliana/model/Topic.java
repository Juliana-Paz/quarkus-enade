package br.unitins.estagio.juliana.model;

import jakarta.persistence.Entity;

@Entity
public class Topic extends DefaultEntity {
    
    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

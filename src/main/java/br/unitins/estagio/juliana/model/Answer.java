package br.unitins.estagio.juliana.model;

import jakarta.persistence.Entity;

@Entity
public class Answer extends DefaultEntity {
    
    private String answerText;
    
    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

}

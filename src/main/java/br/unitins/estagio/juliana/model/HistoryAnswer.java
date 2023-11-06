package br.unitins.estagio.juliana.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class HistoryAnswer extends DefaultEntity {
    
    private Boolean answeredCorrectly;
    private LocalDate timestamp;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_question")
    private Question question;
    @OneToOne
    @JoinColumn(name = "id_answer")
    private Answer answer;
    

    public Boolean getAnsweredCorrectly() {
        return answeredCorrectly;
    }
    public void setAnsweredCorrectly(Boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }
    public LocalDate getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }    

}

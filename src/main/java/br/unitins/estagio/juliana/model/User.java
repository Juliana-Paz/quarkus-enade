package br.unitins.estagio.juliana.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User extends DefaultEntity {

    private String name;
    private String matricula;
    private String telegram;
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    private Long telegramUserId;

    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getTelegram() {
        return telegram;
    }
    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }
    public Long getTelegramUserId() {
        return telegramUserId;
    }
    public void setTelegramUserId(Long telegramUserId) {
        this.telegramUserId = telegramUserId;
    }
    
}

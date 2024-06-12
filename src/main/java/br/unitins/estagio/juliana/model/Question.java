package br.unitins.estagio.juliana.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Question extends DefaultEntity {

    private String questionText;
    private String explanation;
    @ManyToOne
    @JoinColumn(name = "id_answer_correct")
    private Answer correctAnswer;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_answer", joinColumns = @JoinColumn(name = "id_question"), inverseJoinColumns = @JoinColumn(name = "id_answer"))
    private List<Answer> answers;
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    //TODO topic

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}

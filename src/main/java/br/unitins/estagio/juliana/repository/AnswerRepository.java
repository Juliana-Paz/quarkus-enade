package br.unitins.estagio.juliana.repository;

import java.util.List;

import br.unitins.estagio.juliana.model.Answer;
import br.unitins.estagio.juliana.model.Question;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnswerRepository implements PanacheRepository<Answer> {
    // Aqui você pode adicionar métodos customizados, se necessário.

    // Exemplo de método customizado para encontrar resposta por texto
    public Answer findByAnswerText(String answerText) {
        return find("answerText", answerText).firstResult();
    }
}

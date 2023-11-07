package br.unitins.estagio.juliana.service;

import br.unitins.estagio.juliana.model.Question;
import br.unitins.estagio.juliana.repository.HistoryAnswerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class HistoryAnswerServiceImpl implements HistoryAnswerService {

    @Inject
    HistoryAnswerRepository historyAnswerRepository;

    @Override
    public Question findUnansweredQuestion(Long idUser) {

        return null;
    }

}

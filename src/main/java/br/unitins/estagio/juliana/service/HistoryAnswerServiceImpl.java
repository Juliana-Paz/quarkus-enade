package br.unitins.estagio.juliana.service;

import java.util.List;

import br.unitins.estagio.juliana.model.Question;
import br.unitins.estagio.juliana.repository.HistoryAnswerRepository;
import jakarta.inject.Inject;

public class HistoryAnswerServiceImpl implements HistoryAnswerService {

    @Inject
    HistoryAnswerRepository historyAnswerRepository;    

    @Override
    public Question findUnansweredQuestion(Long idUser) {
        
        return null;
    }    
    
}

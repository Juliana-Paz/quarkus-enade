package br.unitins.estagio.juliana.service;

import br.unitins.estagio.juliana.model.Question;

public interface HistoryAnswerService {
    
    public Question findUnansweredQuestion(Long idUser);

}

package br.unitins.estagio.juliana.service;

import java.util.List;
import java.util.Random;

import br.unitins.estagio.juliana.model.Question;
import br.unitins.estagio.juliana.repository.HistoryAnswerRepository;
import br.unitins.estagio.juliana.repository.QuestionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnadeServiceImpl implements EnadeServive {

    @Inject
    QuestionRepository quentionRepository;

    @Inject
    HistoryAnswerRepository historyAnswerRepository;

    @Override
    public Question dailyQuestion(Long idUser) {
        // to do
        // verificar se ha historyAnswer em aberto (if is null)


        // Buscar total de questoes do curso (menos as que o user ja respondeu)

        // TO - DO
        // Buscar pelo curso do user (find user by id) - id do curso 
        List<Question> totalQuestoes = quentionRepository.findAll(2l); 
        
        List<Question> questoesRespondidas = historyAnswerRepository.findAllQuestionsByUser(idUser);
        
        totalQuestoes.removeAll(questoesRespondidas);
        
        Random random = new Random();
        int randomIndex = random.nextInt(totalQuestoes.size());
        
        // Salvar historyAwns. ()
        Question question = totalQuestoes.get(randomIndex);

        return question;

    }
    
}

package br.unitins.estagio.juliana.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import br.unitins.estagio.juliana.model.HistoryAnswer;
import br.unitins.estagio.juliana.model.Question;
import br.unitins.estagio.juliana.model.User;
import br.unitins.estagio.juliana.repository.HistoryAnswerRepository;
import br.unitins.estagio.juliana.repository.QuestionRepository;
import br.unitins.estagio.juliana.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnadeServiceImpl implements EnadeServive {

    @Inject
    QuestionRepository quentionRepository;

    @Inject
    HistoryAnswerRepository historyAnswerRepository;

    @Inject
    UserRepository userRepository;    

    @Transactional
    @Override
    public Question dailyQuestion(Long idUser) {
        // to do
        // verificar se ha historyAnswer em aberto (if is null)
        // Buscar total de questoes do curso (menos as que o user ja respondeu)
          
        HistoryAnswer h = historyAnswerRepository.findUnanswered(idUser);        

        if (h != null) {
            return h.getQuestion();
        }

        User user = userRepository.findById(idUser);

        Question question = findNewRandomQuestion(user);
        
        // Registrando questao do historyAns.
        HistoryAnswer history = new HistoryAnswer();
        history.setUser(user);
        history.setQuestion(question);
        history.setTimestamp(LocalDateTime.now());
        historyAnswerRepository.persist(history);

        return question;

    }
    
    private Question findNewRandomQuestion(User user) {
        
        Long idCurso = user.getCurso().getId();

        List<Question> totalQuestoes = quentionRepository.findAll(idCurso); 
        
        List<Question> questoesRespondidas = historyAnswerRepository.findAllQuestionsByUser(user.getId());
        
        totalQuestoes.removeAll(questoesRespondidas);
        
        Random random = new Random();
        int randomIndex = random.nextInt(totalQuestoes.size());
        Question question = totalQuestoes.get(randomIndex);

        return question;

    }

}

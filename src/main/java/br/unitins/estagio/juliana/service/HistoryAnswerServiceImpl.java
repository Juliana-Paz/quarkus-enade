package br.unitins.estagio.juliana.service;

import java.time.LocalDateTime;

import br.unitins.estagio.juliana.model.Answer;
import br.unitins.estagio.juliana.model.HistoryAnswer;
import br.unitins.estagio.juliana.model.Question;
import br.unitins.estagio.juliana.model.User;
import br.unitins.estagio.juliana.repository.AnswerRepository;
import br.unitins.estagio.juliana.repository.HistoryAnswerRepository;
import br.unitins.estagio.juliana.repository.QuestionRepository;
import br.unitins.estagio.juliana.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class HistoryAnswerServiceImpl implements HistoryAnswerService {

    @Inject
    HistoryAnswerRepository historyAnswerRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    QuestionRepository questionRepository;

    @Inject
    AnswerRepository answerRepository;

    @Override
    public Question findUnansweredQuestion(Long idUser) {

        return null;
    }

     @Transactional
    public HistoryAnswer insert(Long userId, Long questionId, Long answerId) {
        User user = userRepository.findById(userId);
        Question question = questionRepository.findById(questionId);
        Answer userAnswer = answerRepository.findById(answerId);

        if (user == null || question == null || userAnswer == null) {
            throw new IllegalArgumentException("User, Question, or Answer not found");
        }

        // Comparar o conteúdo da resposta dada com o conteúdo da resposta correta
        boolean isCorrect = userAnswer.getAnswerText().equals(question.getCorrectAnswer().getAnswerText());

        HistoryAnswer historyAnswer = new HistoryAnswer();
        historyAnswer.setUser(user);
        historyAnswer.setQuestion(question);
        historyAnswer.setAnswer(userAnswer);
        historyAnswer.setAnsweredCorrectly(isCorrect);
        historyAnswer.setTimestamp(LocalDateTime.now());

        historyAnswerRepository.persist(historyAnswer);

        return historyAnswer;
    }

}

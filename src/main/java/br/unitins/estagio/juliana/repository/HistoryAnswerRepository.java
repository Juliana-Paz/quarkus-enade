package br.unitins.estagio.juliana.repository;

import java.util.List;

import br.unitins.estagio.juliana.model.HistoryAnswer;
import br.unitins.estagio.juliana.model.Question;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;

@ApplicationScoped
public class HistoryAnswerRepository implements PanacheRepository<HistoryAnswer> {

    public List<Question> findAllQuestionsByUser(Long idUser) {

        String jpql = "SELECT h.question FROM HistoryAnswer h WHERE h.user.id = ?1";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter(1, idUser);
        
        return query.getResultList();                
        
    }

    

}

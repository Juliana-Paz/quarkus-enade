package br.unitins.estagio.juliana.repository;

import java.util.List;

import br.unitins.estagio.juliana.model.Question;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuestionRepository implements PanacheRepository<Question> {
    
    public List<Question> findAll(Long idCurso) {        
        return find("curso.id = ?1", idCurso).list();
    }


}

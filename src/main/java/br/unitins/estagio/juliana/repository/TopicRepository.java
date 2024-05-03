package br.unitins.estagio.juliana.repository;

import java.util.List;

import br.unitins.estagio.juliana.model.Topic;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TopicRepository implements PanacheRepository<Topic> {

    public List<Topic> findByName(Topic topic) {
        return find("name LIKE ?1", "%" + topic + "%").list();
    }

}

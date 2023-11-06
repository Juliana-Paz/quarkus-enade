package br.unitins.estagio.juliana.repository;

import java.util.List;

import br.unitins.estagio.juliana.model.Curso;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CursoRepository implements PanacheRepository<Curso> {

    public List<Curso> findByName(String name) {

        return find("name LIKE ?1", "%" + name + "%").list();

    }
}

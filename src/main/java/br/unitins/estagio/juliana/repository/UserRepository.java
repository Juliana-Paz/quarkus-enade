package br.unitins.estagio.juliana.repository;

import java.util.List;

import br.unitins.estagio.juliana.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    
    public List<User> findByName(String user) {

        return find("name LIKE ?1", "%" + user + "%").list();

    }

    public List<User> findByCurso(Long idCurso) {

        return find("curso.id = ?1", idCurso).list();

    }

    // modificar o nome do metodo para getUserId
    // Se nenhum usuário for encontrado com esse ID, o método firstResult() retornará null.
    public User telegramUserIdExists(Long telegramUserId) {

        return find("telegramUserId", telegramUserId).firstResult();

    }

}

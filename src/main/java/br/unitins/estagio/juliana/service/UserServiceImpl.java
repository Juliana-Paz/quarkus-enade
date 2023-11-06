package br.unitins.estagio.juliana.service;

import java.util.List;

import br.unitins.estagio.juliana.model.User;
import br.unitins.estagio.juliana.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    public UserRepository repository;

    @Override
    @Transactional
    public User insert(User user) {
        User novoUser = new User();
        novoUser.setName(user.getName());

        repository.persist(novoUser);
        return user;
    }

    @Override
    @Transactional
    public User update(User user, Long id) {
        User updateUser = repository.findById(id);
        updateUser.setName(user.getName());

        return updateUser;
    }

    @Override
    public void delete(Long id) {
       repository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<User> findByAll() {
        return repository.findAll().list();
    }

    @Override
    public List<User> findByCurso(Long idCurso) {
        return repository.findByCurso(idCurso);
    }

}

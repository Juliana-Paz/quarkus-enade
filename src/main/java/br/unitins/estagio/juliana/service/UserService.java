package br.unitins.estagio.juliana.service;

import java.util.List;

import br.unitins.estagio.juliana.model.User;

public interface UserService {
    
    public User insert(User user);

    public User update(User user, Long id);

    public void delete(Long id);

    public User findById(Long id);

    public List<User> findByName(String user);
    
    public List<User> findByAll();

    public List<User> findByCurso(Long idCurso);
    
}

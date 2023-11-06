package br.unitins.estagio.juliana.service;

import java.util.List;

import br.unitins.estagio.juliana.model.Curso;
import br.unitins.estagio.juliana.repository.CursoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CursoServiceImpl implements CursoService {

    @Inject
    public CursoRepository repository;

    @Override
    @Transactional
    public Curso insert(Curso curso) {
        Curso novoCurso = new Curso();
        novoCurso.setName(curso.getName());

        repository.persist(novoCurso);
        return curso;
    }

    @Override
    @Transactional
    public Curso update(Curso curso, Long id) {
        Curso updateCurso = repository.findById(id);
        updateCurso.setName(curso.getName());

        return updateCurso;
    }

    @Override
    public void delete(Long id) {
       repository.deleteById(id);
    }

    @Override
    public Curso findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Curso> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Curso> findByAll() {
        return repository.findAll().list();
    }

}

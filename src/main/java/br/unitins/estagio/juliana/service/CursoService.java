package br.unitins.estagio.juliana.service;

import java.util.List;

import br.unitins.estagio.juliana.model.Curso;

public interface CursoService {

    public Curso insert(Curso curso);

    public Curso update(Curso curso, Long id);

    public void delete(Long id);

    public Curso findById(Long id);

    public List<Curso> findByName(String name);

    public List<Curso> findByAll(); 
    
}

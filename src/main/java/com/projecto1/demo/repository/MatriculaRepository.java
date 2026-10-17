package com.projecto1.demo.repository;

import com.projecto1.demo.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByEstudianteId(Long estudianteId);
    List<Matricula> findByCursoId(Long cursoId);
    boolean existsByEstudianteIdAndCursoIdAndSemestreCursado(Long estudianteId, Long cursoId, String semestreCursado);
}

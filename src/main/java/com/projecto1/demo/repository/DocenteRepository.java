
package com.projecto1.demo.repository;

import com.projecto1.demo.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    List<Docente> findByDepartamentoId(Long departamentoId);
}
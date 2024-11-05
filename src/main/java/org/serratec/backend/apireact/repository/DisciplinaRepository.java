package org.serratec.backend.apireact.repository;

import org.serratec.backend.apireact.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}

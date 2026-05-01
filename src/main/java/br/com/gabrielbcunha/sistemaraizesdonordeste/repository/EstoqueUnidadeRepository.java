package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.EstoqueUnidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueUnidadeRepository extends JpaRepository<EstoqueUnidade, Long> {
}

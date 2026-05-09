package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.EstoqueUnidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueUnidadeRepository extends JpaRepository<EstoqueUnidade, Long> {

    Optional<EstoqueUnidade> findByUnidadeIdAndItemId(Long unidadeId, Long itemId);

}

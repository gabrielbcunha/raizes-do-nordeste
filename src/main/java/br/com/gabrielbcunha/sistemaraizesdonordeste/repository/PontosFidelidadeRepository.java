package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.PontosFidelidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontosFidelidadeRepository extends JpaRepository<PontosFidelidade, Long> {

    PontosFidelidade getPontosFidelidadeByCliente(Cliente cliente);

    Page<PontosFidelidade> findPontosFidelidadeByCliente(Cliente cliente, Pageable pageable);

}

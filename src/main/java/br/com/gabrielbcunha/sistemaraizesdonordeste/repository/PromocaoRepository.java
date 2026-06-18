package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {

    Optional<Promocao> findByCodigoPromocao(String codigoPromocao);

}
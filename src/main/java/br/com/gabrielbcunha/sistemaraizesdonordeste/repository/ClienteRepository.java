package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findClienteByNumCadastroFidelidade(String numCadastroFidelidade);

}

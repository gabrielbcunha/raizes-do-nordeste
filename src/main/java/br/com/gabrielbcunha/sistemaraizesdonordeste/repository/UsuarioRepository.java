package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUserName(String userName);

}

package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.MenuUnidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuUnidadeRepository extends JpaRepository<MenuUnidade, Long> {

    Page<MenuUnidade> findMenuUnidadeByUnidade(Unidade unidade, Pageable pageable);

}

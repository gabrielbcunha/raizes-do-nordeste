package br.com.gabrielbcunha.sistemaraizesdonordeste.repository;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Pedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findPedidoByCanalPedido(CanalPedido canalPedido);

}

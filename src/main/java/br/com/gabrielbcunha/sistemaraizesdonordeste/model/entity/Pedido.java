package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable=false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="unidade_id")
    private Unidade unidade;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private CanalPedido canalPedido;

    @OneToMany(mappedBy="pedido", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<ItemPedido> itens;

    @Column(nullable=false)
    private LocalDateTime dataPedido;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private TipoEntrega tipoEntrega;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

}

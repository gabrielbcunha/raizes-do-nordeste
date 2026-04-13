package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="estoque_unidade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EstoqueUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="unidade_id", nullable = false)
    private Unidade unidade;

    @ManyToOne
    @JoinColumn(name="item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantidade;

}

package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="menu_unidade")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="unidade_id")
    private Unidade unidade;

    @ManyToOne
    private Item item;

    private boolean disponivel;

    @ManyToMany(mappedBy = "itensMenu")
    private Set<Promocao> promocoes = new HashSet<>();
}

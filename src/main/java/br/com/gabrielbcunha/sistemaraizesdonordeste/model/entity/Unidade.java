package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="unidade")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Unidade {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false, precision = 10, scale = 2)
    private Long id;

    @Column(name="nome_unidade", nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 300)
    private String localizacao;

    @ElementCollection(targetClass = CanalPedido.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "unidade_canais", joinColumns = @JoinColumn(name = "unidade_id"))
    @Column(name = "canal")
    private List<CanalPedido> canaisSuportados;

}

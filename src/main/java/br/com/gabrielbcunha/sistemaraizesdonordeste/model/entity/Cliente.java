package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="clientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String numContato;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(unique = true)
    private Long numCadastroFidelidade;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer quantPontosFidelidade;
}

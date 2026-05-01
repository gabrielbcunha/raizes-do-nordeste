package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Cargo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="funcionarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="unidade_id")
    private Unidade unidade;

    @Enumerated(EnumType.STRING)
    @Column(name="cargo", nullable=false)
    private Cargo cargo;

    @Column(nullable = false, length = 150)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="usuario_id", nullable = false, unique = true)
    private Usuario usuario;

}

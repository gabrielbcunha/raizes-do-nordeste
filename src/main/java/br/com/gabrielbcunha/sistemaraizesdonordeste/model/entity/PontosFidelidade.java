package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="pontos_fidelidade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PontosFidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="pedido_id", nullable = true)
    private Pedido pedido;

    @NotBlank
    private String numCadastroFidelidade;

    @Column(nullable = false)
    private Integer quantidadePontos;

    @Column(nullable = false)
    private LocalDateTime dataTransacao;

}

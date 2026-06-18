package br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity;


import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoEntrega;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoPromocao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="promocoes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Promocao {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    
    @Column(name="nome_promocao", nullable = false, length = 150)
    private String nomePromocao;
    
    @Column(name="codigo_promocao", nullable = false, unique = true)
    private String codigoPromocao;

    @ManyToMany
    @JoinTable(name="promocao_unidade", joinColumns = @JoinColumn(name = "promocao_id"), inverseJoinColumns = @JoinColumn(name = "unidade_id"))
    private Set<Unidade> unidades = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name="promocao_itens", joinColumns = @JoinColumn(name = "promocao_id"), inverseJoinColumns = @JoinColumn(name = "menuUnidade_id"))
    private Set<MenuUnidade> itensMenu = new HashSet<>();
    
    @ElementCollection(targetClass = TipoEntrega.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "promocao_entrega", joinColumns = @JoinColumn(name = "promocao_id"))
    @Column(name = "entrega")
    private List<TipoEntrega> entregasEscritasNaPromocao;
    
    @ElementCollection(targetClass = CanalPedido.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "promocao_canal", joinColumns = @JoinColumn(name = "promocao_id"))
    @Column(name = "canais")
    private List<CanalPedido> canaisEscritosNaPromocao;
    
    @ElementCollection(targetClass = FormaPagamento.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "promocao_pagamento", joinColumns = @JoinColumn(name = "promocao_id"))
    @Column(name = "pagamento")
    private List<FormaPagamento> pagamentosEscritosNaPromocao;
    
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private TipoPromocao tipoPromocao;

    @Column(nullable=false, precision = 10, scale = 2)
    private BigDecimal valorPromocao;

    @Column(nullable=false)
    private LocalDateTime inicioPromocao;
    
    @Column(nullable=false)
    private LocalDateTime terminoPromocao;
	
}
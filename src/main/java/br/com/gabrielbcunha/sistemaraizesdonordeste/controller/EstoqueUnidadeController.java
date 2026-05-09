package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.EstoqueUnidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoque")
public class EstoqueUnidadeController {

    private final EstoqueUnidadeService estoqueUnidadeService;

    public EstoqueUnidadeController(EstoqueUnidadeService estoqueUnidadeService) {
        this.estoqueUnidadeService = estoqueUnidadeService;
    }

    @PostMapping()
    public ResponseEntity<EstoqueUnidadeCreateResponse> adicionarItemEstoque(@Valid @RequestBody EstoqueUnidadeCreateRequest estoqueUnidadeCreateRequest) {
        EstoqueUnidadeCreateResponse cadastroItemEstoque = estoqueUnidadeService.cadastrarItemEstoque(estoqueUnidadeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroItemEstoque);
    }

}
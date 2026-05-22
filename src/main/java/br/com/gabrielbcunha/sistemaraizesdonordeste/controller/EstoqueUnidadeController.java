package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.EstoqueUnidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ResponseEntity<Page<EstoqueUnidadeCreateResponse>> listarTodosEstoques(@PageableDefault(sort="unidade.id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<EstoqueUnidadeCreateResponse> listaEstoqueUnidade = estoqueUnidadeService.listarTodosEstoques(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listaEstoqueUnidade);
    }
}
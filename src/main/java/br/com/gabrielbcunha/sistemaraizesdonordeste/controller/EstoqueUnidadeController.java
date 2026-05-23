package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.EstoqueUnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Estoques das Unidades", description="Endpoints para o cadastro, modificação, leitura e exclusão de Itens nos Estoques das Unidades")
public class EstoqueUnidadeController {

    private final EstoqueUnidadeService estoqueUnidadeService;

    public EstoqueUnidadeController(EstoqueUnidadeService estoqueUnidadeService) {
        this.estoqueUnidadeService = estoqueUnidadeService;
    }

    @PostMapping()
    @Operation(summary="Cadastra um novo Item no estoque de uma Unidade",
            description="Endpoint para o cadastro de um novo Item no estoque de uma unidade válida informada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de estoque cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Item não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<EstoqueUnidadeCreateResponse> adicionarItemEstoque(@Valid @RequestBody EstoqueUnidadeCreateRequest estoqueUnidadeCreateRequest) {
        EstoqueUnidadeCreateResponse cadastroItemEstoque = estoqueUnidadeService.cadastrarItemEstoque(estoqueUnidadeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroItemEstoque);
    }

    @GetMapping()
    @Operation(summary="Lista todos os Itens de todos os Estoques")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada, podendo conter ou não conteúdo")
    })
    public ResponseEntity<Page<EstoqueUnidadeCreateResponse>> listarTodosEstoques(@PageableDefault(sort="unidade.id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<EstoqueUnidadeCreateResponse> listaEstoqueUnidade = estoqueUnidadeService.listarTodosEstoques(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listaEstoqueUnidade);
    }
}
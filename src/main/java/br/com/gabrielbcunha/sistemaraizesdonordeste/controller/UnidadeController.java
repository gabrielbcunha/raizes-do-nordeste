package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidade")
@Tag(name="Unidades", description="Endpoints para o cadastro, modificação, leitura e exclusão de unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @PostMapping()
    @Operation(summary="Cadastra uma nova Unidade",
            description="Endpoint para o cadastro de uma nova unidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidade cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Unidade não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<UnidadeCreateResponse> cadastrarUnidade(@Valid @RequestBody UnidadeCreateRequest unidadeCreateRequest){
        UnidadeCreateResponse cadastroUnidade = unidadeService.cadastrarUnidade(unidadeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroUnidade);
    }

    @GetMapping()
    @Operation(summary="Lista todos as Unidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada, podendo conter ou não conteúdo")
    })
    public ResponseEntity<Page<UnidadeCreateResponse>> listarUnidades(Pageable pageable){
        Page<UnidadeCreateResponse> listaUnidades = unidadeService.listarUnidades(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listaUnidades);
    }

}

package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeGetResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PontosFidelidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelidade")
@Tag(name="Fidelidade", description="Endpoints para a listagem do histórico de pontos de fidelidade")
public class PontosFidelidadeController {

    private final PontosFidelidadeService pontosFidelidadeService;

    public PontosFidelidadeController(PontosFidelidadeService pontosFidelidadeService) {
        this.pontosFidelidadeService = pontosFidelidadeService;
    }

    @GetMapping("/{id}")
    @Operation(summary="Busca o histórico de pontos de fidelidade por ID de Cliente",
            description = "Busca o histórico de criação de entidades PontosFidelidade por cliente, contemplando o ganho e uso de pontos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de pontos encontrado, podendo ou não conter conteúdo"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso")
    })
    public ResponseEntity<Page<PontosFidelidadeGetResponse>> listarHistoricoDePontosPorCliente(@PathVariable Long id, @PageableDefault(sort="dataTransacao", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PontosFidelidadeGetResponse> listarPontosPorCliente = pontosFidelidadeService.listarPontosFidelidadePorCliente(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listarPontosPorCliente);
    }

}

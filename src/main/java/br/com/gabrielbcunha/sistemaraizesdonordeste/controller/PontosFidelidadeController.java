package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeGetResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PontosFidelidadeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelidade")
public class PontosFidelidadeController {

    private final PontosFidelidadeService pontosFidelidadeService;

    public PontosFidelidadeController(PontosFidelidadeService pontosFidelidadeService) {
        this.pontosFidelidadeService = pontosFidelidadeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<PontosFidelidadeGetResponse>> listarHistoricoDePontosPorCliente(@PathVariable Long id, @PageableDefault(sort="dataTransacao", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PontosFidelidadeGetResponse> listarPontosPorCliente = pontosFidelidadeService.listarPontosFidelidadePorCliente(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listarPontosPorCliente);
    }

}

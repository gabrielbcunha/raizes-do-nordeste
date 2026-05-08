package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidade")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @PostMapping()
    public ResponseEntity<UnidadeCreateResponse> cadastrarUnidade(@Valid @RequestBody UnidadeCreateRequest unidadeCreateRequest){
        UnidadeCreateResponse cadastroUnidade = unidadeService.cadastrarUnidade(unidadeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroUnidade);
    }

}

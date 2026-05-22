package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ResponseEntity<Page<UnidadeCreateResponse>> listarUnidades(Pageable pageable){
        Page<UnidadeCreateResponse> listaUnidades = unidadeService.listarUnidades(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listaUnidades);
    }

}

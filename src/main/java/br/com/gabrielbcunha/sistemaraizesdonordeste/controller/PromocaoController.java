package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao.PromocaoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao.PromocaoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PromocaoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidade/promocao")
public class PromocaoController {

    private PromocaoService promocaoService;

    public PromocaoController(PromocaoService promocaoService) {
        this.promocaoService = promocaoService;
    }

    @PostMapping()
    public ResponseEntity<PromocaoCreateResponse> criarPromocao(@Valid @RequestBody PromocaoCreateRequest promocaoCreateRequest){
        PromocaoCreateResponse cadastroPromocao = promocaoService.criarPromocao(promocaoCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroPromocao);
    }


}

package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao.PromocaoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao.PromocaoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PromocaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidade/promocao")
@Tag(name="Promoção", description="Endpoints para criação de promoções para unidades")
public class PromocaoController {

    private PromocaoService promocaoService;

    public PromocaoController(PromocaoService promocaoService) {
        this.promocaoService = promocaoService;
    }

    @PostMapping()
    @Operation(summary="Cadastra promoção",
            description="Cadastra promoção para unidades especificadas, podendo ser por porcentagem ou valor fixo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Promoção criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "MenuUnidade ou Unidade não encontrados"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso")
    })
    public ResponseEntity<PromocaoCreateResponse> criarPromocao(@Valid @RequestBody PromocaoCreateRequest promocaoCreateRequest){
        PromocaoCreateResponse cadastroPromocao = promocaoService.criarPromocao(promocaoCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroPromocao);
    }


}

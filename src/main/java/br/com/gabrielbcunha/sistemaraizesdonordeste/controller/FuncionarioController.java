package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/atendentes")
    public ResponseEntity<FuncionarioCreateResponse> cadastrarAtendente(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroAtendente = funcionarioService.cadastrarAtendente(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroAtendente);
    }

}
package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/cozinheiros")
    public ResponseEntity<FuncionarioCreateResponse> cadastrarCozinheiro(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroCozinheiro = funcionarioService.cadastrarCozinheiro(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroCozinheiro);
    }

    @PostMapping("/administrativos")
    public ResponseEntity<FuncionarioCreateResponse> cadastrarAdministrativo(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroAdministrativo = funcionarioService.cadastrarAdministrativo(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroAdministrativo);
    }

    @PostMapping("/gerentes")
    public ResponseEntity<FuncionarioCreateResponse> cadastrarGerente(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroGerente = funcionarioService.cadastrarGerente(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroGerente);
    }

    @GetMapping()
    public ResponseEntity<Page<FuncionarioCreateResponse>> listarTodosFuncionarios(@PageableDefault(sort="unidade.id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<FuncionarioCreateResponse> listaFuncionarios =  funcionarioService.listarTodosFuncionarios(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listaFuncionarios);
    }

}
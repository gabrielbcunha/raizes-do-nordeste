package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioDeleteRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioDeleteResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.FuncionarioService;
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
@RequestMapping("/funcionarios")
@Tag(name="Funcionários", description = "Endpoints para o cadastro, modificação, leitura e exclusão de Funcionários")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/atendentes")
    @Operation(summary="Cadastra um novo funciOnário com ROLE de ATENDENTE",
            description="Endpoint para cadastro de funcionário com permissões de atendente, pode ser realizado por ADMIN OU GERENTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atendente cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Atendente não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<FuncionarioCreateResponse> cadastrarAtendente(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroAtendente = funcionarioService.cadastrarAtendente(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroAtendente);
    }

    @PostMapping("/cozinheiros")
    @Operation(summary="Cadastra um novo funcionário com ROLE de COZINHEIRO",
            description="Endpoint para cadastro de funcionário com permissões de cozinheiro, pode ser realizado por ADMIN OU GERENTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cozinheiro cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Cozinheiro não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<FuncionarioCreateResponse> cadastrarCozinheiro(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroCozinheiro = funcionarioService.cadastrarCozinheiro(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroCozinheiro);
    }

    @PostMapping("/administrativos")
    @Operation(summary="Cadastra um novo funcionário com ROLE de COZINHEIRO",
            description="Endpoint para cadastro de funcionário com permissões de cozinheiro, pode ser realizado por ADMIN OU GERENTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cozinheiro cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Cozinheiro não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<FuncionarioCreateResponse> cadastrarAdministrativo(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroAdministrativo = funcionarioService.cadastrarAdministrativo(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroAdministrativo);
    }

    @PostMapping("/gerentes")
    @Operation(summary="Cadastra um novo funcionário com ROLE de GERENTE",
            description="Endpoint para cadastro de funcionário com permissões de gerente, pode ser realizado somente por ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gerente cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Gerente não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<FuncionarioCreateResponse> cadastrarGerente(@Valid @RequestBody FuncionarioCreateRequest funcionarioCreateRequest) {
        FuncionarioCreateResponse cadastroGerente = funcionarioService.cadastrarGerente(funcionarioCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroGerente);
    }

    @GetMapping()
    @Operation(summary="Lista todos os Funcionários de todas a Unidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada, podendo conter ou não conteúdo")
    })
    public ResponseEntity<Page<FuncionarioCreateResponse>> listarTodosFuncionarios(@PageableDefault(sort="unidade.id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<FuncionarioCreateResponse> listaFuncionarios =  funcionarioService.listarTodosFuncionarios(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listaFuncionarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FuncionarioDeleteResponse> deletarFuncionario(@PathVariable Long id, @Valid @RequestBody FuncionarioDeleteRequest request) {
        FuncionarioDeleteResponse funcionarioDeletado = funcionarioService.deletarDadosFuncionario(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioDeletado);
    }


}
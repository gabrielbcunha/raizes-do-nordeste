package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.MenuUnidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.MenuUnidadeService;
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
@RequestMapping("/menu")
@Tag(name="Menus das Unidades", description="Endpoints para o cadastro, modificação, leitura e exclusão de Itens nos Menus das Unidades")
public class MenuUnidadeController {

    private MenuUnidadeService menuUnidadeService;

    public MenuUnidadeController(MenuUnidadeService menuUnidadeService) {
        this.menuUnidadeService = menuUnidadeService;
    }

    @PostMapping()
    @Operation(summary="Cadastra um novo Item no menu de um Unidade",
            description="Endpoint para o cadastro de um novo Item no menu de uma unidade válida informada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de menu cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Item não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<MenuUnidadeCreateResponse> adicionarItemMenu(@Valid @RequestBody MenuUnidadeCreateRequest menuUnidadeCreateRequest){
        MenuUnidadeCreateResponse cadastroItemMenu = menuUnidadeService.cadastarItemMenu(menuUnidadeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroItemMenu);
    }

    @GetMapping()
    @Operation(summary="Lista os Itens dos Menus das Unidades podendo ou não ser filtrado por Unidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada, podendo conter ou não conteúdo")
    })
    public ResponseEntity<Page<MenuUnidadeCreateResponse>> listarTodosMenuUnidades(@RequestParam(required = false) Long idUnidade, @PageableDefault(sort="unidade.id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<MenuUnidadeCreateResponse> listarMenuUnidade = menuUnidadeService.listarMenuUnidades(idUnidade,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listarMenuUnidade);
    }
}

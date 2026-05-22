package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.MenuUnidadeService;
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
public class MenuUnidadeController {

    private MenuUnidadeService menuUnidadeService;

    public MenuUnidadeController(MenuUnidadeService menuUnidadeService) {
        this.menuUnidadeService = menuUnidadeService;
    }

    @PostMapping()
    public ResponseEntity<MenuUnidadeCreateResponse> adicionarItemMenu(@Valid @RequestBody MenuUnidadeCreateRequest menuUnidadeCreateRequest){
        MenuUnidadeCreateResponse cadastroItemMenu = menuUnidadeService.cadastarItemMenu(menuUnidadeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroItemMenu);
    }

    @GetMapping()
    public ResponseEntity<Page<MenuUnidadeCreateResponse>> listarTodosMenuUnidades(@PageableDefault(sort="unidade.id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<MenuUnidadeCreateResponse> listarMenuUnidade = menuUnidadeService.listarTodosMenuUnidades(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listarMenuUnidade);
    }

}

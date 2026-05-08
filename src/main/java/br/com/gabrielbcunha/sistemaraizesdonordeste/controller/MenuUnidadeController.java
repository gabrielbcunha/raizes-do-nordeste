package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.MenuUnidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

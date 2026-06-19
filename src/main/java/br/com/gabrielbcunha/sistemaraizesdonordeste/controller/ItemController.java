package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
@Tag(name="Itens",description="Endpoints para o cadastro, modificação, leitura e exclusão de Itens")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @Operation(summary="Cadastra um novo Item",
            description="Endpoint para o cadastro de um novo tipo de Item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Item não cadastrado, alguma informação requerida com preenchimento incorreto"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso")
    })
    public ResponseEntity<ItemCreateResponse> cadastrarItem(@Valid @RequestBody ItemCreateRequest itemCreateRequest){
        ItemCreateResponse cadastroItem = itemService.cadastrarItem(itemCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroItem);
    }

    @GetMapping()
    @Operation(summary="Lista todos os Itens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada, podendo conter ou não conteúdo"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso")
    })
    public ResponseEntity<Page<ItemCreateResponse>> listarTodosItems(Pageable pageable){
        Page<ItemCreateResponse> listaItems = itemService.listarTodosItems(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listaItems);
    }

}

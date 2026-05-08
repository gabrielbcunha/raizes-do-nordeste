package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itens")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemCreateResponse> cadastrarItem(@Valid @RequestBody ItemCreateRequest itemCreateRequest){
        ItemCreateResponse cadastroItem = itemService.cadastrarItem(itemCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroItem);
    }



}

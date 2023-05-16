package DKG.inventory_service.controller;


import DKG.inventory_service.dto.InventoryResponse;
import DKG.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){ // вернет да или нет, есть ли наличие товара
        return inventoryService.isInStock(skuCode);
    }
}

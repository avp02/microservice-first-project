package com.avp.inventoryservice.controller;

import com.avp.inventoryservice.dto.InventoryResponse;
import com.avp.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    //http://localhost:8082/api/inventory/Iphone_13,Nokia_6
    // change
    //http://localhost:8082/api/inventory?skuCode=Iphone_13&skuCode=Nokia_6
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {

        return inventoryService.isInStock(skuCode);
    }
}

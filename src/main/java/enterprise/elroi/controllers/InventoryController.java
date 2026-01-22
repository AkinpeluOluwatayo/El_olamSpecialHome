package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.InventoryRequest;
import enterprise.elroi.dto.response.InventoryResponse;
import enterprise.elroi.services.inventoryService.InventoryServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/el_olam/inventory")
@CrossOrigin(origins = "http://localhost:5173")
public class InventoryController {

    private final InventoryServiceInterface inventoryService;

    @Autowired
    public InventoryController(InventoryServiceInterface inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<InventoryResponse> addItem(@Valid @RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.addItem(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/update-stock/{itemId}")
    public ResponseEntity<InventoryResponse> updateStock(
            @PathVariable("itemId") String itemId,
            @RequestParam("quantity") String quantity) {
        InventoryResponse response = inventoryService.updateStock(itemId, quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {
        List<InventoryResponse> inventoryList = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventoryList);
    }
}
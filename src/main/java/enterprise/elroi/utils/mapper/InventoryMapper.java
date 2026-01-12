package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.model.Inventory;
import enterprise.elroi.dto.requests.InventoryRequest;
import enterprise.elroi.dto.response.InventoryResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InventoryMapper {

    public Inventory toInventory(InventoryRequest request) {
        if (request == null) return null;

        Inventory inventory = new Inventory();
        inventory.setItemName(request.getItemName());
        inventory.setQuantity(request.getQuantity());
        inventory.setCategory(request.getCategory());
        inventory.setLastUpdatedBy(request.getUpdatedBy());

        inventory.setCreatedAt(LocalDateTime.now());
        inventory.setLastUpdated(LocalDateTime.now());

        return inventory;
    }

    public InventoryResponse toInventoryResponse(Inventory inventory) {
        if (inventory == null) return null;

        InventoryResponse response = new InventoryResponse();
        response.setId(inventory.getId());
        response.setItemName(inventory.getItemName());
        response.setQuantity(inventory.getQuantity());
        response.setCategory(inventory.getCategory());
        response.setLastUpdated(inventory.getLastUpdated());

        return response;
    }
}
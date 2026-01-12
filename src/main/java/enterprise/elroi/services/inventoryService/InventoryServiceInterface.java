package enterprise.elroi.services.inventoryService;

import enterprise.elroi.dto.requests.InventoryRequest;
import enterprise.elroi.dto.response.InventoryResponse;
import java.util.List;

public interface InventoryServiceInterface {
    InventoryResponse addItem(InventoryRequest request);
    InventoryResponse updateStock(String itemId, String quantity);
    List<InventoryResponse> getAllInventory();
}
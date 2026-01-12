package enterprise.elroi.services.serviceImplementation.inventoryServiceImpl;

import enterprise.elroi.data.model.Inventory;
import enterprise.elroi.data.repository.InventoryRepository;
import enterprise.elroi.dto.requests.InventoryRequest;
import enterprise.elroi.dto.response.InventoryResponse;
import enterprise.elroi.exceptions.inventoryServiceException.InventoryItemNotFoundException;
import enterprise.elroi.services.inventoryService.InventoryServiceInterface;
import enterprise.elroi.utils.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryServiceInterface {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryResponse addItem(InventoryRequest request) {
        Inventory inventory = inventoryMapper.toInventory(request);
        Inventory savedItem = inventoryRepository.save(inventory);
        return inventoryMapper.toInventoryResponse(savedItem);
    }

    @Override
    public InventoryResponse updateStock(String itemId, String quantity) {
        Inventory existingItem = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new InventoryItemNotFoundException("Inventory item not found"));

        existingItem.setQuantity(quantity);
        existingItem.setLastUpdated(LocalDateTime.now());

        Inventory updatedItem = inventoryRepository.save(existingItem);
        return inventoryMapper.toInventoryResponse(updatedItem);
    }

    @Override
    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventoryMapper::toInventoryResponse)
                .collect(Collectors.toList());
    }
}
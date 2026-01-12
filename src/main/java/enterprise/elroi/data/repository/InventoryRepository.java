package enterprise.elroi.data.repository;

import enterprise.elroi.data.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
}

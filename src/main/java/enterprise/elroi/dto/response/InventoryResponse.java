package enterprise.elroi.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventoryResponse {
    private String id;
    private String itemName;
    private String quantity;
    private String category;
    private LocalDateTime lastUpdated;
}
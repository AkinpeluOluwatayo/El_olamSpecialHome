package enterprise.elroi.dto.requests;

import lombok.Data;

@Data
public class InventoryRequest {
    private String itemName;
    private String quantity;
    private String category;
    private String updatedBy;
}
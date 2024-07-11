package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageDTO {
    private int packageID;
    private String description;
    private double unitPrice;
    private int quantity;
    private int batchID;

}

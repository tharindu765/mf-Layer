package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncensePackageDTO {
    String description;
    int packageID;
    double unitPrice;
    int qty;

    public IncensePackageDTO(int packageID, String description, int qty) {
        this.description = description;
        this.packageID = packageID;
        this.qty = qty;
    }

}

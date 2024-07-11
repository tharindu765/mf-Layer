package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Package {
    private int packageID;
    private String description;
    private double unitPrice;
    private int quantity;
    private int batchID;

}

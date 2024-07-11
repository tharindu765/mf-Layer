package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDetailDTO {
        private int rawMaterialId;
        private String supplierName;
        private Date date;
        private  double unitPrice;
}

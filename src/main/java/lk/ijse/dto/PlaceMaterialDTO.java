package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceMaterialDTO {
    private MaterialDTO RawMaterial;
    private List<SupplierDetailDTO> supplierDetails;
}

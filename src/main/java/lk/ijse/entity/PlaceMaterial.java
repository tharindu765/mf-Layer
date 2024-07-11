package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceMaterial{
    private Material RawMaterial;
    private List<SupplierDetail> supplierDetails;
}

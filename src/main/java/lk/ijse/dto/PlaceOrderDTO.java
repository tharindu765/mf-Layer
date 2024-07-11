package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrderDTO {
    private OrderDTO order;
    private List<OrderDetailDTO> odList;
    private SaleDTO sale;
}

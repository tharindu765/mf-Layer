package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private Date date;
    private String incenseType;
    private String  customerName;
    private int qty;
    private double totalPrice;
    private int orderId;
    private String customerId;
    public OrderDTO(Date date, String incenseType, String customerName, int qty, double totalPrice, int orderId) {
        this.date = date;
        this.incenseType = incenseType;
        this.customerName = customerName;
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
    }

    public OrderDTO(Date date, int orderId, String id) {
        this.date=date;
        this.orderId= orderId;
        this.customerId = id;
    }

    public OrderDTO(Date date, String incenseType, String customerName, int qty, double price) {
        this.date = date;
        this.incenseType = incenseType;
        this.customerName = customerName;
        this.qty = qty;
        this.totalPrice = price;
    }
}

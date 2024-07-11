package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDTO {
    private int transactionID;
    private int qty;
    private Date sDate;
    private int orderID;
    private Date oDate;
    private String customerName;
    private String status;
    private String pType;

    public SaleDTO(int transactionID, int qty, Date sDate, int orderID, Date oDate, String customerName, String status, String pType) {
        this.transactionID = transactionID;
        this.qty = qty;
        this.sDate = sDate;
        this.orderID = orderID;
        this.oDate = oDate;
        this.customerName = customerName;
        this.status = status;
        this.pType = pType;
    }

    private int Count;
    public SaleDTO(String transactionID, Date sDate, int qty, int orderID, String status) {
        this.transactionID = Integer.parseInt(transactionID);
        this.qty = qty;
        this.sDate = sDate;
        this.orderID = orderID;
        this.status = status;
    }

    public SaleDTO(String paymentStatus, int count) {
        this.pType=paymentStatus;
        this.Count=count;
    }
}


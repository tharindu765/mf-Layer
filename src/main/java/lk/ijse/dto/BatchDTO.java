package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchDTO {
    private int BatchID;
    private Date date;
    private int qty;


    public BatchDTO(int batchID, Date date) {
        BatchID = batchID;
        this.date = date;
    }

}

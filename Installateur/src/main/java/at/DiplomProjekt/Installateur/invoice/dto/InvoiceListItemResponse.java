package at.DiplomProjekt.Installateur.invoice.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class InvoiceListItemResponse implements Serializable {

    private Long invoiceId;
    private String invoiceName;
    private int invoiceNumber;
    private String invoiceStatus;
    private String invoiceCreatedAt;
    private String updatedAt;
    private Long customerId;
    private String customerName;
    private Long userId;
    private String userName;

}

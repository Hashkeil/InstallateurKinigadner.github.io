package at.DiplomProjekt.Installateur.invoice.dto;

import at.DiplomProjekt.Installateur.invoice.Invoice;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class InvoiceFormModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerId;
    private String invoiceNote;
    private BigDecimal taxTotal;
    private List<InvoiceItemFormModel> invoiceItems = new ArrayList<>();
    private Invoice.InvoiceStatusEnum status;

    @Override
    public String toString() {
        return "InvoiceFormModel{" +
                "customerId=" + customerId +
                ", invoiceNote='" + invoiceNote + '\'' +
                ", taxTotal=" + taxTotal +
                ", invoiceItems=" + invoiceItems +
                '}';
    }
}

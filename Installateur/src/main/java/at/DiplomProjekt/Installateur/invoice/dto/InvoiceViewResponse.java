package at.DiplomProjekt.Installateur.invoice.dto;



import at.DiplomProjekt.Installateur.account.dto.AccountViewResponse;
import at.DiplomProjekt.Installateur.customer.dto.CustomerResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class InvoiceViewResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long invoiceId;
    private String invoiceName;
    private String invoiceNote;
    private int invoiceNumber;
    private String invoiceStatus;
    private BigDecimal invoiceSubtotal;
    private BigDecimal invoiceTax;
    private BigDecimal invoiceTotal;
    private String invoiceCreatedAt;
    private String invoiceUpdatedAt;
    private List<InvoiceItemResponse> invoiceItems = new ArrayList<>();
    private AccountViewResponse account;
    private CustomerResponse customer;
    private String userName;


}

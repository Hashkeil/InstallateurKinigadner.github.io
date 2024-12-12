package at.DiplomProjekt.Installateur.invoice.dto;

import at.DiplomProjekt.Installateur.customer.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class CreateInvoiceResponse implements Serializable {

    private Long invoiceId;
    private String invoiceName;
    private String invoiceNote;
    private int invoiceNumber;
    private String invoiceStatus;
    private String invoiceCreatedAt;
    private String invoiceUpdatedAt;
    private List<InvoiceItemResponse> invoiceItems = new ArrayList<>();
    private String accountCompany;
    private String accountAddress;
    private String accountCity;
    private String accountState;
    private String accountZip;
    private String accountPhone;
    private String accountEmail;
    private String accountWebsite;
    private String userName;
    private Customer customer;
}

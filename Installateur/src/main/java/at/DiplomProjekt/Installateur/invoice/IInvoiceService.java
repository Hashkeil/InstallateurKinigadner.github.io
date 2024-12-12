package at.DiplomProjekt.Installateur.invoice;

import at.DiplomProjekt.Installateur.invoice.dto.*;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;

import java.math.BigDecimal;
import java.util.List;

public interface IInvoiceService {

    void deleteInvoice(Long id);
    InvoiceViewResponse findInvoiceById(Long id);
    List<InvoiceListItemResponse> findAllInvoices();
    Invoice createDraftInvoice(CustomUserDetails userDetails);
    BigDecimal calculateSubtotal(BigDecimal unitPrice, int quantity);
    BigDecimal calculateSalesTax(BigDecimal taxRate, BigDecimal subtotal);
    InvoiceItemResponse createInvoiceItem(InvoiceItemFormModel invoiceItemModel);
    CreateInvoiceResponse createInvoice(InvoiceFormModel invoiceFormModel, CustomUserDetails userDetails);
}

package at.DiplomProjekt.Installateur.invoice.dto;

import at.DiplomProjekt.Installateur.invoice.Invoice;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Component
public class InvoiceItemFormModel implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Invoice invoice;
	private String productId;
    private String productName;
    private String productDescription;
    private int itemQuantity;
    private BigDecimal unitPrice;
    private BigDecimal taxRate;

    public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return "InvoiceItemFormModel{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", unitPrice=" + unitPrice +
                ", taxRate=" + taxRate +
                '}';
    }
}

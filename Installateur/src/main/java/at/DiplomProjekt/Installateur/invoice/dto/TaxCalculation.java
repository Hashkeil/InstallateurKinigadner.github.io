package at.DiplomProjekt.Installateur.invoice.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Component
@Data
public class TaxCalculation implements Serializable {

    private BigDecimal taxRate;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;


}

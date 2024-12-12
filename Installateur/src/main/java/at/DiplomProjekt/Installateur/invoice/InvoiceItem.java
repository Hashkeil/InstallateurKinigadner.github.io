package at.DiplomProjekt.Installateur.invoice;


import at.DiplomProjekt.Installateur.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Builder
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    
    private BigDecimal unitPrice;
    
    // unitPrice * quantity
    private BigDecimal lineSubtotal;
    
    @Column(precision = 19, scale = 4)
    private BigDecimal taxRate;
    
    // lineSubtotal * (taxRate / 100)
    private BigDecimal taxAmount;

    // unitPrice * quantity + taxAmount
    @Column(nullable = false)
    private BigDecimal amount;
    

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


}

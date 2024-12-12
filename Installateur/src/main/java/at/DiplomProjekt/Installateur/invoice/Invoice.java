package at.DiplomProjekt.Installateur.invoice;


import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.security.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Builder
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice-num-sequence")
    @SequenceGenerator(name = "invoice-num-sequence", sequenceName = "invoice_num_seq", allocationSize = 1)
    private int invoiceNum;

    @Column(length = 50)
    private String name;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private InvoiceStatusEnum status;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_status_id", nullable = false)
    private InvoiceStatus invoiceStatus;

    @Lob
    private String notes;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }




    public enum InvoiceStatusEnum { DRAFT, COMPLETED }

    public Invoice(String name, InvoiceStatus invoiceStatus) {
        this.name = name;
        this.status = InvoiceStatusEnum.DRAFT;
        this.invoiceStatus = invoiceStatus;
    }
}

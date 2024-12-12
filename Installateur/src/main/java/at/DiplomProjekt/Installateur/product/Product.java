package at.DiplomProjekt.Installateur.product;

import at.DiplomProjekt.Installateur.invoice.InvoiceItem;
import at.DiplomProjekt.Installateur.security.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Builder
@Table(name = "products")
public class Product implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 100, nullable = false)
        private String name;

        @Column(nullable = false)
        private String description;

        private int quantity;

        @Column(length = 20, nullable = false)
        private String sku;

        @Column(nullable = false)
        private boolean isService;

        @Column(updatable = false)
        private LocalDateTime createdAt;

        private double price ;

        private LocalDateTime updatedAt;

        @PrePersist
        protected void onCreate() {
            this.createdAt = LocalDateTime.now();
        }

        @PreUpdate
        protected void onUpdate() {
            this.updatedAt = LocalDateTime.now();
        }

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "product")
    private InvoiceItem invoiceItem;





    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

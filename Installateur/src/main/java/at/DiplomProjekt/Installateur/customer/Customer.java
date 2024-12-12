package at.DiplomProjekt.Installateur.customer;


import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.invoice.Invoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String companyName;
    private String address;
    private String city;
    private String zip;




    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;




    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices = new ArrayList<>();
}

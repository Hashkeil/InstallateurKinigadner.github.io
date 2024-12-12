package at.DiplomProjekt.Installateur.security;


import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.invoice.Invoice;
import at.DiplomProjekt.Installateur.product.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode
@ToString
//@Data
@Entity
@Table(name = "users")
@Builder
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled;

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


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Invoice> invoices = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;



    public User() {
        super();
        this.enabled=false;
    }

}



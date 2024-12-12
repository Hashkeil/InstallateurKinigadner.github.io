    package at.DiplomProjekt.Installateur.account;

    import at.DiplomProjekt.Installateur.security.User;
    import jakarta.persistence.*;
    import lombok.*;
    import java.io.Serializable;
    import java.time.LocalDateTime;
    import java.util.HashSet;
    import java.util.Set;
@Getter
@Setter
    @Entity
    @Table(name = "accounts")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Account implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String company;
        private String address;
        private String city;
        private String state;
        private String zip;
        private String phone;
        @Column(unique = true)
        private String email;
        private String website;
        private boolean active;

        @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
        private Set<User> users = new HashSet<>();


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


    }

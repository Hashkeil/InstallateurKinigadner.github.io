package at.DiplomProjekt.Installateur.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "privileges")
@Builder
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;


    public Privilege(String name) {
        this.name = name;
    }
}

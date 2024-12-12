package at.DiplomProjekt.Installateur.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users WHERE r.name = :name")
    Optional<Role> findByNameWithUsers(@Param("name") String name);

}

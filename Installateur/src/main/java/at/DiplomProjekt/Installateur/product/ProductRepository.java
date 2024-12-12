package at.DiplomProjekt.Installateur.product;


import at.DiplomProjekt.Installateur.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("select p from Product p join p.user u join u.account a where a = :account")
    Iterable<Product> findAllByAccount(@Param("account") Account account);

    List<Product> findByNameContaining(String searchTerm);
}

package at.DiplomProjekt.Installateur.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {


    void deleteById(Long id);
    List<Customer> findAll();
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    Optional<Customer> findByEmailAndPhone(String email, String phone);

}

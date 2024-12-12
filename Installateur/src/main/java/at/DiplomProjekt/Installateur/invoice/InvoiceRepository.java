package at.DiplomProjekt.Installateur.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAll();
    Optional<Invoice> findById(Long id);
    Page<Invoice> findAll(Pageable pageable);

    @Query("SELECT i FROM Invoice i WHERE CAST(i.invoiceNum AS string) = :invoiceNum")
    Optional<Invoice> findByInvoiceNum(@Param("invoiceNum") String invoiceNum);
}

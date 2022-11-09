package kh.farrukh.bill_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    Page<Bill> findAllByOwnerId(Long ownerId, Pageable pageable);

    boolean existsByAccountNumber(String accountNumber);
}

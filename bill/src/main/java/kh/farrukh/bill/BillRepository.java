package kh.farrukh.bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

//    Page<Bill> findAllByOwner_Id(long ownerId, Pageable pageable);

    boolean existsByAccountNumber(String accountNumber);
}

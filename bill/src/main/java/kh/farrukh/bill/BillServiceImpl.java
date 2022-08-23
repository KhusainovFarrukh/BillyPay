package kh.farrukh.bill;

import kh.farrukh.clients.bill.StatsIdDTO;
import kh.farrukh.clients.stats.Stats;
import kh.farrukh.clients.stats.StatsClient;
import kh.farrukh.common.exceptions.exceptions.DuplicateResourceException;
import kh.farrukh.common.exceptions.exceptions.ResourceNotFoundException;
import kh.farrukh.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static kh.farrukh.common.paging.PageChecker.checkPageNumber;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final StatsClient statsClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
//    private final UserRepository userRepository;

    @Override
    public PagingResponse<Bill> getBills(
//            Long ownerId,
            int page,
            int pageSize
    ) {
        checkPageNumber(page);
        // TODO: 8/18/22 check user
//        if (ownerId == null && CurrentUserUtils.isAdmin(userRepository)) {
        return new PagingResponse<>(billRepository.findAll(
                PageRequest.of(page - 1, pageSize))
        );
//        } else if (ownerId != null && CurrentUserUtils.isAdminOrAuthor(ownerId, userRepository)) {
//            checkUserId(userRepository, ownerId);
//            return new PagingResponse<>(billRepository.findAllByOwner_Id(
//                ownerId, PageRequest.of(page - 1, pageSize)
//            ));
//        } else {
//            throw new NotEnoughPermissionException();
//        }
    }

    @Override
    public PagingResponse<BillWithStatsDTO> getBillsWithStats(
//            Long ownerId,
            int page,
            int pageSize
    ) {
        checkPageNumber(page);
        // TODO: 8/18/22 check user
//        if (ownerId == null && CurrentUserUtils.isAdmin(userRepository)) {
        Page<Bill> billsPage = billRepository.findAll(PageRequest.of(page - 1, pageSize));

        PagingResponse<BillWithStatsDTO> pagingResponse = new PagingResponse<>();
        pagingResponse.setPage(billsPage.getPageable().getPageNumber() + 1);
        pagingResponse.setTotalPages(billsPage.getTotalPages());
        pagingResponse.setTotalItems(billsPage.getTotalElements());
        if (billsPage.hasNext()) {
            pagingResponse.setNextPage(billsPage.nextPageable().getPageNumber() + 1);
        }
        if (billsPage.hasPrevious()) {
            pagingResponse.setPrevPage(billsPage.previousPageable().getPageNumber() + 1);
        }

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("get-stats-of-bill");

        billsPage.forEach(bill -> {
            List<Stats> stats = circuitBreaker.run(
                    () -> statsClient.getAllStatsOfBill(bill.getId()),
                    (throwable) -> Collections.emptyList()
            );
            pagingResponse.getItems().add(new BillWithStatsDTO(bill, stats));
        });
//        } else if (ownerId != null && CurrentUserUtils.isAdminOrAuthor(ownerId, userRepository)) {
//            checkUserId(userRepository, ownerId);
//            return new PagingResponse<>(billRepository.findAllByOwner_Id(
//                ownerId, PageRequest.of(page - 1, pageSize)
//            ));
//        } else {
//            throw new NotEnoughPermissionException();
//        }
        return pagingResponse;
    }

    @Override
    public Bill getBillById(long id) {
        Bill bill = billRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bill", "id", id)
        );
        // TODO: 8/18/22 check user
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        return bill;
    }

    @Override
    public Bill addBill(BillDTO billDto) {
        // TODO: 8/18/22 check user
//        if (billDto.getOwnerId() == null) {
//            throw new BadRequestException("Owner ID");
//        }
//        if (!CurrentUserUtils.isAdminOrAuthor(billDto.getOwnerId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        if (billRepository.existsByAccountNumber(billDto.getAccountNumber())) {
            throw new DuplicateResourceException("Bill", "account number", billDto.getAccountNumber());
        }
//        return billRepository.save(new Bill(billDto, userRepository));
        return billRepository.save(new Bill(billDto));
    }

    @Override
    public Bill updateBill(long id, BillDTO billDto) {
        Bill existingBill = billRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bill", "id", id)
        );

        // TODO: 8/18/22 check user
//        if (!CurrentUserUtils.isAdminOrAuthor(existingBill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }

        if (!billDto.getAccountNumber().equals(existingBill.getAccountNumber()) &&
                billRepository.existsByAccountNumber(billDto.getAccountNumber())) {
            throw new DuplicateResourceException("Bill", "account number", billDto.getAccountNumber());
        }

        if (!billDto.getPrice().equals(existingBill.getPrice())) {
            statsClient.updateTotalPriceOfStatsByBillId(id, billDto.getPrice());
        }

        existingBill.setAddress(billDto.getAddress());
        existingBill.setAccountNumber(billDto.getAccountNumber());
        existingBill.setType(billDto.getType());
        existingBill.setPrice(billDto.getPrice());

        return billRepository.save(existingBill);
    }

    @Override
    public void deleteBillById(long id) {
        // TODO: 8/18/22 check user
//        Bill bill = billRepository.findById(id).orElseThrow(
//            () -> new ResourceNotFoundException("Bill", "id", id)
//        );
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }

        statsClient.deleteStatsByBillId(id);

        billRepository.deleteById(id);
    }

    @Override
    public Bill addStatsToBill(long id, StatsIdDTO statsIdDTO) {
        Bill bill = billRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bill", "id", id)
        );

        // TODO: 8/18/22 check user

        List<Long> newStats = bill.getStats();
        newStats.add(statsIdDTO.getStatsId());
        bill.setStats(newStats);

        return billRepository.save(bill);
    }

    @Override
    public Bill deleteStatsFromBill(long id, StatsIdDTO statsIdDTO) {
        Bill bill = billRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bill", "id", id)
        );

        // TODO: 8/18/22 check user

        List<Long> newStats = bill.getStats();
        newStats.remove(statsIdDTO.getStatsId());
        bill.setStats(newStats);

        return billRepository.save(bill);
    }
}

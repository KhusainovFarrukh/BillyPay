package kh.farrukh.bill_service;

import feign.FeignException;
import kh.farrukh.amqp.RabbitMQMessageProducer;
import kh.farrukh.common.exceptions.exceptions.BadRequestException;
import kh.farrukh.common.exceptions.exceptions.DuplicateResourceException;
import kh.farrukh.common.exceptions.exceptions.ResourceNotFoundException;
import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.bill.payloads.BillRequestDTO;
import kh.farrukh.feign_clients.bill.payloads.BillResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.BillWithStatsResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.StatsIdDTO;
import kh.farrukh.feign_clients.notification.payloads.NotificationRequestDTO;
import kh.farrukh.feign_clients.stats.StatsClient;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;
import kh.farrukh.feign_clients.user.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static kh.farrukh.common.paging.PageChecker.checkPageNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final StatsClient statsClient;
    private final UserClient userClient;
    //    private final NotificationClient notificationClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public PagingResponse<BillResponseDTO> getBills(
            String token,
            Long ownerId,
            int page,
            int pageSize
    ) {
        checkPageNumber(page);
        // TODO: 8/18/22 check user
//        if (ownerId == null && CurrentUserUtils.isAdmin(userRepository)) {
        if (ownerId == null) {
            return new PagingResponse<>(billRepository.findAll(
                    PageRequest.of(page - 1, pageSize)
            ).map(BillMappers::toBillResponseDTO));
        } else {
            try {
                userClient.getUserById(token, ownerId);
            } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
                // TODO: 11/9/22 make it  work with circuit breaker
                throw new ResourceNotFoundException("User", "id", ownerId);
            }
            return new PagingResponse<>(billRepository.findAllByOwnerId(
                    ownerId,
                    PageRequest.of(page - 1, pageSize)
            ).map(BillMappers::toBillResponseDTO));
        }

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
    public PagingResponse<BillWithStatsResponseDTO> getBillsWithStats(
            String token,
            Long ownerId,
            int page,
            int pageSize
    ) {
        checkPageNumber(page);
        // TODO: 8/18/22 check user
//        if (ownerId == null && CurrentUserUtils.isAdmin(userRepository)) {
        Page<Bill> billsPage;
        if (ownerId == null) {
            billsPage = billRepository.findAll(PageRequest.of(page - 1, pageSize));
        } else {
            try {
                userClient.getUserById(token, ownerId);
            } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
                // TODO: 11/9/22 make it  work with circuit breaker
                throw new ResourceNotFoundException("User", "id", ownerId);
            }
            billsPage = billRepository.findAllByOwnerId(ownerId, PageRequest.of(page - 1, pageSize));
        }

        // TODO: 11/8/22 why not to use constructor?
        PagingResponse<BillWithStatsResponseDTO> pagingResponse = new PagingResponse<>();
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
            List<StatsResponseDTO> stats = circuitBreaker.run(
                    () -> statsClient.getAllStatsOfBill(bill.getId()),
                    (throwable) -> Collections.emptyList()
            );
            pagingResponse.getItems().add(BillMappers.toBillWithStatsResponseDTO(bill, stats));
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
    public BillResponseDTO getBillById(long id) {
        // TODO: 8/18/22 check user
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        return billRepository.findById(id)
                .map(BillMappers::toBillResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));
    }

    @Override
    public BillResponseDTO addBill(String token, BillRequestDTO billRequestDTO) {
        // TODO: 8/18/22 check user
        if (billRequestDTO.getOwnerId() == null) throw new BadRequestException("Owner ID");
        try {
            userClient.getUserById(token, billRequestDTO.getOwnerId());
        } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
            // TODO: 11/9/22 make it  work with circuit breaker
            throw new ResourceNotFoundException("User", "id", billRequestDTO.getOwnerId());
        }
//        if (!CurrentUserUtils.isAdminOrAuthor(billDto.getOwnerId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        if (billRepository.existsByAccountNumber(billRequestDTO.getAccountNumber())) {
            throw new DuplicateResourceException("Bill", "account number", billRequestDTO.getAccountNumber());
        }
//        return billRepository.save(new Bill(billDto, userRepository));
        return BillMappers.toBillResponseDTO(billRepository.save(BillMappers.toBill(billRequestDTO)));
    }

    @Override
    public BillResponseDTO updateBill(long id, BillRequestDTO billRequestDTO) {
        log.info("Updating bill with id {}", id);
        Bill existingBill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));

        // TODO: 8/18/22 check user
//        if (!CurrentUserUtils.isAdminOrAuthor(existingBill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }

        if (!billRequestDTO.getAccountNumber().equals(existingBill.getAccountNumber()) &&
                billRepository.existsByAccountNumber(billRequestDTO.getAccountNumber())) {
            throw new DuplicateResourceException("Bill", "account number", billRequestDTO.getAccountNumber());
        }

        if (!billRequestDTO.getPrice().equals(existingBill.getPrice())) {
            log.info("Price of bill with id {} has been changed", id);
            statsClient.updateTotalPriceOfStatsByBillId(id, billRequestDTO.getPrice());
            NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(
                    "Price of bill with account number " + existingBill.getAccountNumber() + " was changed from " + existingBill.getPrice() + " to " + billRequestDTO.getPrice(),
                    existingBill.getOwnerId()
            );
            rabbitMQMessageProducer.sendNotificationMessage(notificationRequestDTO);
        }

        existingBill.setAddress(billRequestDTO.getAddress());
        existingBill.setAccountNumber(billRequestDTO.getAccountNumber());
        existingBill.setType(BillMappers.toBillType(billRequestDTO.getType()));
        existingBill.setPrice(billRequestDTO.getPrice());

        return BillMappers.toBillResponseDTO(billRepository.save(existingBill));
    }

    @Override
    public void deleteBillById(String token, long id) {
        if (!billRepository.existsById(id)) throw new ResourceNotFoundException("Bill", "id", id);

        // TODO: 8/18/22 check user
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }

        statsClient.deleteStatsByBillId(token, id);

        billRepository.deleteById(id);
    }

    @Override
    public BillResponseDTO addStatsToBill(long id, StatsIdDTO statsIdDTO) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));

        // TODO: 8/18/22 check user

        List<Long> newStats = bill.getStats();
        newStats.add(statsIdDTO.getStatsId());
        bill.setStats(newStats);

        return BillMappers.toBillResponseDTO(billRepository.save(bill));
    }

    @Override
    public BillResponseDTO deleteStatsFromBill(long id, StatsIdDTO statsIdDTO) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));

        // TODO: 8/18/22 check user

        List<Long> newStats = bill.getStats();
        newStats.remove(statsIdDTO.getStatsId());
        bill.setStats(newStats);

        return BillMappers.toBillResponseDTO(billRepository.save(bill));
    }
}

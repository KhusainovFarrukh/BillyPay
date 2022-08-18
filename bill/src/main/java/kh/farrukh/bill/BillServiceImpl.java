package kh.farrukh.bill;

import kh.farrukh.bill.utils.exception.custom.exceptions.DuplicateResourceException;
import kh.farrukh.bill.utils.exception.custom.exceptions.ResourceNotFoundException;
import kh.farrukh.bill.utils.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static kh.farrukh.bill.utils.checkers.Checkers.checkPageNumber;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final RestTemplate restTemplate;
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

        // TODO: 8/18/22 update totalPrice of all stats connected to this bill

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

        deleteAllStatsOfBill(id);

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

    private Stats getStats(long statsId) {
        try {
            Stats stats = restTemplate.getForObject(
                    "http://STATS/api/v1/stats/{statsId}",
                    Stats.class,
                    statsId
            );
            if (stats == null) {
                throw new ResourceNotFoundException("Stats", "id", statsId);
            }
            return stats;
        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("Stats", "id", statsId);
        }
    }

    private void deleteAllStatsOfBill(long billId) {
        restTemplate.delete(
                "http://STATS/api/v1/stats?bill_id={billId}",
                billId
        );
    }
}

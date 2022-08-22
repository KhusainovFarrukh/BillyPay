package kh.farrukh.stats;

import feign.FeignException;
import kh.farrukh.clients.bill.Bill;
import kh.farrukh.clients.bill.BillClient;
import kh.farrukh.clients.bill.StatsIdDTO;
import kh.farrukh.stats.utils.exception.custom.exceptions.BadRequestException;
import kh.farrukh.stats.utils.exception.custom.exceptions.ResourceNotFoundException;
import kh.farrukh.stats.utils.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kh.farrukh.stats.utils.checkers.Checkers.checkPageNumber;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final BillClient billClient;
//    private final UserRepository userRepository;

    @Override
    public PagingResponse<Stats> getStatsList(
            Long billId,
            int page,
            int pageSize
    ) {
        checkPageNumber(page);
        if (billId == null) {
            return new PagingResponse<>(statsRepository.findAll(
                    PageRequest.of(page - 1, pageSize)
            ));
        } else {
            try {
                billClient.getBillById(billId);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException("Bill", "id", billId);
            }
            return new PagingResponse<>(statsRepository.findAllByBillId(
                    billId, PageRequest.of(page - 1, pageSize)
            ));
        }
        // TODO: 8/18/22 add user check
//        if (billId == null && CurrentUserUtils.isAdmin(userRepository)) {
//        return new PagingResponse<>(statsRepository.findAll(
//                PageRequest.of(page - 1, pageSize))
//        );
//        } else if (billId != null) {
//            Bill bill = billRepository.findById(billId).orElseThrow(
//                () -> new ResourceNotFoundException("Bill", "id", billId)
//            );
//            if (CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//                return new PagingResponse<>(statsRepository.findAllByBill_Id(
//                    billId, PageRequest.of(page - 1, pageSize)
//                ));
//            } else {
//                throw new NotEnoughPermissionException();
//            }
//        } else {
//            throw new NotEnoughPermissionException();
//        }
    }

    @Override
    public List<Stats> getAllStatsOfBill(long billId) {
        return statsRepository.findAllByBillId(billId);
    }

    @Override
    public Stats getStatsById(long id) {
        Stats stats = statsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Stats", "id", id)
        );
        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(stats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        return stats;
    }

    @Override
    public Stats addStats(StatsDTO statsDto) {
        if (statsDto.getBillId() == null) {
            throw new BadRequestException("Bill ID");
        }
        Bill bill;
        try {
            bill = billClient.getBillById(statsDto.getBillId());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Bill", "id", statsDto.getBillId());
        }
        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        Stats stats = statsRepository.save(new Stats(statsDto, bill.getPrice()));
        billClient.addStatsToBill(statsDto.getBillId(), new StatsIdDTO(stats.getId()));
        return stats;
    }

    @Override
    public Stats updateStats(long id, StatsDTO statsDto) {
        Stats existingStats = statsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Stats", "id", id)
        );

        Bill bill;
        try {
            bill = billClient.getBillById(existingStats.getBillId());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Bill", "id", existingStats.getBillId());
        }

        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(existingStats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }

        existingStats.setAmount(statsDto.getAmount());
        existingStats.setTotalPrice(statsDto.getAmount() * bill.getPrice());
        existingStats.setStartDate(statsDto.getStartDate());
        existingStats.setEndDate(statsDto.getEndDate());

        return statsRepository.save(existingStats);
    }

    @Override
    public void deleteStatsById(long id) {
        Stats stats = statsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Stats", "id", id)
        );
        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(stats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        try {
            billClient.deleteStatsFromBill(stats.getBillId(), new StatsIdDTO(id));
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Bill", "id", stats.getBillId());
        }
        statsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteStatsByBillId(long billId) {
        statsRepository.deleteAllByBillId(billId);
    }

    @Override
    public void updateTotalPriceOfStatsByBillId(long billId, Double price) {
        statsRepository.updateTotalPriceByBillId(billId, price);
    }
}

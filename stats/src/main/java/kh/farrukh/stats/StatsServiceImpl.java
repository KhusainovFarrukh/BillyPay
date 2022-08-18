package kh.farrukh.stats;

import kh.farrukh.stats.utils.exception.custom.exceptions.ResourceNotFoundException;
import kh.farrukh.stats.utils.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static kh.farrukh.stats.utils.checkers.Checkers.checkPageNumber;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
//    private final BillRepository billRepository;
//    private final UserRepository userRepository;

    @Override
    public PagingResponse<Stats> getStatsList(
//            Long billId,
            int page,
            int pageSize
    ) {
        checkPageNumber(page);
//        if (billId == null && CurrentUserUtils.isAdmin(userRepository)) {
        return new PagingResponse<>(statsRepository.findAll(
                PageRequest.of(page - 1, pageSize))
        );
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
    public Stats getStatsById(long id) {
        Stats stats = statsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Stats", "id", id)
        );
//        if (!CurrentUserUtils.isAdminOrAuthor(stats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        return stats;
    }

    @Override
    public Stats addStats(StatsDTO statsDto) {
//        if (statsDto.getBillId() == null) {
//            throw new BadRequestException("Bill ID");
//        }
//        Bill bill = billRepository.findById(statsDto.getBillId()).orElseThrow(
//            () -> new ResourceNotFoundException("Bill", "id", statsDto.getBillId())
//        );
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
//        return statsRepository.save(new Stats(statsDto, billRepository));
        return statsRepository.save(new Stats(statsDto));
    }

    @Override
    public Stats updateStats(long id, StatsDTO statsDto) {
        Stats existingStats = statsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Stats", "id", id)
        );

//        if (!CurrentUserUtils.isAdminOrAuthor(existingStats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }

        existingStats.setAmount(statsDto.getAmount());
//        existingStats.setTotalPrice(existingStats.getAmount() * existingStats.getBill().getPrice());
        existingStats.setTotalPrice(existingStats.getAmount() * 1);
        existingStats.setStartDate(statsDto.getStartDate());
        existingStats.setEndDate(statsDto.getEndDate());

        return statsRepository.save(existingStats);
    }

    @Override
    public void deleteStatsById(long id) {
//        Stats stats = statsRepository.findById(id).orElseThrow(
//            () -> new ResourceNotFoundException("Stats", "id", id)
//        );
//        if (!CurrentUserUtils.isAdminOrAuthor(stats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        statsRepository.deleteById(id);
    }
}

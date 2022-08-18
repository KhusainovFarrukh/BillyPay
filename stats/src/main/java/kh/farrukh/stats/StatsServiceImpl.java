package kh.farrukh.stats;

import com.fasterxml.jackson.databind.ObjectMapper;
import kh.farrukh.stats.utils.exception.custom.exceptions.BadRequestException;
import kh.farrukh.stats.utils.exception.custom.exceptions.ResourceNotFoundException;
import kh.farrukh.stats.utils.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static kh.farrukh.stats.utils.checkers.Checkers.checkPageNumber;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final RestTemplate restTemplate;
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
            getBill(billId);
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
        Bill bill = getBill(statsDto.getBillId());
        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        return statsRepository.save(new Stats(statsDto, bill.getPrice()));
    }

    @Override
    public Stats updateStats(long id, StatsDTO statsDto) {
        Stats existingStats = statsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Stats", "id", id)
        );

        Bill bill = getBill(existingStats.getBillId());

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
        statsRepository.deleteById(id);
    }

    private Bill getBill(Long billId) {
        try {
            Bill bill = restTemplate.execute(
                    "http://localhost:8080/api/v1/bills/{billId}",
                    HttpMethod.GET,
                    null,
                    response -> new ObjectMapper().readValue(response.getBody(), Bill.class),
                    billId
            );
            if (bill == null) {
                throw new ResourceNotFoundException("Bill", "id", billId);
            }
            return bill;
        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("Bill", "id", billId);
        }
    }
}

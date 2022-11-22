package kh.farrukh.stats_service;

import feign.FeignException;
import kh.farrukh.common.exceptions.exceptions.BadRequestException;
import kh.farrukh.common.exceptions.exceptions.ResourceNotFoundException;
import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.bill.BillClient;
import kh.farrukh.feign_clients.bill.payloads.BillResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.StatsIdDTO;
import kh.farrukh.feign_clients.stats.payloads.StatsRequestDTO;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kh.farrukh.common.paging.PageChecker.checkPageNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final BillClient billClient;

    @Override
    public PagingResponse<StatsResponseDTO> getStatsList(
            String token,
            Long billId,
            int page,
            int pageSize
    ) {
        checkPageNumber(page);
        if (billId == null) {
            return new PagingResponse<>(statsRepository.findAll(
                    PageRequest.of(page - 1, pageSize)
            ).map(StatsMappers::toStatsResponseDTO));
        } else {
            try {
                billClient.getBillById(token, billId);
            } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
                throw new ResourceNotFoundException("Bill", "id", billId);
            }
            return new PagingResponse<>(statsRepository.findAllByBillId(
                    billId, PageRequest.of(page - 1, pageSize)
            ).map(StatsMappers::toStatsResponseDTO));
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
    public List<StatsResponseDTO> getAllStatsOfBill(long billId) {
        return statsRepository.findAllByBillId(billId)
                .stream().map(StatsMappers::toStatsResponseDTO).toList();
    }

    @Override
    public StatsResponseDTO getStatsById(long id) {
        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(stats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        return statsRepository.findById(id)
                .map(StatsMappers::toStatsResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Stats", "id", id));
    }

    @Override
    public StatsResponseDTO addStats(String token, StatsRequestDTO statsRequestDTO) {
        if (statsRequestDTO.getBillId() == null) throw new BadRequestException("Bill ID");
        BillResponseDTO bill;
        try {
            bill = billClient.getBillById(token, statsRequestDTO.getBillId());
        } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
            throw new ResourceNotFoundException("Bill", "id", statsRequestDTO.getBillId());
        }
        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(bill.getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        Stats stats = statsRepository.save(StatsMappers.toStats(statsRequestDTO, bill.getPrice()));
        billClient.addStatsToBill(statsRequestDTO.getBillId(), new StatsIdDTO(stats.getId()));
        return StatsMappers.toStatsResponseDTO(stats);
    }

    @Override
    public StatsResponseDTO updateStats(String token, long id, StatsRequestDTO statsRequestDTO) {
        Stats existingStats = statsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stats", "id", id));

        BillResponseDTO bill;
        try {
            bill = billClient.getBillById(token, existingStats.getBillId());
        } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
            throw new ResourceNotFoundException("Bill", "id", existingStats.getBillId());
        }

        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(existingStats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }

        existingStats.setAmount(statsRequestDTO.getAmount());
        existingStats.setTotalPrice(statsRequestDTO.getAmount() * bill.getPrice());
        existingStats.setStartDate(statsRequestDTO.getStartDate());
        existingStats.setEndDate(statsRequestDTO.getEndDate());

        return StatsMappers.toStatsResponseDTO(statsRepository.save(existingStats));
    }

    @Override
    public void deleteStatsById(long id) {
        Stats stats = statsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stats", "id", id));
        // TODO: 8/18/22 add user check
//        if (!CurrentUserUtils.isAdminOrAuthor(stats.getBill().getOwner().getId(), userRepository)) {
//            throw new NotEnoughPermissionException();
//        }
        try {
            billClient.deleteStatsFromBill(stats.getBillId(), new StatsIdDTO(id));
        } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
            throw new ResourceNotFoundException("Bill", "id", stats.getBillId());
        }
        statsRepository.deleteById(id);
    }

    @Override
    // TODO: 11/10/22 why transactional?
    @Transactional
    public void deleteStatsByBillId(String token, long billId) {
        try {
            billClient.getBillById(token, billId);
        } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
            throw new ResourceNotFoundException("Bill", "id", billId);
        }
        statsRepository.deleteAllByBillId(billId);
    }

    @Override
    public void updateTotalPriceOfStatsByBillId(long billId, Double price) {
        log.info("Updating total price of stats by bill id: {}", billId);
        statsRepository.updateTotalPriceByBillId(billId, price);
    }
}
